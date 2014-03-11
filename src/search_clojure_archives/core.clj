(ns search-clojure-archives.core
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.util.response :refer [response status]]))

(defroutes search
  (GET "/messages" [author term] (str "Looking for messages with author " author ", term " term)))

(defroutes handler
  (GET "/" [] "#clojure search api")
  (handler/api search)
  (route/not-found (-> "sorry, nothing to see here" response (status 404))))

(defonce webserver (atom nil))

(defn stop-webserver! []
  (when @webserver
    (swap! webserver (fn [jetty] (.stop jetty)))))

(defn start-webserver! [port]
  (when-not @webserver
    (reset! webserver (run-jetty #'handler {:port port :join? false}))))

(defn -main
  "Runs the server on the port specified. Port can be set as an
  argument, via env var PORT, or defaults to 8080."
  [& [port]]
  (let [webserver-port (Integer. (or port (System/getenv "PORT") "8080"))]
    (start-webserver! webserver-port)))
