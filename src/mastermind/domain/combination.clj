(ns mastermind.domain.combination
  (:require [clojure.set :refer [subset?]]
            [mastermind.utils :refer [dups?]]))

(defn- digit-set [n]
  "1234 -> #{1 2 3 4}; 1231 -> #{1 2 3}"
  (set (map (fn [^Character c] (Character/digit c 10)) (str n))))

(defn- all-combinations-filtering
  "starting from the sequence of all combinations with the right number of
  digits, filter out according to dups and the digits"
  [{:keys [num-digits dups digits]}]
  (let [min (int (Math/pow 10 (dec num-digits)))
        max (int (Math/pow 10 num-digits))
        valid-dups? (if dups (constantly true) (comp not dups?))
        valid-digits? (fn [n] (subset? (digit-set n) digits))
        valid? #(and (valid-dups? %) (valid-digits? %))]
    (filter valid? (range min max))))

; TODO look into making the resulting seq lazy
(defn all-combinations
  "returns a seq of all the combinations for a given level"
  [level]
  (all-combinations-filtering level))

(defn get-combination [level]
  (rand-nth (all-combinations level)))
