(ns mastermind.app.auto
  (:require [mastermind.app.code-breaker.machine :as machine-cb]
            [mastermind.app.code-maker.machine :as machine-cm]
            [mastermind.app.console :as console]
            [mastermind.app.level :as level]
            [mastermind.domain :as domain]))

(def ^:const default-level (level/levels :expert))

(defn new-game []
  {:level default-level
   :code-breaker (let [cb (machine-cb/new-code-breaker default-level)]
                   (reify domain/CodeBreaker
                     (get-next-guess [this]
                       (let [result (domain/get-next-guess cb)]
                         (console/display result)
                         result))
                     (notify [this guess feedback]
                       (console/display feedback)
                       (domain/notify cb guess feedback))))
   :code-maker (machine-cm/new-code-maker default-level)})

(comment
  ({:a 1} :a) ;; 1 

)
