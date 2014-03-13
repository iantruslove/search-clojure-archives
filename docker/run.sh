#!/bin/sh

cd /usr/local/src/search-clojure-archives.git
LEIN_ROOT=true ES_HOST=$ELASTICSEARCH_PORT_9200_TCP_ADDR ES_PORT=$ELASTICSEARCH_PORT_9200_TCP_PORT lein run 80
