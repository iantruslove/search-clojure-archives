# clojure-search-archives
#
# Dependencies:
# - ElasticSearch server, either:
#   - As a docker link with a container named "elasticsearch" exposing port 9200
#     (via env var `ELASTICSEARCH_PORT_9200`)
#  - via `ES_PORT` and `ES_HOST` env vars

FROM iant/clojure:0.1.0

MAINTAINER Ian Truslove "ian.truslove@gmail.com"

# Download app sources
RUN git clone https://github.com/iantruslove/search-clojure-archives.git /usr/local/src/search-clojure-archives

# Precompile sources
RUN mkdir -p /usr/local/src/search-clojure-archives
WORKDIR /usr/local/src/search-clojure-archives
RUN LEIN_ROOT=true lein uberjar

EXPOSE 80

ENV BASEDIR /usr/local/src/search-clojure-archives
CMD ["java", "-jar", "target/search-clojure-archives-0.0.1-standalone.jar", "80"]
