# search-clojure-archives

Demo web app searching through some #clojure logs

## Usage

* Get yourself an ElasticSearch server. I've been playing with Docker
  to do this:
  * `docker pull iant/es_demo`, then
  `docker run -p 2222:22 -p 9200:9200 -p 9300:9300 -t -i iant/es_demo`
* Throw some data ES's way: `./bin/populate_es.sh` - this grabs a
  conveniently formatted packet of data I stashed in a gist and
  indexes it.
* Run the Ring server: `lein run <port>`
