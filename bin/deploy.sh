#!/bin/bash

NAME=$1
JAR=target/$1.jar

echo Updating lambda $NAME with .jar $JAR
aws lambda update-function-code --function-name $NAME --zip-file fileb://$JAR

