(ns kahawa.dev-server
  (:require [clojure.core.server :refer [start-server stop-server]]
            [clojure.string :as str]))

(some-> (System/console)
        .writer)

(defn og-println
  [& args]
  (let [writer (or (some-> (System/console) .writer) *out*)]
    (.write writer (str/join " " args))
    (.write writer "\n")
    (.flush writer)))

(defn accept []
  (println "accepting")
  (loop []
    (let [input (.read *in*)]
      (print (char input))
      (flush)
      (recur))))

(defn start-dev-server [accept-fn-sym]
  (let [server (start-server {:port 0
                              :name "kahawa-dev-server"
                              :accept accept-fn-sym})
        socket-addr (.getLocalSocketAddress server)
        port (.getPort socket-addr)
        host (.getHostName socket-addr)]
    (og-println "\u001b[32mStarted dev server for REPL usage, connect with:\u001b[39m")
    (og-println "stty -echo -icanon && nc" host port)
    server))

(defn stop-dev-server []
  (stop-server "kahawa-dev-server"))
