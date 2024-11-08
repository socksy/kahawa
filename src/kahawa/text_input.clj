(ns kahawa.text-input
  (:require [clojure.string :as str]
            [kahawa.component :as k.c]
            [kahawa.wcwidth :refer [string-width]])
  #_(:import [java.lang StringBuilder]))

(defn text-input
  [{:keys [max-width rows scrollable]}]
  ;; using pure java strings for now, if it doesn't perform then
  ;; let's look at using a StringBuilder or rope etc
  {:raw-text ""
   ;; TODO should this be a stringbuilder?
   :visible-text ""
   :max-width (or max-width 999)
   :rows (or rows 1)
   :scrollable? (or scrollable true)
   :cursor-x 0
   :cursor-y 0})

(defn sanitize-text-input
  [^String text ^boolean single-line?]
  (cond-> (str/replace text "\\" "\\\\")
    single-line? (str/replace "\n" "")))

(defn update-visible-text
  [{:keys [^long max-width ^String raw-text
           ^boolean scrollable? ^long rows]
    :as text-input-state}
   ^String new-text]
  (let [sanitized-text (sanitize-text-input new-text scrollable?)
        new-width (string-width sanitized-text)
        width-difference (- new-width (string-width raw-text))
        new-height (if scrollable?
                     (inc (count (re-seq #"\n" sanitized-text)))
                     1)]
    (-> (cond (and (<= new-width max-width)
                   (<= new-height rows))
              (-> text-input-state
                  (assoc :visible-text sanitized-text)
                  ;; TODO this is nonsense
                  (update :cursor-x #(+ % width-difference)))

              (<= new-height rows)
              (-> text-input-state
                  ;; TODO replace subs with something that is both unicode aware
                  ;; and also differentiates visible chars
                  (assoc :visible-text (subs sanitized-text (- new-width max-width))))
              ;;TODO multiline text...
              )
        (assoc :raw-text new-text))))

(defn text-update
  [[:keys [cursor-x ]
    :as _text-input-state] new-char]
  (if ()))

(defn is-control-char?
  [char]
  ;; TODO
  false)

(defn control-update
  [text-input-state]
  text-input-state)

(defn read-char
  [text-input-state char]
  (if (is-control-char? char)
    (control-update text-input-state)
    (update-visible-text text-input-state )
      ))

(defn render
  [text-input]
  ;; TODO multiline?
  (str "> " (:visible-text text-input)))

;; FIXME remove
(defn- test-accept
  []
  ())
