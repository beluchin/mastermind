(ns mastermind.computer-guesses
  (:require [mastermind.computer-guesses.impl :as impl]
            [clojure.string :as s]
            [validation :as v]))

(defn- two-tokens* [line]
  (let [tokens (s/split line #" ")]
    (if-not (= 2 (count tokens))
      {:error "solo 2 resultados: ok y so-so"}
      tokens)))

(defn- only-digits* [tokens]
  (let [int-or-nil #(try (Integer/parseInt %) (catch NumberFormatException _ nil))
        integers (map int-or-nil tokens)
        digit? #(<= 0 % 9)]
    (if (every? digit? integers)
      integers
      {:error "solo digitos, si?"})))

(defn- single-digits-in-range* [digits]
  (if (every? #(<= % 4) digits)
    digits
    {:error "los digitos no pueden ser mayores que 4"}))

(defn- read-evaluation []
  (let [t (v/ensure->
            (read-line)
            two-tokens*
            only-digits*
            single-digits-in-range*)
        evaluation (fn [[ok so-so]] {:ok ok :so-so so-so})
        print-error (fn [{e :error}] (println e))]
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

