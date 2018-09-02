(ns mastermind.computer-guesses
  (:require [mastermind.computer-guesses.impl :as impl]
            [clojure.string :as s]))

(defn- read-evaluation []
  (let [line (read-line)
        [ok so-so] (into [] (map #(Integer/parseInt %) (s/split line #" ")))]
    {:ok ok, :so-so so-so}))

(defn all-ok? [evaluation]
  (= 4 (:ok evaluation)))

(defn execute []
  (loop [possible-solutions (range 1000 9999)]
    (let [guess (rand-nth possible-solutions)]
      (println guess)
      (let [evaluation (read-evaluation)]
        (when-not (all-ok? evaluation)
          (recur (impl/filter-solutions possible-solutions guess evaluation)))))))

