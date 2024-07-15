(ns com.iraqidata.training
  (:gen-class)
  (:require
   [com.iraqidata.backend :as backend]
   [ring.adapter.jetty]))

(defn greet
  "Callable entry point to the application."
  [data]
  (println (str "Hello, " (or (:name data) "World") "!")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (ring.adapter.jetty/run-jetty  #'backend/app
                                {:port 9876
                                 :join? false}))
