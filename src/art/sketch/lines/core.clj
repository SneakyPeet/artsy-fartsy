(ns art.sketch.lines.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil.middlewares.bind-output :as bind-output]
            [art.utils :as u]))

(defn- line [col-count line-ends i]
  (let [[pcol prow] (u/grid-position col-count i)]
    {:i i
     :x1 (rand-int line-ends)
     :y1 (rand-int line-ends)
     :x2 (rand-int line-ends)
     :y2 (rand-int line-ends)
     :px pcol
     :py prow}))


(defn generate [line-ends rows cols w% h%]
  {:rows rows
   :cols cols
   :w% w%
   :h% h%
   :line-ends line-ends
   :tiles
   (->> (range (* rows cols))
        (map #(line cols line-ends %)))})


(defn setup [r f]
  (fn []
    (q/color-mode :hsb)
    (q/frame-rate r)
    (f)))


(defn draw
  [width height background color {:keys [rows cols w% h% line-ends tiles]}]
  (let [padding-left (* (/ width 100) (/ (- 100 w%) 2))
        padding-top (* (/ height 100) (/ (- 100 h%) 2))
        width (* w% (/ width 100))
        height (* h% (/ height 100))
        px-offset (/ width cols)
        py-offset (/ height rows)
        lx-offset (/ px-offset (dec line-ends))
        ly-offset (/ py-offset (dec line-ends))]
    (q/background background)
    (q/stroke 0)
    (q/stroke-weight 2)
    (q/with-translation [padding-left padding-top]
      (doseq [{:keys [px py x1 y1 x2 y2] :as tile} tiles]
        (color tile)
        (let [lx1 (+ (* px px-offset) (* x1 lx-offset))
              lx2 (+ (* px px-offset) (* x2 lx-offset))
              ly1 (+ (* py py-offset) (* y1 ly-offset))
              ly2 (+ (* py py-offset) (* y2 ly-offset))]
          (q/line lx1 ly1 lx2 ly2))))))


(defn next [f]
  (fn [_]
    (f)))
