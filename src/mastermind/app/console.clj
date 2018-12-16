(ns mastermind.app.console)

(defn print-level [_] (throw (UnsupportedOperationException.)))

(defn game-started [_]
  (print-level 42))
