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
lscpu_out="$(lscpu)"

parse_lscpu_simple() {
  local sep=${3:-': '}
  echo "$lscpu_out" | egrep "$1" | awk -F "$sep" "{print \$$2}" | xargs
}

# Parse host hardware specs
cpu_numbers=$(parse_lscpu_simple '^CPU\(s\):' '2')
cpu_architecture=$(parse_lscpu_simple '^Architecture:' '2')
cpu_model=$(parse_lscpu_simple '^Model name:' '2')
cpu_mhz=$(echo "$cpu_model" | awk '{print $5}' | sed 's/[^0-9.]//g' | awk '{print $1 * 1000}')
l2_cache=$(parse_lscpu_simple '^L2 cache:' '3' ' ')
total_mem=$(vmstat --unit M | tail -1 | awk '{print $4}')

# Add row in host_info table
export PGPASSWORD="$psql_password"
insert_stmt="
  INSERT INTO host_info (
    hostname,
    cpu_number,
    cpu_architecture,
    cpu_model,
    cpu_mhz,
    l2_cache,
    timestamp,
    total_mem
  )
  VALUES (
    '$host_name',
    $cpu_numbers,
    '$cpu_architecture',
    '$cpu_model',
    $cpu_mhz,
    $l2_cache,
    '$timestamp',
    $total_mem
  );
"
psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_stmt"
exit $?