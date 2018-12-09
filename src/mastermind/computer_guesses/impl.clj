(ns mastermind.computer-guesses.impl
  (:require [mastermind.domain.evaluation :as m]))

(defn filter-solutions [prior-set guess evaluation]
  (filter #(= evaluation (m/evaluation guess %)) prior-set))