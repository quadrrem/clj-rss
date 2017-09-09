(ns clj-rss.core
  (require [clojure.java.io :as io]
           [clojure.xml :as xml]))

(defn parse [url]
  (with-open
    [xin (io/input-stream url)]
      (xml/parse xin)))

(defn is-item? [x]
  (contains? x :item))

(defn conj-item [x , y]
  (let [items (:items x)]
    (conj x {:items (conj items (vec y))})))

(defn conj-channel 
  ([] [])
  ([x y] (if (is-item? y)
            (conj-item x y)
            (conj x y))))

(defn read-tags [el]
  (when (not (nil? el))
    (if (map? el)
      (let [tag (:tag el)
            content (:content el)]
        {(keyword tag) (reduce conj-channel (map read-tags content))})
      el)))

(defn read-rss [source]
  (read-tags (parse source)))