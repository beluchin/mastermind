(ns mastermind.app.user-guesses
  (:require [mastermind.app.code-breaker.user :as user]
            [mastermind.app.code-maker.machine :as machine]
            [mastermind.app.level :as level]))

(def ^:const default-level (:easy level/levels))

(defn new-game []
  (let [level default-level]
    {:level level
     :code-breaker (user/new-code-breaker level)
     :code-maker (machine/new-code-maker level)}))


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
