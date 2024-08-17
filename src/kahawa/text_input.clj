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
(str "foo" \a)

, "மா", "ர்" .
(java.text.Normalizer/normalize "கு")
(string-width "👨‍👩‍👦")

(defn read-char
  [text-input-state char]
  (-> text-input-state
      (assoc )))

(< \u0300 1)

(long \u0300)
0x0300


(count ( \ö))

(defn wcwidth [^java.lang.Character char]
  (let [c ^long (long char)]
    (cond
      (= c 0) 0
      ;; ASCII control characters
      (<= 0x00 c 0x1F) 0
      (= c 0x7F) 0
      ;; Combining characters
      (or (<= 0x0300 c 0x036F)  ;; Combining diacritical marks
          (<= 0x1AB0 c 0x1AFF)  ;; Combining Diacritical Marks Extended
          (<= 0x1DC0 c 0x1DFF)  ;; Combining Diacritical Marks Supplement
          (<= 0x20D0 c 0x20FF)  ;; Combining Diacritical Marks for Symbols
          (<= 0xFE20 c 0xFE2F) ;; Combining Half Marks
          (= 0x200d c)) ;; Zero-width joiner
      -1
      ;; double-width
      (or (<= 0x1100 c 0x115F)    ;; Hangul Jamo init. consonants
          (<= 0x2329 c 0x232A)    ;; LEFT-POINTING ANGLE BRACKET, RIGHT-POINTING ANGLE BRACKET
          (<= 0x2E80 c 0xA4CF)    ;; CJK Radicals Supplement..Yi Radicals
          (<= 0xAC00 c 0xD7A3)    ;; Hangul Syllables
          (<= 0xF900 c 0xFAFF)    ;; CJK Compatibility Ideographs
          (<= 0xFE10 c 0xFE19)    ;; Vertical forms
          (<= 0xFE30 c 0xFE6F)    ;; CJK Compatibility Forms
          (<= 0xFF00 c 0xFF60)    ;; Fullwidth Forms
          (<= 0xFFE0 c 0xFFE6)    ;; Fullwidth punctuation
          (<= 0x1F300 c 0x1F64F)  ;; Emoticons
          (<= 0x1F900 c 0x1F9FF)  ;; Supplemental Symbols and Pictographs
          (<= 0x20000 c 0x2FFFD)  ;; CJK Ideographs Extension
          (<= 0x30000 c 0x3FFFD)) ;; Reserved for future use
      2
      :else 1)))

(defn string-width [s]
  (reduce + (map wcwidth s)))
(string-width "hello")
