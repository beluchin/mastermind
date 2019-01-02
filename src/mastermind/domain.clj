(ns mastermind.domain)

(defprotocol Playable
  (get-next-guess [game])
  (get-answer [game guess])
  (notify [game guess answer])
  (num-digits [game]))

(defn play [game]
  (loop []
    (let [guess (get-next-guess game)
          answer (get-answer game guess)]
      (when-not (= (num-digits game) (:ok answer))
        (notify game guess answer)
        (recur)))))
