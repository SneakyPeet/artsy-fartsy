(ns art.sketch.photolines.core
  (:require [quil.core :as q]
            [infix.macros :refer [$=]]
            [art.utils :as u]
            [art.defaults :as d]))

(def id :lumber-dark3)
(def save? false)

(def drawings
  {:lumber-dark
   {:path "resources/lumberjack.JPG"
    :width 900 :height 900 :margin 50
    :lines 70
    :linepoints 200
    :background (fn [_] (q/background 360))
    :canvas-color (fn [_] (q/fill 0))
    :main-stroke (fn [_] (q/stroke 200 0 100 0.5))
    :loop-n 100
    :rd 0.1
    :correction 10
    :grouping :bright
    :offsetter :bright}
   :lumber-dark3
   {:path "resources/lumberjack3.JPG"
    :width 900 :height 900 :margin 50
    :lines 40
    :linepoints 200
    :background (fn [_] (q/background 360))
    :canvas-color (fn [_] (q/fill 0))
    :main-stroke (fn [_] (q/stroke 200 0 100 0.5))
    :loop-n 100
    :rd 0.12
    :correction 10
    :grouping :bright
    :offsetter :bright}})


(def conf (get drawings id))
(defn s [k] (get conf k))
(defn f [k & args]
  (if args
    (apply (s k) args)
    ((s k))))


(defn setup []
  (q/color-mode :hsb 360 100 100 1.0)
  (f :background conf)

  (let [{:keys [path lines linepoints grouping margin width height correction]} conf
        img (q/load-image path)
        base-pixels (u/derive-pixels linepoints lines img)
        offset (->> base-pixels
                    (group-by grouping)
                    (sort-by (fn [[k v]] (count v)))
                    last
                    first)]
    (f :canvas-color conf)
    (q/rect (+ margin correction) margin
            ($= width - (margin * 2) - correction) ($= height - (margin * 2) - correction))
    (q/no-fill)
    {:i 100 :p base-pixels :offset offset}))


(defn draw
  [{:keys [i p offset]}]
  (when save?
    (q/save-frame (str "./renders/photolines/" (name id) "/img####.png")))
  (when (<= i 0)
    (when save?
      (q/save (str "./renders/photolines/" (name id) ".png")))
    (q/no-loop))
  (f :main-stroke conf)
  #_(do
    (q/stroke 200 0 100 0.2))
  #_(do
    (q/stroke 200 20 0 0.05)
    (cond
        (= 0 (mod i 30))
        (q/stroke 270 50 100 0.5)
        (= 0 (mod i 50))
        (q/stroke 360 50 100 0.5)
        (= 0 (mod i 20))
        (q/stroke 120 50 100 0.5)))
  (let [{:keys [width height margin lines linepoints rd offsetter correction]} conf
        width ($= width - (margin * 2))
        height ($= height - (margin * 2))
        dx (/ width lines)
        dy (/ height linepoints)
        cols (group-by :px p)]
      (doseq [[px rows] cols]
        (q/begin-shape)
        (doseq [{:keys [hue bright sat py color] :as row} rows]
          (let [x1 ($= px * dx)
                y1 ($= py * dy + margin)
                off (* rd (Math/abs (- offset (f :offsetter row))))
                off (q/random (- off) off)
                x1 (+ x1 off margin correction)]
            (q/vertex x1 y1)))
        (q/end-shape))))


(defn next-state [state]
  (update state :i dec))


(q/defsketch photolines
  :title "photolines"
  :size [width height]
  :setup setup
  :update next-state
  :draw draw
  :features d/features
  :middleware d/middleware)
