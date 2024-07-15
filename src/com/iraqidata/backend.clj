(ns com.iraqidata.backend
  (:require [reitit.ring :as ring]
            [ring.adapter.jetty :refer [run-jetty]]))

;; # Simplest Form

(def app
  (ring/ring-handler
   (ring/router
    ["/api/v0/ping" {:get {:handler (fn [_] {:status 200
                                             :body "Hello World!"})}}])))
(comment
  (def server
    (run-jetty #'app
               {:port 9876
                :join? false}))

  (.stop server))
