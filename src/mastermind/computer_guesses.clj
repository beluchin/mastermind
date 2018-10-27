(ns mastermind.computer-guesses
  (:require [mastermind.computer-guesses.impl :as impl]
            [clojure.string :as s]))

(defmacro ensure-> [x & forms] (throw (UnsupportedOperationException.)))

(defn map* [ok so-so] (throw (UnsupportedOperationException.)))

(defn- two-tokens* [line]
  (throw (UnsupportedOperationException.))
  #_(let [line (read-line)
          tokens (s/split line #" ")]
      (if-not (= 2 (count tokens))
        ,,,
        )))

(defn- only-digits* [t]
  (throw (UnsupportedOperationException.))
  #_(let [[ok so-so] (map #(Integer/parseInt %) tokens)]
      {:ok ok, :so-so so-so}))

(defn print-error [{e :error}] (throw (UnsupportedOperationException.)))

(defn- read-evaluation []
  (let [t (ensure->
            read-line
            two-tokens*
            only-digits*)
        evaluation (fn [[ok so-so]] (map* ok so-so))]
    (if-not (:error t)
      (evaluation t)
      (do
        (print-error t)
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

