(defproject search-clojure-archives "0.0.1"
  :description "Search the #clojure archives"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-http "0.9.0"]
                 [com.taoensso/timbre "3.1.2"]
                 [compojure "1.1.6"]
                 [enlive "1.1.5"]
                 [org.clojure/clojure "1.5.1"]
                 [ring "1.2.1"]]
  :plugins [[lein-ring "0.8.10"]]
  :resource-paths ["resources"]
  :ring {:handler search-clojure-archives.core/handler}
  :main search-clojure-archives.core
  )
