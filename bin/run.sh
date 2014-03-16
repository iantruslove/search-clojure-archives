#!/usr/bin/env bash

# Ensure env vars for ES are set up
# Fall back to the expected Docker vars if the primary ones aren't available
ES_HOST=${ES_HOST:-"$ELASTICSEARCH_PORT_9200_TCP_ADDR"}
[[ -z "$ES_HOST" ]] && echo "Error: ES_HOST not set" && exit 1

ES_PORT=${ES_PORT:-"$ELASTICSEARCH_PORT_9200_TCP_PORT"}
[[ -z "$ES_PORT" ]] && echo "Error: ES_PORT not set" && exit 1

for file in "$BASEDIR/target"/*standalone.jar; do
    [[ $file -nt $jar ]] && jar=$file
done

JAR=$jar

MAIN=${MAIN:-search-clojure-archives.core}

JAVACMD=`which java`

LEIN_ROOT=true ES_HOST=$ES_HOST ES_PORT=$ES_PORT \
    $JAVACMD \
    $JAVA_OPTS $EXTRA_JVM_ARGUMENTS \
    -jar "$JAR" \
    "$@"
