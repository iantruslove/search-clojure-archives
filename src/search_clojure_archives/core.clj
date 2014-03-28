(ns search-clojure-archives.core
  (:require [search-clojure-archives.es :as es]
            [clojure.tools.logging :as log]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [net.cgrand.enlive-html :as html]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.resource :as resource]
            [ring.util.response :refer [redirect response status]])
  (:gen-class))

(defonce webserver (atom nil))

(defonce es-url (atom nil))

(html/defsnippet result-snippet "templates/message.html"
  [:div#search_results :.message]
  [{:keys [datestamp author message]}]
  [:.time] (html/content datestamp)
  [:.author] (html/content author)
  [:.text] (html/content message))

(html/deftemplate page-template "templates/page.html"
  [results]
  [:div#search_results] (html/content
                         (map result-snippet results)))

(defroutes search-handler
  (GET "/messages" [author term]
       (str "Looking for messages with author " author ", term " term)))

(defn search [query]
  (log/debug (format "Search: q=%s" query))
  (es/search {:url @es-url
              :query query}))

(defroutes handler
  (-> (GET "/" [q] (page-template (search q)))
      handler/api
      (resource/wrap-resource "public"))
  (route/not-found (-> "sorry, nothing to see here" response (status 404))))

(defn stop-webserver! []
  (when @webserver
    (log/debug "Stopping webserver...")
    (swap! webserver (fn [jetty] (.stop jetty)))))

(defn start-webserver! [port]
  (when-not @webserver
    (log/debug "Starting webserver, port" port)
    (reset! webserver (run-jetty #'handler {:port port :join? false}))))

(defn -main
  "Runs the server on the port specified. Port can be set as an
  argument, via env var PORT, or defaults to 8080.

  Depends upon an ElasticSearch server, identified by env vars ES_HOST
  and ES_PORT.  Defaults are localhost and 9200 respectively."
  [& [port]]
  (let [webserver-port (Integer. (or port (System/getenv "PORT") "8080"))
        elasticsearch-host (or (System/getenv "ELASTICSEARCH_PORT_9200_TCP_ADDR")
                               (System/getenv "ES_HOST")
                               "localhost")
        elasticsearch-port (Integer. (or (System/getenv "ELASTICSEARCH_PORT_9200_TCP_PORT")
                                         (System/getenv "ES_PORT")
                                         "9200"))
        elasticsearch-url (format "http://%s:%d/" elasticsearch-host elasticsearch-port)]
    (try
      (log/info "Connecting to ElasticSearch server at" elasticsearch-url)
      (reset! es-url elasticsearch-url)
      (let [state (:status (es/cluster-stats @es-url))]
        (log/debug "ElasticSearch cluster state:" state)
        (when (= "red" state)
          (throw (Exception. (str "Oh dear, cluster state is " state)))))
      (catch Exception e
        (log/error e "Problem querying ElasticSearch cluster status at" @es-url ":" (.getMessage e))))
    (start-webserver! webserver-port)
    (log/info "Webserver started.")))
