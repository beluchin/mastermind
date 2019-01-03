(ns returning)

(defn returning-fn 
  "returns a function that returns the args one at time e.g.:
  (def f (returning-fn 1 2 3))
  (f)                      ;; => 1
  (f :can-take-any-arg)    ;; => 2
  (f :multiple :args :too) ;; => 3

  taken from https://stackoverflow.com/a/48374397/614800"
  [x y & more]
  (let [a (atom (concat [x y] more))]
    (fn [& args]
      (let [r (first @a)]
        (swap! a rest)
        r))))

(comment

((fn [& more] more) 1 2 3 4)
(concat [1 2] [3])

)
