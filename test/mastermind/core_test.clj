(ns mastermind.core-test
  (:require [clojure.test :as t]
            [mastermind.app.auto :as auto]
            [mastermind.app.code-breaker.machine :as machine-cb]
            [mastermind.app.code-maker.machine :as machine-cm]
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
  (let [level auto/default-level]
    (with-redefs [machine-cb/get-next-guess (returning/returning-fn
                                             1
                                             2)
                  machine-cb/notify (constantly nil)
                  machine-cm/get-feedback (returning/returning-fn 
                                           {:ok 0} 
                                           {:ok (:num-digits level)})
                  println (spy/spy println)]
      (t/is (do (sut/-main "auto")
                (= ['(1) '({:ok 0}) '(2)] (subvec (spy/calls println) 1)))))))

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

(comment
  (defn a [y] (+ 5 y)) 
  (def b (partial a 5))
  (b) ;; 10
  (defn a [y] (+ 10 y))
  (b) ;; 10
  (def b (partial (var a) 5))
  (b) ;; 15
  (defn a [y] (+ 5 y))
  (b) ;; 10

  (a 5) ;; 10
  ((var a) 5) ;; 10

  (symbol? 'a) ;; true
  (symbol? a) ;; false
  (type a) ;; mastermind.core_test$a
  (type 'a) ;; clojure.lang.Symbol
  (type #'a) ;; clojure.lang.Var
  a ;; #function[mastermind.core-test/a]
  (var a) ;; #'mastermind.core-test/a

  (def aa (var a))
  (type #'aa) ;; clojure.lang.Var
  (aa 5) ;; 10
  (#'aa 5) ;; 10
  aa ;; #'mastermind.core-test/a
  a ;; #function[mastermind.core-test/a]
  
  (var 5) ;; ClassCastException - 5 is not a symbol
  (var aa) ;; #'mastermind.core-test/aa
  )
