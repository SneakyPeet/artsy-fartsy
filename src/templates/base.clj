(ns art.sketch.template.core
  (:require [quil.core :as q]
            [infix.macros :refer [$=]]
            [art.utils :as u]
            [art.defaults :as d]))

(def width 500)
(def height 500)

(defn setup []
  (q/background 255)
  {})


(defn draw
  [state]
  (q/fill 200)
  (q/rect 20 20 200 200))


(defn next-state [state]
  state)


(q/defsketch template
  :title "template"
  :size [width height]
  :setup setup
  :update next-state
  :draw draw
  :features d/features
  :middleware d/middleware)
