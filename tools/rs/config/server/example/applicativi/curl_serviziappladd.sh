#!/bin/bash

body="applicativo.json"

if [ -n "$1" ]; then
	body=$1
fi

curl --user amministratore:123456 -i -H "Content-Type: application/json" -X POST --data @${body} "http://127.0.0.1:8080/govwayAPIConfig/applicativi"
