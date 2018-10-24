(ns art.utils
  (:require [quil.core :as q]
            [infix.macros :refer [$=]]))


;;;; GRID

(defn grid-position
  "given the grid width and index, returns a vector containing column and row"
  [col-count i]
  (let [col (mod i col-count)
        row (/ (- i col) col-count)]
    [col row]))


(defn grid-index
  "given the grid width and the row and column, returns the index"
  [col-count row col]
  (+ col (* row col-count)))


;;;; PIXELS

(def pixel-vals (juxt q/hue q/saturation q/brightness q/alpha))

(defn pixel-info [cols i quil-pixel]
  (let [[hue sat bright alpha] (pixel-vals quil-pixel)
        [px py] (grid-position cols i)]
    {:i i
     :px px
     :py py
     :hue hue
     :sat sat
     :bright bright
     :alpha alpha
     :color (q/color hue sat bright alpha)}))


(defn derive-pixels [rows cols img]
  (let [w (.width img)
        h (.height img)
        dx (int ($= w / cols))
        dy (int ($= h / rows))
        dcols (->> (range cols) (map #(* % dx)))
        drows (->> (range rows) (map #(* % dy)))
        indexes (for [y drows
                      x dcols]
                  (grid-index w y x))
        pixels (q/pixels img)]
    (->> indexes
         (map #(aget pixels %))
         (map-indexed #(pixel-info cols %1 %2)))))
