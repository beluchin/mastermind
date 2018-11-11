(ns mastermind)

(def levels
  {:easy {:num-digits 3, :dups false, :digits (set (range 0 6))}
   :hard {:num-digits 4, :dups false, :digits (set (range 0 10))}
   :expert {:num-digits 4, :dups true, :digits (set (range 0 10))}})