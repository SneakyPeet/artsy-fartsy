(ns art.sketch.pixelfy
  (:require [quil.core :as q]
            [art.utils :as u]
            [art.defaults :as d]))


  (def image-path "resources/lumberjack.JPG")
(def width 900)
(def height 900)
(def cols 40)
(def rows 40)

(defn setup []
  (q/background 255)
  (q/no-loop)
  (q/color-mode :hsb 360 100 100 1.0)
  (let [img (q/load-image image-path)
        base-pixels (u/derive-pixels rows cols img)]
    base-pixels))


(defn draw
  [pixels]
  (let [dx (/ width cols)
        dy (/ height rows)]
    (doseq [{:keys [px py color]} pixels]
      (let [x1 (* px dx)
            y1 (* py dy)]
        (q/fill color)
        (q/no-stroke)
        (q/rect x1 y1 dx dy)))))


(q/defsketch pixely
  :title "pixelfy"
  :size [width height]
  :setup setup
  :update identity
  :draw draw
  :features d/features
  :middleware d/middleware)
