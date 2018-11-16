(ns mastermind.user-guesses
  (:require [mastermind.combination :refer [get-combination]]
            [mastermind.evaluation :refer :all]
            [mastermind :refer [levels]]
            [validation :refer [ensure->]]))

; TODO implement
(defn- level [options] (:easy levels))

(defn- show-hidden? [options]
  (some #{"--show-hidden"} options))

; duplicated in combination-test
(defn- num-digits [n]
  (count (str n)))

(defn- correct-number-of-digits [guess level]
  (if (= (:num-digits level) (num-digits guess))
    guess
    {:error "demasiados digitos ..."}))

(defn- read-guess [level]
  (let [g (ensure-> (read-line)
                    (#(Integer/parseInt %))
                    (correct-number-of-digits level))
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
