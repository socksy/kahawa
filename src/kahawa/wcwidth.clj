(ns kahawa.wcwidth
  (:require [couplet.core :as cp]))

(defn codepoint-width [^java.lang.Integer c]
  (cond
    (= c 0) 0
    ;; ASCII control characters
    (<= 0x00 c 0x1F) 0
    (= c 0x7F) 0
    ;; Combining characters
    (or (<= 0x0300 c 0x036F)    ;; Combining diacritical marks
        (<= 0x1AB0 c 0x1AFF)    ;; Combining Diacritical Marks Extended
        (<= 0x1DC0 c 0x1DFF)    ;; Combining Diacritical Marks Supplement
        (<= 0x20D0 c 0x20FF)    ;; Combining Diacritical Marks for Symbols
        (<= 0xFE20 c 0xFE2F)    ;; Combining Half Marks
        (= 0x200d c))           ;; Zero-width joiner
    0

    ;; double-width
    (or (<= 0x1100 c 0x11FF)    ;; Hangul Jamo init. consonants
        (<= 0x2329 c 0x232A)    ;; LEFT-POINTING ANGLE BRACKET, RIGHT-POINTING ANGLE BRACKET
        (<= 0x2E80 c 0xA4CF)    ;; CJK Radicals Supplement..Yi Radicals
        (<= 0xAC00 c 0xD7A3)    ;; Hangul Syllables
        (<= 0xF900 c 0xFAFF)    ;; CJK Compatibility Ideographs
        (<= 0xFE10 c 0xFE19)    ;; Vertical forms
        (<= 0xFE30 c 0xFE6F)    ;; CJK Compatibility Forms
        (<= 0xFF00 c 0xFF60)    ;; Fullwidth Forms
        (<= 0xFFE0 c 0xFFE6)    ;; Fullwidth punctuation
          ;;;;;;;;; fake emoji that often get rendered as such;;;;;;;;;;
        (<= 0x23E9 c 0x23EC)    ;; Media controls
        (<= 0x231A c 0x231B)    ;; Watch + half full hourglass
        (= 0x23F0 c)            ;; Clock
        (= 0x23F3 c)            ;; Newly poured hourglass
        (<= 0x23FB c 0x23FC)    ;; Power icons... but only when the next char is whitespace on kitty?!
        (<= 0x2614 c 0x2615)    ;; An umbrella and hot beverage
        (<= 0x2648 c 0x2653)    ;; Zodiac bs
        (= 0x267F c)            ;; Accessibility sign
        (= 0x26A1 c)            ;; Lightning, very very frightening
        (<= 0x26C4 c 0x26C5)    ;; Snowman and a sun.. dangerous combo
        (= 0x26CE c)            ;; a... 13th zodiac sign?
        (<= 0x26CF c 0x26FF)    ;; road symbols etc
        (= 0x2700 c)            ;; scissors
        (= 0x2705 c)            ;; white checkmark (usually on green)
        (<= 0x270A c 0x270B)    ;; raised fist and raised hand
        (= 0x2728 c)            ;; AI emoji
        (= 0x274C c)            ;; Cross mark
        (= 0x274E c)            ;; Boxed cross mark
        (<= 0x2753 c 0x2755)    ;; ?!!
        (= 0x2757 c)            ;; !
        (= 0x27BF c)            ;; curly loop
          ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
        (<= 0x1F300 c 0x1F64F)  ;; Emoji proper
        (<= 0x1F900 c 0x1F9FF)  ;; Supplemental Symbols and Pictographs
        (<= 0x20000 c 0x2FFFD)  ;; CJK Ideographs Extension
        ;; rarer ones
        (= 0x2103 c)            ;; Degrees celcius sign
        (= 0x2109 c)            ;; Degrees farenheit sign
        (= 0x214f c)            ;; Samaritan source (squiggly line)
        (= 0x2114 c)            ;; lb sign
        (= 0x2118 c)            ;; Weierstrass elliptical "p" function
        (<= 0x30000 c 0x3FFFD)) ;; Reserved for future use
    2

    :else 1))

(defn wcwidth [ ^java.lang.Character c]
  (codepoint-width (long c)))

(defn remove-zero-width-adjacent-codepoints
  "Given a codepoint seq, 0 out codepoint after each ZWJ,
  so it doesn't get counted in the length"
  [ ^couplet.core.CodePointSeq codes]
  (mapv (fn [current-char previous-char]
          (if (= 0x200D previous-char)
            0
            current-char))
        codes
        (cons 32 codes))) ; aka offset by 1

(defn string-width*
  [s]
  (->> s
      cp/codepoints
      remove-zero-width-adjacent-codepoints
      (map codepoint-width)
      (reduce +)))

(def string-width (memoize string-width*))

;; FIXME work out how to split strings at unicode char boundaries closest to
;; a physical width
;;
;; 1 method - for loop through each thing until you get to that point?
;; another approach - store each section as a string inside of a vector
(defn string-split
  [s]
  (->> s
       cp/codepoints
       remove-zero-width-adjacent-codepoints
       ()))

(comment (string-width "hello") ;; => 5
         (string-width "🥩") ;; => 2
         (string-width "👨‍👩‍👦") ;; => 2
         (string-width "サンプル") ;; => 8
         (string-width "サンプル👨‍👩‍👦") ;; => 10
         (string-width "위키") ;; => 4

         )
