(ns mastermind.domain.utils)

(defn dups? [n]
  "1234 -> false; 1231 -> true"
  (let [s (str n)]
    (not= (count s) (count (set s)))))
