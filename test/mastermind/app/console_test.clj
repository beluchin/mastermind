(ns mastermind.app.console-test
  (:require [clojure.test :as t]
            [mastermind.app.console :as sut]
            [returning :refer [returning-fn]]
            [spy.core :as spy]))

(t/deftest read-until-no-error-semantics
  (t/testing "reads until no errors"
    (with-redefs [read-line (spy/stub)]
      (t/is (= 1234
               (sut/read-until-no-error (returning-fn {:error :x} {:error :another-error} 1234))))))

  (t/testing "translates errors"
    (with-redefs [read-line (spy/stub)
                  println (spy/spy println)]

      (t/are [e txt] (do (sut/read-until-no-error (returning-fn {:error e} 1234))
                         (spy/called-with? println txt))
        :not-a-number (:not-a-number sut/error-dict) ;; known errors
        :unknown-error (:default sut/error-dict))))) ;; catch-all
