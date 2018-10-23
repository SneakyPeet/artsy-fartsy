(ns art.utils)

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
