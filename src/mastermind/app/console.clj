(ns mastermind.app.console)

(defn read-trimmed-line [] (read-line))

(defn read-until-no-error [f]
  (first (drop-while :error (repeatedly #(f (read-trimmed-line))))))


(comment

;; print many lines
(println "line 1" "line 2")

;; https://stackoverflow.com/a/48374397/614800
(let [a (atom ["a" "b"])]
  (defn f []
    (let [r (first @a)]
      (swap! a rest)
      r)))
(f) ;; "a"
(f) ;; "b"
(f) ;; nil

)
