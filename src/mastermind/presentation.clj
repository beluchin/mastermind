(ns mastermind.presentation)

(defn int-or-nil [x]
  (try (Integer/parseInt x) (catch NumberFormatException _ nil)))

(defn read-trimmed-line []
  (clojure.string/trim (read-line)))

(defn print-level [l]
  (println (update l :digits #(sort (vec %)))))

