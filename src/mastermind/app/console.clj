(ns mastermind.app.console)

(defn printable [level]
  (update level :digits #(sort (vec %))))

(defn print-level [l]
  (print (printable l)))

(defn game-started [_]
  (print-level 42))
