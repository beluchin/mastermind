(ns mastermind.app.user-guesses
  (:require [mastermind.app.level :refer :all]
            [mastermind.app.utils :as u]
            [mastermind.domain :as domain]
            [validation :as v]
            [mastermind.app.console :refer [read-trimmed-line]]))

(defn- an-int [s]
  (let [n (u/int-or-nil s)]
    (if (nil? n)
      {:error :not-a-number}
      n)))

(defn guess-or-error [txt]
  (v/ensure-> txt
              an-int))

(defrecord Game [level]
  domain/Playable
  (get-next-guess [game] (guess-or-error (read-trimmed-line)))
  (get-answer [_ _])
  (notify [_ _ _])
  (num-digits [_]))

(defn new-game
  "new game for the user to guess. Contains the :level"
  []
  (Game. (:easy levels)))

(comment

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
