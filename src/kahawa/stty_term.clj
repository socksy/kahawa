(ns kahawa.stty-term
  (:require [clojure.java.shell :refer [sh]]
            [clojure.core.server :refer [start-server stop-server]]))

(def enable-cbreak?!
  (if (System/console)
    (sh "stty" "size")
    (sh "tput" "cols" "2>" "/dev/tty")))

(defn set-width-height []
  ())
(.flush (.writer (System/console)))
(.write (.writer (System/console)) "test\n")

(comment

(def _server (start-dev-server 'kahawa.stty-term/accept))

_server


(.getPort (.getLocalSocketAddress _server))



  (stop-server "dev-server")


  )

#_(try (sh "stty" "sane")
     (catch java.lang.IOException e (type e))
       (catch java.lang.Exception e e))
