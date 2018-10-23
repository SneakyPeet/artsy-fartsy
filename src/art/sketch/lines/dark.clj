(ns art.sketch.lines.dark
  (:require [quil.core :as q]
            [art.utils :as u]
            [art.defaults :as d]
            [art.sketch.lines.core :as l]))

(def width 1500)
(def height 500)
(def rows 50)
(def cols 150)


(defn gen-new []
  (q/save-frame "./renders/lines/life/####.png")
  (l/generate 2 rows cols 80 80))


(defn assension [tile]
  (q/stroke 150
            255
            (+ 20 (Math/pow (- rows (:py tile)) 1.5))))


(defn alternate [tile]
  (let [n 70
        l 130
        d 255
        cl 255
        cd 150
        should-lighten? (> (rand-int 100) n)]
    (q/stroke (if should-lighten? cl cd)
              (if should-lighten? l d)
              (+ 20 (Math/pow (- rows (:py tile)) 1.5)))))


(defn sprinkles [tile]
  (let [n 92
        should-sprinkle? (> (rand-int 100) n)]
    (q/stroke (if should-sprinkle? (rand-int 256) 150)
              (if should-sprinkle? 255 200)
              (if should-sprinkle? 150 50))))


(defn sprinkles-light [tile]
  (let [n 88
        should-sprinkle? (> (rand-int 100) n)
        c [220 60 120 160]]
    (q/stroke (if should-sprinkle? (nth c (rand-int (count c))) 150)
              (if should-sprinkle? 255 0)
              220)))


(defn life [tile]
  (let [n 20
        check-size (- 200
                      (* 0.65 (int (Math/pow (:px tile) 1.14)))
                      (* 0.9 (int (Math/pow (Math/abs (- (:py tile) (/ rows 2))) 1.74))))
        should-sprinkle?
        (and (even? (:i tile))
             (even? (:px tile))
             (even? (:py tile))
             (> (rand-int check-size) n))]
    (q/stroke
     (- 75 (* 0.5 (:px tile)))
     (if should-sprinkle? 200 0)
     (if should-sprinkle? (- 220 (* 1 (:px tile))) 255))))


(defn pattern [tile]
  (let [n 80
        should-sprinkle? (> (rand-int 100) n)
        c [10 120]]
    (q/stroke (if should-sprinkle? (nth c (rand-int (count c))) 150)
              0
              (if should-sprinkle? 255 220))))


(defn draw [state]
  (l/draw width height (q/color 255) life state))


(q/defsketch lines-sketch
  :title "dark lines"
  :size [width height]
  :setup (l/setup 1 gen-new)
  :update (l/next gen-new)
  :draw draw
  :features d/features
  :middleware d/middleware)
