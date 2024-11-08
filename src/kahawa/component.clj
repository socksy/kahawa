(ns kahawa.component)

(def default-display
  {:width 80
   :height 60
   :writer (java.lang.StringBuilder.)
   :relative-x 0
   :relative-y 0})

(defmulti render
  (fn [display name params & args]
    name))

(defmethod centre)
