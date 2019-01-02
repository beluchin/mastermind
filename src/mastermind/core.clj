(ns mastermind.core
  (:require [mastermind.app.user-guesses :as user-guesses]
            [mastermind.domain :refer [play]]))

(defn printable [level]
  (update level :digits #(sort (vec %))))

(defn print-level [game]
  (println (printable (:level game))))

(defn print-header [game]
  (print-level game))

(defn -main [& args]
  (let [game (user-guesses/new-game)]
    (print-header game)
    (play game)))

(comment
(defn print-incorrect-subcommand []
  (println (clojure.string/join
             "\n"
             ["Incorrect subcommand. Possible choices are:"
               "I-guess"
              "computer-guesses"])))
)
