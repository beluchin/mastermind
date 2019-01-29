(ns mastermind.core-test
  (:require [clojure.test :as t]
            [mastermind.app.auto :as auto]
            [mastermind.app.computer-guesses :as computer-guesses]
            [mastermind.app.user-guesses :as user-guesses]
            [mastermind.core :as sut]
            [mastermind.domain :as domain]
            [mastermind.domain.combination :as combination]
            [spy.core :as spy]))

(t/deftest user-guesses-end-to-end
  (let [hidden (combination/get-combination user-guesses/default-level)]
    (with-redefs [rand-nth (constantly hidden)
                  read-line (constantly (str hidden))] 
      (t/is (nil? (sut/-main "I-guess"))))))

(t/deftest computer-guesses-end-to-end 
  (with-redefs
    [read-line (constantly (format "%d 0" (:num-digits computer-guesses/default-level)))]
    (t/is (nil? (sut/-main "computer-guesses")))))

(t/deftest auto-end-to-end
  (let [level auto/default-level
        code (combination/get-combination level)]
    (with-redefs [rand-nth (constantly code)
                  println (spy/mock (fn [& args]))]
      (t/is (do (sut/-main "auto")
                (spy/called-with? println code))))))

(t/deftest prints-level-when-game-starts
  (with-redefs [sut/print-level (spy/mock (fn [_] nil))
                domain/play (spy/mock (fn [_] nil))]
    (t/are [switch] (do
                      (spy/reset-spy! sut/print-level)
                      (sut/-main switch)
                      (spy/called-once? sut/print-level))
      "computer-guesses"
      "I-guess"
      "auto")))

(t/deftest digits-are-sorted-on-printable-level
  (let [level {:digits #{1 2 3}}]
    (t/is (= (:digits (sut/printable level)) [1 2 3]))))
