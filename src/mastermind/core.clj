(ns mastermind.core
  (:require [mastermind.app.computer-guesses :as computer-guesses]
            [mastermind.app.user-guesses :as user-guesses]
            [mastermind.domain :refer [play]]))

(defn printable [level]
  (update level :digits #(sort (vec %))))

(defn print-level [game]
  (println (printable (:level game))))

(defn print-header [game]
  (print-level game))

(defn- game-constructor-fn-or-error [subcmd]
  (condp = subcmd
    "I-guess" user-guesses/new-game
    "computer-guesses" computer-guesses/new-game
    :error))

(defn- print-incorrect-subcommand []
  (println (clojure.string/join
             "\n"
             ["Incorrect subcommand. Possible choices are:"
               "I-guess"
              "computer-guesses"])))

(defn -main [& args]
  (let [subcmd (first args)
        game-constructor-fn (game-constructor-fn-or-error subcmd)]
    (if-not (= :error game-constructor-fn)
      (let [game (game-constructor-fn)]
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
)
