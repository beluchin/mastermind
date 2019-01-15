(ns mastermind.app.user-guesses
  (:require [mastermind.app.console :as console]
            [mastermind.app.level :as level]
            [mastermind.app.utils :as u]
            [mastermind.domain :as domain]
            [mastermind.domain.combination :as combination]
            [mastermind.domain.evaluation :as evaluation]
            [validation :as v]
            [mastermind.app.user-guesses.error :as error]))

(defn- an-int [s]
  (let [n (u/int-or-nil s)]
    (if (nil? n)
      {:error :not-a-number}
      n)))

(defn- no-zero-in-front [s]
  (if (not= \0 (first s))
    s
    {:error :zero-in-front}))

(defn guess-or-error [txt]
  (v/ensure-> txt
              no-zero-in-front
              an-int))

(defrecord Game [level hidden]
  domain/Playable
  (get-next-guess [_] (console/read-until-no-error error/error-dict guess-or-error))
  (get-answer [_ guess] (evaluation/evaluation guess hidden))
  (notify [_ _ answer] (console/display answer))
  (num-digits [_] (:num-digits level)))

(def default-level (:easy level/levels))

(defn new-game
  "new game for the user to guess. Contains the :level"
  []
  (let [level default-level] (Game. level (combination/get-combination level))))


(comment

(:error :a)

(defprotocol Foo 
  (foo [_])
  (bar [_]))
(def a-foo
  (reify Foo
    (foo [_] 42)
    (bar [_]))) ;; left unimplemented evaluates to nil
(foo a-foo)
(bar a-foo) ;; => nil

)
