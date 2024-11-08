(ns kahawa.dev-server
  (:require [clojure.core.server :refer [start-server stop-server]]
            [clojure.string :as str]))

(defn println-with-writer
  [writer args]
  (.write writer (str/join " " args))
  (.write writer "\n")
  (.flush writer))

(defn og-println
  [& args]
  (println-with-writer
   (or (some-> (System/console) .writer)
       *out*)
   args))

(def !server-output-stream (atom nil))
(defn assert-server-stream
  []
  (assert (some? @!server-output-stream)
          "tried to write to server but you haven't started any server"))

(defn server-println
  [& args]
  (assert-server-stream)
  (println-with-writer @!server-output-stream args))

(defn server-write
  [s]
  (assert-server-stream)
  (.write @!server-output-stream s)
  (.flush @!server-output-stream))

(defn accept []
  (reset! !server-output-stream *out*)
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
  (reset! !server-output-stream nil)
  (stop-server "kahawa-dev-server"))

(comment
  (start-dev-server 'kahawa.dev-server/accept)
  (stop-dev-server))
