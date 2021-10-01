#!/bin/bash
var=$(docker ps -a -q)
docker stop container $var
docker build -t sonar .
docker run -p 9000:9000 sonar
