(ns mastermind.core
  (:require [mastermind.app.computer-guesses :as computer-guesses]
            [mastermind.app.user-guesses :as user-guesses]
            [mastermind.domain :refer [play]]
            [mastermind.app.auto :as auto]))

(defn printable [level]
  (update level :digits #(sort (vec %))))

(defn print-level [game]
  (println (printable (:level game))))

(defn print-header [game]
  (print-level game))

(defn- new-game-fn-or-error [subcmd]
  (condp = subcmd
    "I-guess" user-guesses/new-game
    "computer-guesses" computer-guesses/new-game
    "auto" auto/new-game
    :error))

(defn- print-incorrect-subcommand []
  (println (clojure.string/join
             "\n"
             ["Incorrect subcommand. Possible choices are:"
               "I-guess"
              "computer-guesses"])))

(defn -main [& args]
  (let [subcmd (first args)
        new-game-fn (new-game-fn-or-error subcmd)]
    (if-not (= :error new-game-fn)
      (let [game (new-game-fn)]
        (print-header game)
        (play game))
      (print-incorrect-subcommand))))

(comment

  (defn call-service
    [arg1 arg2 callback-fn]
    ;; ...perform service call, eventually invoking callback-fn with results...
    (future (callback-fn (+ arg1 arg2) (- arg1 arg2))))

  (defn sync-fn
    [async-fn]
    (fn [& args]
      (let [result (promise)]
        (apply async-fn (conj (vec args) #(deliver result %&)))
        @result)))

  ((sync-fn call-service) 8 7)

  ;; trying to mock a specific implementation of a protocol
  (defprotocol P
    (foo [p]))
  (defrecord R1 [])
  (defrecord R2 [])
  (extend-protocol P
    R1
    (foo [_] :r1)
    R2
    (foo [_] :r2))
  
  (foo (->R1)) ;; :r1
  (foo (->R2)) ;; :r2
  
  (with-redefs [foo (constantly :redefed)]
    (foo (->R1))) ;; :redefed

  (with-redefs [foo (constantly :redefed)]
    (foo (->R2))) ;; :redefed

)
