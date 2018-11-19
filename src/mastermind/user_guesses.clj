(ns mastermind.user-guesses
  (:require [mastermind.combination :refer [get-combination]]
            [mastermind.evaluation :refer :all]
            [mastermind :refer [levels]]
            [validation :refer [ensure->]]
            [mastermind.presentation :refer [read-trimmed-line int-or-nil]]
            [mastermind.utils :refer [dups?]]))

; TODO implement
(defn- level [_] (:easy levels))

(defn- show-hidden? [options]
  (some #{"--show-hidden"} options))

; duplicated in combination-test
(defn- num-digits [n]
  (count (str n)))

(defn- correct-number-of-digits [guess level]
  (if (= (:num-digits level) (num-digits guess))
    guess
    {:error (str "se requieren " (:num-digits level) " digitos")}))

(defn- a-number [s]
  (let [n (int-or-nil s)]
    (if (nil? n)
      {:error "solo numeros"}
      n)))

(defn check-duplicates [n level]
  (if (and (not (:dups level)) (dups? n))
    {:error "sin repetidos"}
    n))

(defn- no-zero-in-front [s]
  (if (not= \0 (first s))
    s
    {:error "no zero in front"}))

(defn- digits [n]
  "1234 -> (seq [1 2 3 4])
  https://stackoverflow.com/a/29942388/614800"
  (->> n
       (iterate #(quot % 10))
       (take-while pos?)
       (mapv #(mod % 10))
       rseq))

(defn- digits-in-range [n level]
  (if (every? (:digits level) (digits n))
    n
    {:error "los digitos no estan en el rango"}))

(defn- read-guess [level]
  (let [g (ensure-> (read-trimmed-line)
                    no-zero-in-front
                    a-number
                    (correct-number-of-digits level)
                    (check-duplicates level)
                    (digits-in-range level))
        print-error (fn [{e :error}] (println e))]
    (if-not (:error g)
      g
      (do (print-error g)
          (recur level)))))

(defn execute [options]
  (let [l (level options)
        num-digits (:num-digits l)
        hidden (get-combination l)]
    (when (show-hidden? options) (println "hidden:" hidden))
    (println l)
    (println "guesses:")
    (doseq [r (repeatedly #(evaluation (read-guess l) hidden))
            :while (not= num-digits (:ok r))]
      (println r))))
