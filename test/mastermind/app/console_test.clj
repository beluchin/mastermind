(ns mastermind.app.console-test
  (:require [clojure.test :as t]
            [mastermind.app.console :as sut]
            [returning :refer [returning-fn]]
            [spy.core :as spy]))

(t/deftest read-until-no-error-semantics
  (t/testing "reads until no errors"
    (with-redefs [read-line (spy/stub "whatever")]
      (t/is (= 1234
               (sut/read-until-no-error
                {}
                (returning-fn {:error :x} {:error :another-error} 1234))))))

  (t/testing "translates errors"
    (with-redefs [read-line (spy/stub "whatever")
                  println (spy/spy println)]

      (t/are [e txt] (do (sut/read-until-no-error
                          {:not-a-number "not a number"
                           :default "catch all"}
                          (returning-fn {:error e} 1234))
                         (spy/called-with? println txt))
        :not-a-number "not a number" ;; known errors
        :unknown-error "catch all")))  ;; catch-all

  (t/testing "missing :default in error-dict"
    (with-redefs [read-line (spy/stub "whatever")
                  println (spy/spy println)]
      (t/is (do
              (sut/read-until-no-error {} (returning-fn {:error :anything} 1234))
              (spy/called-with? println "something is wrong")))))
  
  (t/testing "trimmed input"
    (with-redefs [read-line (spy/stub "     1234       ")]
      (t/is (= "1234" (sut/read-until-no-error {} identity))))))

(comment

(read-string "    1234        ") ;; => ClassCasException
(= "a1234" (read-string "              a1234          "))
)
