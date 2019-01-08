(ns mastermind.app.computer-guesses
  (:require [mastermind.domain :as domain]))

(defrecord Game []
  domain/Playable
  (get-next-guess [_])
  (get-answer [_ guess])
  (notify [_ guess answer])
  (num-digits [_]))

(defn new-game [] (->Game))



