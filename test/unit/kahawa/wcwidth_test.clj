(ns kahawa.wcwidth-test
  (:require [clojure.test :refer [deftest is testing]]
            [kahawa.wcwidth :refer [string-width]]))

(deftest test-string-width
  (testing "Basic ASCII characters"
    (is (= 5 (string-width "hello")) "ASCII string should have width equal to its length")
    (is (= 0 (string-width "")) "Empty string should have width 0"))

  (testing "Emoji characters"
    (is (= 2 (string-width "🥩")) "Single emoji should have width 2")
    (is (= 2 (string-width "👨‍👩‍👦")) "Family emoji sequence (using ZWJ) should have width 2")
    (is (= 6 (string-width "🍎🍊🍇")) "Multiple emoji should sum to correct width (3 emojis x 2 width each)")
    (is (= 8 (string-width "abc🍎def")) "Mixed text and emoji"))

  (testing "CJK characters"
    (is (= 10 (string-width "こんにちは")) "Japanese characters (Katakana/Hiragana) are double-width")
    (is (= 4 (string-width "위키")) "Korean Hangul, 2 characters, double-width")
    (is (= 4 (string-width "你好")) "Chinese characters are also double-width")
    (is (= 10 (string-width "サンプル👨‍👩‍👦")) "Japanese + emoji, 8 + 2 width"))

  (testing "Control characters and special cases"
    (is (= 0 (string-width "\u0000")) "Null character should have width 0")
    (is (= 0 (string-width "\u0007")) "Bell character should have width 0")
    (is (= 0 (string-width "\u0300")) "Combining diacritical mark should have width 0")
    (is (= 1 (string-width "a\u0300")) "Combining diacritical mark after regular char should still be counted as 1")
    (is (= 3 (string-width "a🥩")) "ASCII + emoji should sum to correct width")))

(deftest test-fake-emoji-width
  (testing "Media control symbols"
    (is (= 2 (string-width "⏩")) "Fast-forward symbol should have width 2")
    (is (= 2 (string-width "⏪")) "Rewind symbol should have width 2")
    (is (= 2 (string-width "⏫")) "Fast up symbol should have width 2")
    (is (= 2 (string-width "⏬")) "Fast down symbol should have width 2"))

  (testing "Watch and hourglass symbols"
    (is (= 2 (string-width "⌚")) "Watch symbol should have width 2")
    (is (= 2 (string-width "⌛")) "Hourglass symbol should have width 2")
    (is (= 2 (string-width "⏳")) "Hourglass with flowing sand should have width 2"))

  (testing "Weather-related symbols"
    (is (= 2 (string-width "☔")) "Umbrella with rain drops should have width 2")
    (is (= 2 (string-width "☕")) "Hot beverage symbol should have width 2")
    (is (= 2 (string-width "⛄")) "Snowman without snow should have width 2")
    (is (= 2 (string-width "⛅")) "Sun behind cloud should have width 2"))

  (testing "Zodiac symbols"
    (is (= 2 (string-width "♈")) "Aries zodiac symbol should have width 2")
    (is (= 2 (string-width "♉")) "Taurus zodiac symbol should have width 2")
    (is (= 2 (string-width "♓")) "Pisces zodiac symbol should have width 2")
    (is (= 2 (string-width "⛎")) "Ophiuchus zodiac symbol should have width 2"))

  (testing "Miscellaneous symbols"
    (is (= 2 (string-width "⚡")) "Lightning bolt symbol should have width 2")
    (is (= 2 (string-width "♿")) "Wheelchair symbol should have width 2")
    (is (= 2 (string-width "⛏")) "Pickaxe symbol should have width 2")
    (is (= 2 (string-width "✅")) "Checkmark symbol should have width 2")
    (is (= 2 (string-width "✊")) "Raised fist symbol should have width 2"))

  (testing "Sparkle symbol"
    (is (= 2 (string-width "✨")) "Sparkles symbol (often called AI emoji) should have width 2"))

  (testing "Power and exclamation symbols"
    (is (= 2 (string-width "❌")) "Cross mark symbol should have width 2")
    (is (= 2 (string-width "❎")) "Negative squared cross mark should have width 2")
    (is (= 2 (string-width "❓")) "Red question mark should have width 2")
    (is (= 2 (string-width "❗")) "Red exclamation mark should have width 2")
    (is (= 2 (string-width "➿")) "Curly loop symbol should have width 2")))
