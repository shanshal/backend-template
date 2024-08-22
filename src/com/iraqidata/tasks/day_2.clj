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

total-flights
;; => 336776

;; 1 point

;; 2. How many unique carriers were there in total?
(def unique-carriers
  (-> ds
      (tc/select-columns :carrier)
      (tc/unique-by :carrier)
      (tc/row-count)))

unique-carriers
;; => 16

;;

;; 3. How many unique airports were there in total?
(def airports
  (-> ds
      (tc/select-columns [:origin :dest])
      (tc/unique-by [:dest])
      (tc/row-count)))

;; 0 point

;; 4. What is the average arrival delay for each month?
(def avg-arr-delay
  (-> ds
      (tc/group-by :month)
      (tc/aggregate {:avg-arr-delay
                     (fn [col]
                       (tc/mean col :arr-delay))})))

;; 1 point

;; Optional: Use the `airlines` dataset to get the name of the carrier with the
;; highest average distance.

(def top-distance
  (-> ds
      (tc/group-by :carrier)
      (tc/aggregate {:avg-distance (fn [col] (tc/mean col :distance))})
      (tc/order-by :avg-distance-summary :desc)
      (tc/first)))

;; Half point

(def airlines
  (tc/dataset "./resources/data/airlines.csv"
              {:key-fn keyword}))

;; Final score: 2.5 points
