# Linux Cluster Monitoring Agent

## Introduction

--- 
The **Linux Cluster Administration (LCA)** team at Jarvis manages a Linux cluster of
10 nodes, each running CentOS 7. This project aims to help this team  collect hardware 
specifications and usage data for each of their nodes.

Hardware and usage data are collected using two respective **Bash** scripts. This data
is then persisted into a **PostgreSQL** database running in a **Docker** container.

## Setup

---
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

---
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
* * * * * bash path/to/project/root/scripts/psql_docker.sh &> /tmp/host_usage.log 
```

## Architecture

---