(ns mastermind.user-guesses
  (:use mastermind.evaluation))

(def ^:private levels
  {:easy {:num-digits 3, :dups false, :range (range 0 6)}
   :hard {:num-digits 4, :dups false, :range (range 0 10)}})

(defn- get-new-combination-no-dups [num-digits]
  (loop [n num-digits
         digits (range 0 10)
         combination 0]
    (if (= n 0)
      combinations
      (let [d (rand-nth (seq digits))]
        (recur (- n 1) (disj digits d) (+ d (* combination 10)))))))

(defn- get-new-combination [num-digits]
  (get-new-combination-no-dups num-digits))

; TODO implement
(defn- level [options] (:easy levels))

(defn execute [options]
  (let [l (level options)
        num-digits (:num-digits l)
        hidden (get-new-combination num-digits)]
    #_(println "hidden:" hidden)
    (println "guesses:")
    (let [get-guess #(Integer/parseInt (read-line))]
      (doseq [r (repeatedly #(evaluation (get-guess) hidden))
              :while (not= num-digits (:ok r))]
        (println r)))))
