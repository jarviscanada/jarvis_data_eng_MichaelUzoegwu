# Linux Cluster Monitoring Agent

## Introduction
The **Linux Cluster Administration (LCA)** team at Jarvis manages a Linux cluster of
10 nodes, each running CentOS 7. This project aims to help this team  collect hardware 
specifications and usage data for each of their nodes.

Hardware and usage data are collected using two respective **Bash** scripts. This data
is then persisted into a **PostgreSQL** database running in a **Docker** container.

## Setup
```python
# Prerequisites-----------------------------
# Both docker and psql need to be installed
# ------------------------------------------
```
Create a psql instance with your postgres user and password:
``` bash
scripts/psql_docker.sh create <pg_user> <pg_password>
```
Create the DB:
```bash
psql -h <pg_host> -U <pg_user> -W
# Now in psql shell
CREATE DATABASE <db_name>;
\q # To exit shell
```
Create `host_info` and `host_usage` tables:
```bash
psql -h <pg_host> -U <pg_user> -d <db_name> -f sql/ddl.sql
```

## Usage
Store hardware data in `host_info` table:
```bash
scripts/host_info.sh <pg_host> <pg_port> <db_name> <pg_user> <pg_password>
```
Store host usage data in `host_usage` table:
```bash
scripts/host_usage.sh <pg_host> <pg_port> <db_name> <pg_user> <pg_password>
```
Create cron job to run `scripts/host_usage.sh` every minute:
```bash
crontab -e
# In the text editor type
* * * * * bash path/to/project/root/scripts/psql_docker.sh &>> /tmp/host_usage.log 
```

## Architecture
![Architecture Illustration](assets/linux-project.drawio.png "Architecture Illustration")

## Implementation

### Database model
There are two tables to store the `host info` and host `host usage` data
respectively.
---
`host_info` - *columns and sample data* | *[key] and (unit) included for clarity*

| id [pk] | hostname               | cpu_number | cpu_architecture | cpu_model                     | cpu_mhz | l2_cache | timestamp           | total_mem |
|---------|------------------------|------------|------------------|-------------------------------|---------|----------|---------------------|-----------|
| 1       | desktop-north-america  | 2          | x86_64           | Intel(R) Xeon(R) CPU @2.20GHz | 2200    | 256 (KB) | 2024-08-19 11:29:59 | 3800 (KB) |
---
`host_usage` -*columns and sample data* | *(unit) included for clarity*

| timestamp [pk]  | host_id [fk] | memory_free | cpu_idle | cpu_kernel | disk_io | disk_available |
|-----------------|--------------|-------------|----------|------------|---------|----------------|
| 2023-11-28      | 1            | 3803        | 96 (%)   | 1 (%)      | 2 (%)   | 17000 (MB)     |
---

### Agent - `host_info.sh`
`host_info.sh` collects hardware specifications mainly by parsing the output of
the `lscpu` command. Once collected, the data is persisted into the DB executing
an `INSERT` statement with the `psql` command. **This script is only intended to
be run once per host.**

```bash
#Usage
scripts/host_info.sh <pg_host> <pg_port> <db_name> <pg_user> <pg_password>
```
```bash
# Pseudocode
parse_lscpu() { lscpu | egrep "..." | awk "..."  }

cpu_number=$(parse_lscpu "...")
cpu_architecture=$(parse_lscpu "...")
...

psql ... -c "INSERT INTO host_info (cpu_number,...) VALUES (2,...);" 
```

### Agent - `host_usage.sh`
`host_usage.sh`  is similar in structure but collects usage data at time of execution.
**This script runs every minute via a cron job.**

```bash
#Usage
scripts/host_usage.sh <pg_host> <pg_port> <db_name> <pg_user> <pg_password>
```
```bash
# Pseudocode
parse_vmstat() { vmstat | awk "..."  }
memory_free=$(parse_vmstat "..." )
disk_io=$(parse_vmstat "...")
...

psql ... -c "INSERT INTO host_usage (memory_free,...) VALUES (3803,...);" 
```

### Utility - `psql_docker.sh`
`psql_docker.sh` is a utility script that manages the docker container containing
our psql database.
```bash
scripts/psql_docker.sh <create|start|stop> <pg_user> <pg_password>
```
*There are three modes `create|start|stop`.
`create` requires `pg_user` and `pg_password` as  arguments to initialize 
the container. `start|stop` do not.*

- `create` creates a docker container with a psql DB. A psql superuser is created
with the `pg_user` and `pg_password` parameters.
- `start` starts the docker container with `docker container start`
- `stop` stops the docker container with `docker container stop`

### Utility - `ddl.sql`
This file contains sql statements that will create our database with its two tables
`host_info` and `host_usage`.
```psql
--Pseudocode
CREATE DATABASE host_agent;
CREATE TABLE host_info (...);
CREATE TABLE host_usage (...);
```

### Automation - `crontab`
This entry in `crontab` ensures that the script is run every minute.
```bash
* * * * * bash path/to/project/root/scripts/psql_docker.sh &>> /tmp/host_usage.log 
```

## Potential Improvements

### Streamline setup process
Create a `setup.sh <pg_host> <pg_user> <pg_password>` script to do all the setup.
```bash
# setup.sh
scripts/psql_docker.sh create <pg_user> <pg_password>
psql -h <pg_host> -U <pg_user> -d host_agent -f sql/ddl.sql
```
Alternatively modify `psql_docker.sh create` to have it run the `psql` command
shown above.

### Remove password parameters
To avoid exposing passwords we could ask the user to add their credentials in a 
`~/.pgpass` file.