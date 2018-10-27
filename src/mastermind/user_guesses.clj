(ns mastermind.user-guesses
  (:use mastermind.evaluation))

(defn- get-new-combination
  "selects a new random combination
  - a random number from 1000 (inclusive) to 9999 (exclusive)"
  []
  (+ 1000 (rand-int 9000)))

(defn execute []
  (let [hidden (get-new-combination)]
    (println "hidden:" hidden)
    (println "guesses:")
    (let [get-guess #(Integer/parseInt (read-line))]
      (doseq [r (repeatedly #(evaluation (get-guess) hidden))
              :while (not= 4 (:ok r))]
        (println r)))))
