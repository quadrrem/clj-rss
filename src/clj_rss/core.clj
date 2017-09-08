(ns clj-rss.core (:gen-class)
  (require [clojure.java.io :as io]
           [clojure.xml :as xml]
           [clojure.pprint :as pp]))

(def URL "https://www.srf.ch/news/bnf/rss/1890")

(defn parse [url]
  (with-open
    [xin (io/input-stream (java.net.URL. url))]
      (xml/parse xin)))
     
(defn read-tags [el]
  (when (not (nil? el))
    (if (map? el)
      (let [tag (:tag el)
            content (:content el)]
        {(keyword tag) (reduce conj (map read-tags content))})
      el)))

(defn test []
  (pp/pprint (read-tags (parse URL))))

(defn -main [& args]
  (println "Working!")
  (test))