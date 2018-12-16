(ns mastermind.app.console-test
  (:require [clojure.test :refer :all]
            [mastermind.app.console :as console]
            [spy.core :as spy]))

(deftest calls-print-level-when-game-starts
  (with-redefs [console/print-level (spy/mock (fn [_] nil))] 
    (console/game-started "I-guess")
    (is (spy/called-once? console/print-level))))
