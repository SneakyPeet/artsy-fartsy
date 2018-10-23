(ns art.defaults
  (:require [quil.middleware :as m]))

(def middleware [m/pause-on-error m/fun-mode])

(def features [:keep-on-top :no-bind-output])
