(ns mastermind.app.user-guesses.console-test
  (:require [mastermind.app.user-guesses.console :as sut]
            [clojure.test :as t]
            [mastermind.domain :as domain]))

(comment 

;; 42 (a java.lang.Long) cannot be cast to clojure.lang.IObj
(def a-foo (with-meta 42 {`bar (constantly "hello")}))

(defprotocol Foo
  :extend-via-metadata true
  (bar [foo]))
(def a-foo (with-meta {} {`bar (constantly "hello")}))
(meta a-foo)
(bar a-foo)

(defrecord Game [level hidden]
  Foo
  (bar [game] 42))
(def game (Game. :easy 123))
(bar game)

)
