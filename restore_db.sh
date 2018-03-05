#!/usr/bin/env bash

set -e

FILENAME=$0
CONTAINER=$1
SQLSCRIPT=$2

if [[ ! -z $CONTAINER && ! -z $SQLSCRIPT ]]; then
    echo "Copying SQL Script $SQLSCRIPT in container $CONTAINER"
    docker cp $SQLSCRIPT $CONTAINER:/db.sql
    echo "Running script in container $CONTAINER"
    docker exec -it $CONTAINER sh -c "psql -U admin -W application < /db.sql"
    echo "Done"
else
    echo "Usage: $FILENAME <container_name> <sql_script>"
fi