(ns com.iraqidata.tasks.day-2
  (:require
   [clojure.string :as str]
   scicloj.clay.v2.api
   [tablecloth.api :as tc]
   tech.v3.datatype.casting))

(def ds (tc/dataset "./resources/data/flights.csv"
                    {:key-fn #(keyword (str/replace (name %) "_" "-"))}))

;; 1. How many flights were there in total?
(def total-flights
  (tc/row-count ds))


;; 2. How many unique carriers were there in total?
(def unique-carriers
  (-> ds
      (tc/select :carrier)
      (tc/distinct)
      (tc/row-count)))


;; 3. How many unique airports were there in total?
(def airports
  (-> ds
      (tc/select-columns [:origin :dest])
      (tc/distinct)
      (tc/row-count)))


;; 4. What is the average arrival delay for each month?
(def avg-arr-delay
  (-> ds
      (tc/group-by :month)
      (tc/aggregate {:avg-arr-delay (fn [col] (tc/mean col :arr-delay))})))


;; Optional: Use the `airlines` dataset to get the name of the carrier with the
;; highest average distance.
(def top-distance
  (-> ds
      (tc/group-by :carrier)
      (tc/aggregate {:avg-distance (fn [col] (tc/mean col :distance))})
      (tc/sort-by :avg-distance :desc)
      (tc/first)))

(def airlines
  (tc/dataset "./resources/data/airlines.csv"
              {:key-fn keyword}))
