(ns kahawa.base-terminal-interactions
  (:require [stringer.core :as s]))

(def esc-code (s/strcat \u001b \[))
(defn coded [v] (s/strcat esc-code v))
(def clear-screen-code (coded "2J"))

(defn with-saved-cursor
  ([v & more]
   (with-saved-cursor (s/strcat v (apply str more))))
  ([v]
   (str (coded "s") v (coded "u"))))

(defn move-cursor-code
  [x y]
  (coded (s/strcat y ";" x "H")))

(defn print-out
  [writer tree]
  ())
