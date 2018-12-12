(ns mastermind.app.user-guesses
  (:require [mastermind.app.level :refer :all]))

(defn new-game
  "new game for the user to guess. Contains the :level"
  []
  {:level (:easy levels)})
