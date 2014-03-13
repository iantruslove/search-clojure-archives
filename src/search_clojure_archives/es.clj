(ns search-clojure-archives.es
  (:require [clj-http.client :as client]))

(defn search  
  [arg-map]
  (throw (Exception. "Foo! TODO!")))

(defn cluster-state [url]
  (-> 
   (client/get (str url "/_cluster/state") {:as :json})
   :body))

(defn cluster-stats [url]
  (-> 
   (client/get (str url "/_cluster/stats") {:as :json})
   :body))
