(ns mastermind.app.console
  (:require [clojure.string :as str]))

(declare read-trimmed-line)

(defn read-until-no-error [error-dict f]
  (letfn [(translate [e] (e error-dict (:default error-dict "something is wrong")))
          (error [{e :error}] (when e (println (translate e)) e))]
    (first (drop-while error (repeatedly #(f (read-trimmed-line)))))))

(def display println)

(defn- read-trimmed-line [] (str/trim (read-line)))


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

  ;; keyword functions take a default value
  (:x errors-in-spanish (:default errors-in-spanish)))
