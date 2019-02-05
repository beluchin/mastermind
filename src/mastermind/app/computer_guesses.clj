(ns mastermind.app.computer-guesses
  (:require [mastermind.app.code-breaker.machine :as machine]
            [mastermind.app.code-maker.user :as user]
            [mastermind.app.level :as level]))

(def ^:const default-level (:expert level/levels))

(defn new-game []
  {:level default-level
   :code-breaker (machine/new-code-breaker default-level)
   :code-maker (user/new-code-maker default-level)})
