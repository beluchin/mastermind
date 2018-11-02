(ns validation)

(defn decorated-for-ensure-> [f]
  (fn [& args]
    (let [x (first args)]
      (if (and (map? x) (contains? x :error))
        x
        (apply f args)))))

(defn- decorate [forms]
  (let [decorate-symbol #(list (list `decorated-for-ensure-> %))
        decorate-seq (fn [form] `((decorated-for-ensure-> ~(first form)) ~@(next form)))]
    (map #(if (symbol? %) (decorate-symbol %) (decorate-seq %)) forms)))

(defmacro ensure->
  "Threads the expr through the (decorated) forms as the first arg.
  The forms are decorated with decorated-for-ensure-> i.e. the first :error
  is returned"
  ([x] x)
  ([x & forms] `(-> ~x ~@(decorate forms))))