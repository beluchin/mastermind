(ns mastermind.app.console-test
  (:require [clojure.test :as t]
            [mastermind.app.console :as sut]
            [returning :refer [returning-fn]]))

(t/deftest reads-until-no-error 
  (with-redefs [read-line (constantly nil)]
    (t/is (= 1234
             (sut/read-until-no-error (returning-fn {:error :x} {:error :another-error} 1234))))))
