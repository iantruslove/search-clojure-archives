(defproject search-clojure-archives "0.0.1"
  :description "Search the #clojure archives"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-http "0.9.0"]
                 [compojure "1.1.6"]
                 [enlive "1.1.5"]
                 [log4j "1.2.15" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]
                 [org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.logging "0.2.6"]
                 [org.slf4j/slf4j-log4j12 "1.6.6"] 
                 [ring "1.2.1"]]
  :plugins [[lein-ring "0.8.10"]]
  :resource-paths ["resources"]
  :ring {:handler search-clojure-archives.core/handler}
  :main search-clojure-archives.core
  :profiles {:uberjar {:aot :all}}
  )
