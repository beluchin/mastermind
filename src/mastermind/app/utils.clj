(ns mastermind.app.utils)

(defn int-or-nil [x]
  (try (Integer/parseInt x) (catch NumberFormatException _ nil)))
