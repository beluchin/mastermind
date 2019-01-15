(ns mastermind.app.user-guesses.error)

(def ^:private errors-in-spanish
  {:not-a-number "hmm, solo numeros"
   :zero-in-front "no se permiten ceros al inicio"
   :default "algo no esta bien"})

(def error-dict errors-in-spanish)
