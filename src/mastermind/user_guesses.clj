(ns mastermind.user-guesses
  (:require [mastermind.combination :refer [get-combination]]
            [mastermind.evaluation :refer :all]
            [mastermind :refer [levels]]))

; TODO implement
(defn- level [options] (:easy levels))

(defn show-hidden? [options]
  (some #{"--show-hidden"} options))

(defn execute [options]
  (let [l (level options)
        num-digits (:num-digits l)
        hidden (get-combination l)]
    (when (show-hidden? options) (println "hidden:" hidden))
    (println l)
    (println "guesses:")
    (let [get-guess #(Integer/parseInt (read-line))]
      (doseq [r (repeatedly #(evaluation (get-guess) hidden))
              :while (not= num-digits (:ok r))]
        (println r)))))
