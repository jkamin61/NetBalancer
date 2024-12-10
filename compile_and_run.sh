#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <PORT> <NUMBER>"
    exit 1
fi

PORT=$1
NUMBER=$2

CLASS_PATH="target/classes"

MAIN_CLASS="org.net_balancer.DAS"

java -cp $CLASS_PATH $MAIN_CLASS $PORT $NUMBER
