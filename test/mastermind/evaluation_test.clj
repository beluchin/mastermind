(ns mastermind.evaluation-test
  (:require [clojure.test :refer :all]
            [mastermind.evaluation :refer :all]))

(deftest evaluation-all
  (is (= 4 (:ok (evaluation 1234 1234))))
  (is (not= 4 (:ok (evaluation 1234 4567))))
  ; ok's
  (is (= 1 (:ok (evaluation 1234 1567))))
  (is (= 2 (:ok (evaluation 1234 1784))))
  (is (= 2 (:ok (evaluation 1234 1335))))
  ; so-so's
  (is (= 1 (:so-so (evaluation 1234 5671))))
  (is (= 2 (:so-so (evaluation 1234 5612))))
  (is (= 4 (:so-so (evaluation 1234 4321))))
  ;
  (is (= {:ok 1, :so-so 1} (evaluation 1234 1562)))
  (is (= {:ok 1, :so-so 1} (evaluation 1234 1522)))
  (is (= {:ok 2, :so-so 0} (evaluation 1234 1222))))