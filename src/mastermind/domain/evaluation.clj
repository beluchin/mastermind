(ns mastermind.domain.evaluation
  (:require [clojure.set :as set]))

(defn- value-counts
  "{:1 1, :2 1} -> {1 2} ; only one value (1) and appears in two positions
   {:0 1, :1 2, :2 1} -> {1 2, 2 1} ; two values: 1 appears twice and 2 once"
  [amap]
  (->> amap
       (group-by second)
       (map (fn [[f s]] [f (count s)]))
       (into {})))

(defn- map-intersection
  "{:a 1}, {:a 1} -> {:a 1}
   {:a 1}, {:a 2} -> {}
   {:a 1, :b 3}, {:a 2, :b 3} -> {:b 3}"
  [map1 map2]
  (into {} (set/intersection (set map1) (set map2))))

(defn- so-so-count
  "takes two (partial) combinations that do *not* contain ok values

  {:0 1, :1 2}, {:0 2, :2 1} -> 2
  {:0 1}, {:1 1} -> 1
  {:0 1}, {:1 2} -> 0"
  [amap1 amap2]
  (let [m1vc (value-counts amap1)
        m2vc (value-counts amap2)
        min-count-by-value-total (fn [r k v] (+ r (min v (get m2vc k 0))))]
    (reduce-kv min-count-by-value-total 0 m1vc)))

(defn- to-map
  "1234 -> {:0 1, :1 2, :2 3, :3 4}
   1123 -> {:0 1, :1 1, :2 3, :3 4}"
  [combination]
  (let [position-as-kwd (fn [[f s]] [(keyword (str f)) s])]
    (->> combination
         str
         (map #(Character/digit % 10))
         (map-indexed vector)
         (map position-as-kwd)
         (into {}))))

(defn evaluation [comb1 comb2]
  (let [m1 (to-map comb1)
        m2 (to-map comb2)
        ok-pos (keys (map-intersection m1 m2))
        remaining-m1 (apply dissoc m1 ok-pos)
        remaining-m2 (apply dissoc m2 ok-pos)]
    {:ok    (count ok-pos)
     :so-so (so-so-count remaining-m1 remaining-m2)}))
