(ns mastermind.user-guesses
  (:use mastermind.evaluation))

(def ^:private levels
  {:easy {:num_digits 3, :dups false, :range (range 0 6)}
   :hard {:num_digits 4, :dups false, :range (range 0 10)}})

(defn- get-new-combination [num-digits]
  (let [min (int (Math/pow 10 (- num-digits 1)))
        max (int (Math/pow 10 num-digits))]
    (+ min (rand-int (- max min)))))

; TODO implement level
(defn- level [options] (:easy levels))

(defn execute [options]
  (let [l (level options)
        num-digits (:num_digits l)
        hidden (get-new-combination num-digits)]
    #_(println "hidden:" hidden)
    (println "guesses:")
    (let [get-guess #(Integer/parseInt (read-line))]
      (doseq [r (repeatedly #(evaluation (get-guess) hidden))
              :while (not= num-digits (:ok r))]
        (println r)))))
