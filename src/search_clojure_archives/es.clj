(ns search-clojure-archives.es
  (:require [clj-http.client :as client]))

(defn search
  [{:keys [url query] :as arg-map}]

  (let [resp (client/get (str url "/irc.freenode.clojure/message/_search?size=50&q=" query) {:as :json})]
    (map :_source (-> resp :body :hits :hits))))

(defn cluster-state [url]
  (->
   (client/get (str url "/_cluster/state") {:as :json})
   :body))

(defn cluster-stats [url]
  (->
   (client/get (str url "/_cluster/stats") {:as :json})
   :body))
