(ns thread-first-ensure-test
  (:require [clojure.test :refer :all]
            [validation :refer :all]))

(deftest threads-the-initial-value
  (is (= 42 (ensure-> 42))))

(deftest inserts-x-as-the-second-item-in-first-form
  (is (= 43 (ensure->
              42
              (+ 1)))))

(deftest makes-each-form-into-a-list
  (is (= 42 (ensure->
              42
              +))))

(deftest threads-error-given-symbols
  (is (= {:error :on-first-fn}
         (let [returns-error (constantly {:error :on-first-fn})]
           (ensure->
             42
             returns-error
             +)))))

(deftest threads-error-given-lists
  (is (= {:error :on-first-fn}
         (ensure->
           42
           ((constantly {:error :on-first-fn}))
           +))))