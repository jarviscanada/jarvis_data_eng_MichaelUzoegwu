#!/bin/bash

if [ "$#" -ne 5 ]; then
  echo 'Illegal number of parameters' >&2
  exit 1
fi

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

host_name=$(hostname -f)
timestamp=$(date '+%F %T')
vmstat_mb="$(vmstat --unit M)"
vmstat_mb_disk="$(vmstat --unit M -d)"

parse_vmstat_simple() {
  local vmstat_out="$vmstat_mb"
  if [ "$2" = '-d' ]; then
    vmstat_out="$vmstat_mb_disk"
  fi
  echo "$vmstat_out" | tail -1 | awk "{print \$$1}"
}

# Parse host usage info
memory_free=$(parse_vmstat_simple '4')
cpu_idle=$(parse_vmstat_simple '15')
cpu_kernel=$(parse_vmstat_simple '14')
disk_io=$(parse_vmstat_simple '10' '-d')
disk_available=$(df -BM / | tail -1 | awk '{print $4}' | sed 's/[^0-9]//g')

# Insert row into host_usage table
export PGPASSWORD="$psql_password"
host_id_query="(
  SELECT id FROM host_info
  WHERE hostname='$host_name'
)"
insert_stmt="
  INSERT INTO host_usage (
    timestamp,
    host_id,
    memory_free,
    cpu_idle,
    cpu_kernel,
    disk_io,
    disk_available
  )
  VALUES (
    '$timestamp',
    $host_id_query,
    $memory_free,
    $cpu_idle,
    $cpu_kernel,
    $disk_io,
    $disk_available
  );
"
psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_stmt"
exit $?