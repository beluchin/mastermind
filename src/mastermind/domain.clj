(ns mastermind.domain)

(defprotocol CodeMaker
  (get-feedback [this guess]))

(defprotocol CodeBreaker
  (get-next-guess [this])
  (notify [this guess feedback]))

(defn play 
  ([game]
   (play (get-in game [:level :num-digits]) (game :code-breaker) (game :code-maker)))
  ([num-digits code-breaker code-maker]
   (loop []

     ;; TODO: replace the loop with something like do-until or repeatedly
     ;; or something like that ...

     (let [guess (get-next-guess code-breaker)
           feedback (get-feedback code-maker guess)]
       (when-not (= num-digits (:ok feedback))
         (notify code-breaker guess feedback)
         (recur))))))
