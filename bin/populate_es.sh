#!/usr/bin/env bash

# Ensure env vars for ES are set up
# Fall back to the expected Docker vars if the primary ones aren't available
[[ -z "$ES_HOST" ]] && ES_HOST=$ELASTICSEARCH_PORT_9200_TCP_ADDR
[[ -z "$ES_HOST" ]] && echo "Error: ES_HOST not set" && exit 1

[[ -z "$ES_PORT" ]] && ES_PORT=$ELASTICSEARCH_PORT_9200_TCP_PORT
[[ -z "$ES_PORT" ]] && echo "Error: ES_PORT not set" && exit 1

# Download our initial index seed data
rm -rf /tmp/2011-07-04.json
wget https://gist.githubusercontent.com/iantruslove/9479521/raw/f1b779f17699d4f580b3fe833404e2bdccb11e24/2011-07-04.json -O /tmp/2011-07-04.json
ES=$ES_HOST:$ES_PORT
echo "Connecting to ES server at $ES..."
curl -X POST http://$ES/_bulk --data-binary @/tmp/2011-07-04.json
rm -rf /tmp/2011-07-04.json

