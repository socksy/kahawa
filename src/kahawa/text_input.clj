(ns kahawa.text-input
  (:import [java.lang StringBuilder]))

(defn text-input
  [{:keys [max-width rows scrollable]}]
  ;; using pure java strings for now, if it doesn't perform then
  ;; let's look at using a StringBuilder or rope etc
  {:raw-text ""
   :visible-text ""
   :max-width (or max-width 999)
   :current-w 0
   :current-h 0
   :rows (or rows 1)
   :scrollable (or scrollable true)
   :cursor-x 0
   :cursor-y 0})

(defn update-visible-text
  [{:keys [^long max-width ^String visible-text ^String raw-text
           ^boolean scrollable ^long current-w  ^long current-h
           ^long rows]
    :as text-input-state}
   ^String new-text]


  (cond (and (<= current-w max-width)
           (<= current-h rows))
        (str ))
  )

(defn read-char
  [text-input-state char]
  (-> text-input-state
      (assoc )))
