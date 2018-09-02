(ns mastermind.core
  (:require [mastermind.computer-guesses :as computer-guesses]
            [mastermind.user-guesses :as user-guesses]))

(defn print-incorrect-subcommand []
  (println (clojure.string/join
             "\n"
             ["Incorrect subcommand. Possible choices are:"
               "I-guess"
              "computer-guesses"])))

(defn -main
  [& args]
  (let [subcmd (first args)]
    (condp = subcmd
      "I-guess" (user-guesses/execute)
      "computer-guesses" (computer-guesses/execute)
      (print-incorrect-subcommand))))