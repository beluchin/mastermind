(ns returning-test
  (:require  [clojure.test :as t]
             [returning :as sut]))

(t/deftest semantics
  (let [f (sut/returning-fn 1 2 3)]
    (t/is (= 1 (f))) 
    (t/is (= 2 (f :can-take-any-arg)))
    (t/is (= 3 (f :multiple :args :too)))
    (t/is (nil? (f :from-here-on-out)))))
