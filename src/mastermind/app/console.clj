(ns mastermind.app.console)

(def errors-in-spanish
  {:not-a-number "hmm, solo numeros"
   :default "algo no esta bien"})

(def error-dict errors-in-spanish)

(defn read-trimmed-line [] (read-line))

(defn read-until-no-error [f]
  (letfn [(translate [e] (e error-dict (:default error-dict)))
          (error [{e :error}] (when e (println (translate e)) e))]
    (first (drop-while error (repeatedly #(f (read-trimmed-line)))))))


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

(letfn [(translate [e] (e errors-in-spanish (:default errors-in-spanish)))
        (error [{e :error}] (when e (println (translate e)) e))]
  (error {:error :not-translatable}))

(:x errors-in-spanish (:default errors-in-spanish))
)
