(ns mastermind.core-test
  (:require [mastermind.core :as sut]
            [clojure.test :as t]
            [spy.core :as spy]
            [mastermind.domain :as domain]
            [mastermind.app.console :as console]
            [mastermind.app.user-guesses :as user-guesses]
            [mastermind.domain.combination :as combination]
            [mastermind.app.computer-guesses :as computer-guesses]))

(t/deftest user-guesses-end-to-end
  (let [hidden (combination/get-combination user-guesses/default-level)]
    (with-redefs [console/read-until-no-error (constantly hidden)
                  rand-nth (constantly hidden)] 
      (t/is (nil? (sut/-main "I-guess"))))))

(t/deftest computer-guesses-end-to-end 
  (let [level computer-guesses/default-level]
    (with-redefs
      [read-line (constantly (str (:num-digits level) " 0"))]
      (t/is (nil? (sut/-main "computer-guesses"))))))

(t/deftest prints-level-when-game-starts
  (with-redefs [sut/print-level (spy/mock (fn [_] nil))
                domain/play (spy/mock (fn [_] nil))]
    (t/are [switch] (do
                      (spy/reset-spy! sut/print-level)
                      (sut/-main switch)
                      (spy/called-once? sut/print-level))
      "computer-guesses"
      "I-guess")))

(t/deftest digits-are-sorted-on-printable-level
  (let [level {:digits #{1 2 3}}]
    (t/is (= (:digits (sut/printable level)) [1 2 3]))))
