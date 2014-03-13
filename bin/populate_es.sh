#!/bin/sh
# Download our initial index seed data

rm -rf /tmp/2011-07-04.json
wget https://gist.githubusercontent.com/iantruslove/9479521/raw/f1b779f17699d4f580b3fe833404e2bdccb11e24/2011-07-04.json -O /tmp/2011-07-04.json
curl -X POST http://localhost:9200/_bulk --data-binary @/tmp/2011-07-04.json
rm -rf /tmp/2011-07-04.json

