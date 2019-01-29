(ns mastermind.app.auto
  (:require [mastermind.app.level :as level]
            [mastermind.domain :as domain]))

(def ^:const default-level (level/levels :expert))

(defn new-game []
  {:level default-level
   :code-breaker (reify domain/CodeBreaker
                   (get-next-guess* [_]
                     (let [guess (rand-nth [])]
                       (println guess)
                       guess)))
   :code-maker (reify domain/CodeMaker
                 (get-feedback [_ _] {:ok (default-level :num-digits)}))})

(comment
  ({:a 1} :a) ;; 1 

)
