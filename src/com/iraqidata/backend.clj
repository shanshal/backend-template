(ns com.iraqidata.backend
  (:require
   [clojure.pprint :as pprint]
   [clojure.string :as str]
   [muuntaja.core :as muuntaja]
   [reitit.ring :as ring]
   [reitit.coercion.malli :as malli]
   [reitit.ring.middleware.parameters :refer [parameters-middleware]]
   [reitit.ring.middleware.muuntaja
    :refer [format-negotiate-middleware format-request-middleware
            format-response-middleware]]
   [reitit.ring.coercion
    :refer [coerce-request-middleware coerce-response-middleware]]
   [ring.adapter.jetty :refer [run-jetty]]
   [tablecloth.api :as tc]))

;; # Simplest Form

(def app
  (ring/ring-handler
   (ring/router
    ["/api"
     ["/ping" {:get {:handler (fn [req]
                                {:status 200
                                 :body "Hello World!"})}}]
     ["/most-delays" {:get {:handler
                            (fn [_]
                              {:body (let [ds (tc/dataset "./resources/data/flights.csv"
                                                          {:key-fn #(keyword (str/replace (name %) "_" "-"))})
                                           airlines (tc/dataset "./resources/data/airlines.csv"
                                                                {:key-fn keyword})
                                           ds (-> (tc/inner-join ds airlines [:carrier])
                                                  (tc/group-by :name)
                                                  (tc/mean :arr-delay)
                                                  (tc/rename-columns [:name :data])
                                                  (tc/order-by :data :desc))]
                                       {:categories (-> ds
                                                        :name
                                                        vec)
                                        :series [{:name "Average Delay"
                                                  :data (-> ds
                                                            :data
                                                            vec)}]})})}
                      :post {:handler
                             (fn [req]
                               {:body
                                (let [_ (println req)
                                      n (-> req
                                            :query-params
                                            (get "n")
                                            Integer/parseInt)
                                      ds (tc/dataset "./resources/data/flights.csv"
                                                     {:key-fn #(keyword (str/replace (name %) "_" "-"))})
                                      airlines (tc/dataset "./resources/data/airlines.csv"
                                                           {:key-fn keyword})
                                      ds (-> (tc/inner-join ds airlines [:carrier])
                                             (tc/group-by :name)
                                             (tc/mean :arr-delay)
                                             (tc/rename-columns [:name :data])
                                             (tc/order-by :data :desc))]
                                  {:categories (take n (-> ds
                                                           :name
                                                           vec))
                                   :series [{:name "Average Delay"
                                             :data (take n (-> ds
                                                               :data
                                                               vec))}]})
                                :parameters {:query {:n int?}}})}}]
     ["/most-delays/:n" {:get {:handler
                               (fn [req]
                                 {:body
                                  (let [n (Integer/parseInt (:n (:path-params req)))
                                        ds (tc/dataset "./resources/data/flights.csv"
                                                       {:key-fn #(keyword (str/replace (name %) "_" "-"))})
                                        airlines (tc/dataset "./resources/data/airlines.csv"
                                                             {:key-fn keyword})
                                        ds (-> (tc/inner-join ds airlines [:carrier])
                                               (tc/group-by :name)
                                               (tc/mean :arr-delay)
                                               (tc/rename-columns [:name :data])
                                               (tc/order-by :data :desc))]
                                    {:categories (take n (-> ds
                                                             :name
                                                             vec))
                                     :series [{:name "Average Delay"
                                               :data (take n (-> ds
                                                                 :data
                                                                 vec))}]})})}}]]
    {:data {:coercion malli/coercion
            :muuntaja muuntaja/instance
            :middleware [parameters-middleware

                         format-negotiate-middleware
                         format-response-middleware
                         format-request-middleware

                         coerce-request-middleware
                         coerce-response-middleware]}})))



(comment
  (def server
    (run-jetty #'app
               {:port 9876
                :join? false}))

  (.stop server)

  (app {:request-method :post
        :uri "/api/most-delays"
        :body-params {:n "5"}})

  (app {:request-method :post
        :uri "/api/most-delays"
        :body-params {:n 5}})

                                        ;
  )
