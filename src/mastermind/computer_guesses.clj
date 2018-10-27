(ns mastermind.computer-guesses
  (:require [mastermind.computer-guesses.impl :as impl]
            [clojure.string :as s]))

(defn- read-evaluation []
  (let [line (read-line)
        tokens (s/split line #" ")]
    (if (= 2 (count tokens))
      (let [[ok so-so] (map #(Integer/parseInt %) tokens)]
        {:ok ok, :so-so so-so})
      (do
        (println "hmm ... I need two values; first the ok and then the so-so")
        (recur)))))

(defn all-ok? [evaluation]
  (= 4 (:ok evaluation)))

(defn execute []
  (loop [possible-solutions (range 1000 9999)]
    (let [guess (rand-nth possible-solutions)]
      (println guess)
      (let [evaluation (read-evaluation)]
        (when-not (all-ok? evaluation)
          (recur (impl/filter-solutions possible-solutions guess evaluation)))))))

