(ns art.sketch.lines.dark
  (:require [quil.core :as q]
            [art.utils :as u]
            [art.defaults :as d]
            [art.sketch.lines.core :as l]))

(def width 1000)
(def height 1000)
(def w% 80)
(def h% 10)
(def col 30)
(def row 7)

(defn gen-new [] (l/generate 2 row col w% h%))


(defn color [{:keys [px py]}]
  (q/stroke (+ 125 (* 25 (/ py row)))
            255
            (let [n 5
                  s 40]
              (cond
                (< px n) (* s (inc px))
                (> px (- col n)) (* s (identity (- col px)))
                :else 255))))


(defn draw [state]
  (l/draw width height (q/color 0 0 50) color state))


(defn next-state [state]
  (update
   state :tiles
   (fn [tiles]
     (->> tiles
          (map
           (fn [tile]
             (update tile :px
                     #(if (= 0 %)
                        (dec col)
                        (dec %)))))))))


(q/defsketch lines-sketch
  :title "dark lines"
  :size [width height]
  :setup (l/setup 10 gen-new)
  :update next-state
  :draw draw
  :features d/features
  :middleware d/middleware)
