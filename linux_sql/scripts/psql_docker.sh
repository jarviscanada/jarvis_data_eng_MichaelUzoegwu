#! /bin/sh

# Capture CLI arguments
cmd=$1
db_username=$2
db_password=$3

container_name=jrvs-psql
volume_name=pgdata

sudo systemctl status docker > /dev/null || sudo systemctl start docker

# This command will fail when creating a container because the container doesn't exist
# We redirect stderr to null to avoid an error message showing up during a successful command
docker container inspect $container_name > /dev/null 2>&1
container_status=$?

# Use switch case to handle create|stop|start options
case $cmd in
  create)

  if [ $container_status -eq 0 ]; then
		echo 'Container already exists'
		exit 1
	fi

  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi

	docker volume create $volume_name
	# Create and run postgres docker container
  # Configure container's default postgres username and password
  # Mount volume created in previous command
  # Map port 5432 to allow access to DB from host
	docker run --name $container_name -e POSTGRES_USER=$db_username -e POSTGRES_PASSWORD=$db_password -d -v $volume_name:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
  echo 'Container successfully created'
	exit $?
	;;

  start|stop)
  if [ $container_status -ne 0 ]; then
    echo 'Container does not exist'
    exit 1
  fi

	docker container $cmd jrvs-psql
	exit $?
	;;

  *)
	echo 'Illegal command'
	echo 'Commands: start|stop|create'
	exit 1
	;;
esac