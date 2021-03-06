(ns recursion)

(defn product [coll]
  (if (empty? coll)
    1
    (* (first coll) (product (rest coll)))))

(defn singleton? [coll]
  (if (empty? coll)
    false
    (if (empty? (rest coll))
      true
      false)))

(defn my-last [coll]
  (if (empty? coll)
    nil
    (let [f (first coll)
          r (rest coll)]
      (if (empty? r)
        f
        (my-last r)))))

(defn max-element [a-seq]
  (if (empty? a-seq)
    nil
    (let [f (first a-seq)
          r (rest a-seq)]
      (if (empty? r)
        f
        (max f (max-element r))))))

(defn seq-max [seq-1 seq-2]
  (let [count-seq-1 (count seq-1)
        count-seq-2 (count seq-2)]
  (if (> count-seq-1 count-seq-2)
    seq-1
    seq-2)))

(defn longest-sequence [a-seq]
  (if (empty? a-seq)
    nil
    (let [f (first a-seq)
          r (rest a-seq)]
      (if (empty? r)
        f
        (seq-max f (longest-sequence r))))))

(defn my-filter [pred? a-seq]
  (if (empty? a-seq)
    a-seq
    (let [f (first a-seq)
          r (rest a-seq)
          fil-r (my-filter pred? r)]
      (if (pred? f)
        (cons f fil-r)
        fil-r))))

(defn sequence-contains? [elem a-seq]
  (cond
    (empty? a-seq)
      false
    (= (first a-seq) elem)
      true
    :else
      (sequence-contains? elem (rest a-seq))))

(defn my-take-while [pred? a-seq]
  (if (empty? a-seq)
    '()
    (let [f (first a-seq)
          r (rest a-seq)]
      (if (pred? f)
        (cons f (my-take-while pred? r))
        '()))))

(defn my-drop-while [pred? a-seq]
  (if (empty? a-seq)
    '()
    (let [f (first a-seq)
          r (rest a-seq)]
      (if (pred? f)
        (my-drop-while pred? r)
        a-seq))))

(defn seq= [a-seq b-seq]
  (cond
    (and (empty? a-seq) (empty? b-seq)) true
    (or (empty? a-seq) (empty? b-seq)) false
    (not= (first a-seq) (first b-seq)) false
    :else (seq= (rest a-seq) (rest b-seq))))

(defn my-map [f seq-1 seq-2]
  (if (or (empty? seq-1) (empty? seq-2))
    '()
    (cons (f (first seq-1) (first seq-2)) (my-map f (rest seq-1) (rest seq-2)))))

(defn power [n k]
  (if (= 0 k)
    1
    (* n (power n (dec k)))))

(defn fib [n]
  (cond
    (= n 0) 0
    (= n 1) 1
    :else (+ (fib (- n 1)) (fib (- n 2)))))

(defn my-repeat [how-many-times what-to-repeat]
  (if (<= how-many-times 0)
    '()
    (cons what-to-repeat (my-repeat (dec how-many-times) what-to-repeat))))

(defn my-range [up-to]
  (if (<= up-to 0)
    '()
    (cons (dec up-to) (my-range (dec up-to)))))

(defn tails [a-seq]
  (if (empty? a-seq)
    '(())
    (cons (seq a-seq) (tails (rest a-seq)))))

(defn inits [a-seq]
  (let [ra-seq (reverse a-seq)]
    (reverse (map reverse (tails ra-seq)))))

(defn rotations [a-seq]
  (if (empty? a-seq)
    '(())
    (letfn [(rotations-div [left right]
              (if (empty? left)
                []
                (cons (concat left right) (rotations-div (rest left) (conj right (first left))))))]
      (rotations-div a-seq []))))

(defn my-frequencies-helper [freqs a-seq]
  (if (empty? a-seq)
    freqs
    (let [el (first a-seq)
          ra-seq (rest a-seq)
          new-freqs (assoc freqs el (inc (get freqs el 0)))]
      (my-frequencies-helper new-freqs ra-seq))))

(defn my-frequencies [a-seq]
  (my-frequencies-helper {} a-seq))

(defn un-frequencies [a-map]
  (if (empty? a-map)
    '()
    (let [[value n] (first a-map)]
      (concat (repeat n value) (un-frequencies (rest a-map))))))

(defn my-take [n coll]
  (if (or (= 0 n) (empty? coll))
    '()
    (cons (first coll) (my-take (dec n) (rest coll)))))

(defn my-drop [n coll]
  (cond
    (empty? coll) '()
    (<= n 0) coll
    :else (my-drop (dec n) (rest coll))))

(defn halve [a-seq]
  (if (empty? a-seq)
    '(() ())
    (let [h (int (/ (count a-seq) 2))]
      (loop [c h, lf [], rg a-seq]
        (if (= c 0)
          (list lf rg)
          (recur (dec c) (conj lf (first rg)) (rest rg)))))))

(defn seq-merge [a-seq b-seq]
  (cond
    (and (empty? a-seq) (empty? b-seq)) '()
    (empty? a-seq) b-seq
    (empty? b-seq) a-seq
    :else (let [fa (first a-seq)
                fb (first b-seq)
                ra (rest a-seq)
                rb (rest b-seq)]
            (if (< fa fb)
              (cons fa (seq-merge ra b-seq))
              (cons fb (seq-merge a-seq rb))))))

(defn merge-sort [a-seq]
  (if (< (count a-seq) 2)
    a-seq
    (let [[first-half second-half] (halve a-seq)]
      (seq-merge (merge-sort first-half) (merge-sort second-half)))))

;; (defn split-into-monotonics [a-seq]
;;   (if (empty? a-seq)
;;     '()
;;     (let [parts (filter #(> (count %) 1) (inits a-seq))
;;           decr (apply max (map #(if (apply >= %) (count %) 0) parts))
;;           grow (apply max (map #(if (apply <= %) (count %) 0) parts))
;;           monotonic (max grow decr)]
;;       (cons (take monotonic a-seq) (split-into-monotonics (drop monotonic a-seq))))))

(defn split-into-monotonics [a-seq]
  (if (empty? a-seq)
    '()
    (let [grow (count (take-while <= a-seq))
          decr (count (take-while >= a-seq))
          ]
      )

(defn permutations [a-set]
  [:-])

(defn powerset [a-set]
  [:-])

