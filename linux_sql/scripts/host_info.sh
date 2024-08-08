#!/bin/bash

#if [ "${#}" -ne 5 ]; then
#  echo 'Illegal number of parameters' >&2
#  exit 1
#fi

psql_host=${1}
psql_port=${2}
db_name=${3}
psql_user=${4}
psql_password=${5}
lscpu_out="$(lscpu)"
vmstat_mb="$(vmstat --unit M)"
vmstat_mb_disk="$(vmstat --unit M -d)"

parse_lscpu_simple() {
  local sep=${3:-': '}
  echo "${lscpu_out}" | egrep "$1" | awk -F "${sep}" "{print \$${2}}" | xargs
}

parse_vmstat_simple() {
  local vmstat_out="${vmstat_mb}"
  if [ "${2}" = '-d' ]; then
    vmstat_out="${vmstat_mb_disk}"
  fi
  echo "${vmstat_out}" | tail -1 | awk "{print \$${1}}"
}

# Parse host hardware specs
host_name=$(hostname -f)
cpu_numbers=$(parse_lscpu_simple '^CPU\(s\):' '2')
cpu_architecture=$(parse_lscpu_simple '^Architecture:' '2')
cpu_model=$(parse_lscpu_simple '^Model name:' '2')
cpu_mhz=$(echo "${cpu_model}" | awk '{print $5}' | sed 's/[^0-9.]//g' | awk '{print $1 * 1000}')
l2_cache=$(parse_lscpu_simple '^L2 cache:' '3' ' ')
total_mem=$(parse_vmstat_simple '4' | awk '{print $1 * 1000}')
timestamp=$(date '+%F %T')

# Parse host usage info
memory_free=$(parse_vmstat_simple '4')
cpu_idle=$(parse_vmstat_simple '15')
cpu_kernel=$(parse_vmstat_simple '14')
disk_io=$(parse_vmstat_simple '10' '-d')
disk_available=$(df -BM / | tail -1 | awk '{print $4}' | sed 's/[^0-9]//g')

echo "${l2_cache}"

exit 0