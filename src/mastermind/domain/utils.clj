(ns mastermind.domain.utils)

(defn dups?
  "1234 -> false; 1231 -> true"
  [n]
  (let [s (str n)]
    (not= (count s) (count (set s)))))
