#!/usr/bin/env bash
CURRENTDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
PROJECTNAME=${USER}`basename "$CURRENTDIR"`

docker-compose -p $PROJECTNAME down