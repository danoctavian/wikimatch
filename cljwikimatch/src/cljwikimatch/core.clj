(ns cljwikimatch.core
(:gen-class
  :methods  [#^{:static true} [foo [int] void]
             #^{:static true} [bar [int] void]])
 (:require [monger.core :as mg]
           [monger.collection :as mc])
 (:import [com.mongodb MongoOptions ServerAddress]
              [org.bson.types ObjectId]
              [com.mongodb DB WriteConcern]
          )
)

(use 'clojure.java.io)
(def mgdbname "wikimatch")
(def mgtraincoll "train")
(def mgtestcoll "test")

(defn noends [s] (subs s 1 ( -(count s) 1)))
; takes a vector with the elems and makes numbers and removes quotes
(defn tidyRow [row]
  (let [[asin guid t booktxt wikisum wikititle wikiquery overlap class] row]
    {:asin asin :guid guid  :term (noends t) :booktxt (noends booktxt)
     :wikisum (noends wikisum) :wikititle (noends wikititle)
     :wikiquery (noends wikiquery) :overlap (java.lang.Integer/parseInt overlap)
     :class (java.lang.Integer/parseInt class)})
  )

(defn parseLine [line] 
 (tidyRow (clojure.string/split line #","))
 )

(defn uploadToMongo [file mgcoll]
 (mg/connect!)
 (mg/set-db! (mg/get-db mgdbname))
 (println "uploading to mongo" mgcoll mgdbname) 
  (with-open [rdr (reader file)]
  (doseq [line (line-seq rdr)]
    (try 
    (mc/insert mgcoll (parseLine line))
    (catch Exception e ())
    )
;    (println line)
    ))
 )

(defn runUserUpload []
  (println "input file name")
  (let [fname (read-line)]
     (println "uploading file " fname)
     (uploadToMongo file mgtraincoll)
    )
  )

(defn -main []
  (println "print 1 to load files to mongodb")
  (uploadToMongo "/home/dan/data/kindle-education-xray/klo-100.txt" "smallData")
;  (runUserUpload)
;    (let [input (read-line)]
;      (case input "1" (runUserUpload) "default" (println "fuck off now")))  

  )

(defn -foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
(defn -bar
  "  I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

