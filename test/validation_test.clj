(ns validation-test
  (:require [clojure.test :refer :all]
            [validation :refer :all]))

(deftest thread-first-ensure-threads-the-initial-value
  (is (= 42 (ensure-> 42))))

(deftest thread-first-ensure-inserts-x-as-the-second-item-in-first-form
  (is (= 43 (ensure->
              42
              (+ 1)))))

(deftest thread-first-ensure-makes-each-form-into-a-list
  (is (= 42 (ensure->
              42
              +))))

(deftest thread-first-ensure-threads-error-given-symbols
  (is (= {:error :on-first-fn}
         (let [returns-error (constantly {:error :on-first-fn})]
           (ensure->
             42
             returns-error
             +)))))

(deftest thread-first-ensure-threads-error-given-lists
  (is (= {:error :on-first-fn}
         (ensure->
           42
           ((constantly {:error :on-first-fn}))
           +))))

