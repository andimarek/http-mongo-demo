(ns http-mongo-demo.core-test
  (:require [clj-http.client :as client]))

(client/put "http://127.0.0.1:3000/pizza" {:form-params {:name "Andis Special" :belag "thunfisch und salami" :extra "Knoblauch" :preis "8,24 EUR"} :content-type :json})