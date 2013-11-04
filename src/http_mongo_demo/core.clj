(ns http-mongo-demo.core
  (:use [compojure.core]
        [ring.middleware.json]
        [monger.core :only [connect! connect set-db! get-db]]
        [monger.collection :only [insert insert-batch]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [monger.collection :as mc])
  (:import [org.bson.types ObjectId]
           [com.mongodb DB WriteConcern]))

(defn connect-to-mongo []
  (connect! {:host "127.0.0.1" :port 27017})
  (set-db! (monger.core/get-db "pizza")))

(defn write-pizza [pizza]
  (insert "pizzas" (merge {:_id (ObjectId.)} pizza)))


(def pizza-route
  (PUT "/pizza" {body :body}
       (write-pizza body)
       "SUCCESS"))

(defn api
  [routes]
  (-> routes
      wrap-json-body))

(def app
  (api pizza-route))


(connect-to-mongo)
(run-jetty #'app {:port 3000})