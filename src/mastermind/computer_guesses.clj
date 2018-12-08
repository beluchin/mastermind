(ns mastermind.computer-guesses
  (:require [clojure.string :as s]
            [mastermind :refer [levels]]
            [mastermind.computer-guesses.impl :as impl]
            [mastermind.domain.combination :refer [all-combinations]]
            [mastermind.presentation :as presentation]
            [validation :as v]))

(defn- two-tokens* [line]
  (let [tokens (remove s/blank? (s/split line #" "))]
    (if-not (= 2 (count tokens))
      {:error "solo 2 resultados: ok y so-so"}
      tokens)))

(defn- only-digits* [tokens]
  (let [integers (map presentation/int-or-nil tokens)
        digit? #(<= 0 % 9)]
    (if (every? digit? integers)
      integers
      {:error "solo digitos, si?"})))

(defn- single-digits-in-range* [digits]
  (if (every? #(<= % 4) digits)
    digits
    {:error "los digitos no pueden ser mayores que 4"}))

(defn- evaluation-check* [evaluation]
  (let [sum-no-more-than (fn [e n]
                           (if (<= (apply + (vals e)) n)
                             e
                             {:error (str "la suma tiene que ser menor que " n)}))
        all-but-one-ok-is-illegal (fn [e n]
                                    (if (or (< (:ok e) (- n 1)) (not= 1 (:so-so e)))
                                      e
                                      {:error "no es posible tener un solo so-so y los demas ok"}))]
    (v/ensure->
      evaluation
      (sum-no-more-than 4)
      (all-but-one-ok-is-illegal 4))))

(defn- read-evaluation []
  (let [evaluation (fn [[ok so-so]] {:ok ok :so-so so-so})
        e (v/ensure-> (presentation/read-trimmed-line)
                      two-tokens*
                      only-digits*
                      single-digits-in-range*
                      evaluation
                      evaluation-check*)

        ; duplicated in user-guesses
        print-error (fn [{e :error}] (println e))]

    (if-not (:error e)
      e
      (do
        (print-error e)
        (recur)))))

(defn all-ok? [evaluation]
  (= 4 (:ok evaluation)))

(defn execute []
  (let [level (:hard levels)]
    (presentation/print-level level)
    (loop [possible-solutions (all-combinations level)]
      (if (empty? possible-solutions)
        (println "hmm, me diste un resultado mal")
        (let [guess (rand-nth possible-solutions)]
          (println guess)
          (let [evaluation (read-evaluation)]
            (when-not (all-ok? evaluation)
              (recur (impl/filter-solutions possible-solutions guess evaluation)))))))))

