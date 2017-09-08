(ns clj-rss.core-test
  (:require [clojure.test :refer :all]
            [clj-rss.core :refer :all]
            [clojure.pprint :as pp]
            [clojure.java.io :as io]))

(def URL "https://www.srf.ch/news/bnf/rss/1890")
(def FILE "testrss.xml")

;(deftest read-test-url
;  (testing "Read test url rss"
;    (pp/pprint (read-rss URL))))

;(deftest print-test-file
;  (testing "Read test file rss"
;    (pp/pprint (read-rss (io/resource FILE)))))

(deftest read-test-file
  (testing "Read test file rss"
    (let [channel (get-in (read-rss (io/resource FILE)) [:rss :channel])]
      (is (= 48 (count (:items channel))))
      (is (= "SRF News Schweiz" (:title channel)))
      (is (= "https://www.srf.ch/news/bnf/rss/1890" (:link channel))))))