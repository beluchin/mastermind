(ns mastermind.combination-test
  (:require [clojure.test :refer :all]
            [mastermind.combination :refer :all]
            [clojure.set :refer [subset?]]))

; duplicated in user-guesses
(defn- num-digits [n]
  (count (str n)))

(defn- dups? [comb]
  (let [s (str comb)
        n (count s)]
    (not= n (count (set s)))))

(defn- random-level
  ([] {:num-digits 4, :dups false, :digits (set (range 0 10))})
  ([& {:as overrides}] (merge (random-level) overrides)))

(deftest supports-num-digits
  (are [n] (every? #(= n (num-digits %)) (all-combinations (random-level :num-digits n)))
           4
           3
           5))

(deftest supports-no-dups
  (is (every? (comp not dups?) (all-combinations (random-level :dups false)))))

(deftest supports-dups
  (is (some dups? (all-combinations (random-level :dups true)))))

(deftest supports-digit-range
  (are [digits] (let [digit (fn [^Character c] (Character/digit c 10))
                      digit-set (fn [comb] (set (map digit (str comb))))
                      digits-in-range? #(subset? (digit-set %) digits)]
                  (every? digits-in-range? (all-combinations (random-level :digits digits))))
                #{2 4 6 8 0}
                #{1 3 5 7 9}))

#_(deftest not-enough-digits ,,,)