(ns mastermind.domain)

(defprotocol Playable
  (get-next-guess [game])
  (get-answer [game guess])
  (notify [game guess answer])
  (num-digits [game]))

(defprotocol CodeMaker
  (get-feedback [this guess]))

(defprotocol CodeBreaker
  (get-next-guess* [this])
  (notify* [this feedback]))

(defn play 
  ([game]
   (play (game :level) (game :code-breaker) (game :code-maker))
   #_(loop []
     
       ;; TODO: replace the loop with something like do-until or repeatedly
       ;; or something like that ...

       (let [guess (get-next-guess game)
             answer (get-answer game guess)]
         (when-not (= (num-digits game) (:ok answer))
           (notify game guess answer)
           (recur)))))

  ([num-digits code-breaker code-maker]
   (get-next-guess* code-breaker)
   (println (rand-nth []))
   #_(loop []

     ;; TODO: replace the loop with something like do-until or repeatedly
     ;; or something like that ...

     (let [guess (get-next-guess* code-breaker)
           feedback (get-feedback code-maker guess)]
       (when-not (= num-digits (:ok feedback))
         (notify* code-breaker feedback)
         (recur))))))
