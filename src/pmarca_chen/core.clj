(ns pmarca-chen.core
  (:require [clojure.set :as set]
            [twitter.oauth :as oauth]
            [twitter.api.restful :as api]))

(def old-tweets (atom #{}))

(def my-creds (oauth/make-oauth-creds
               (System/getenv "PMARCACHEN_CONSUMER_KEY")
               (System/getenv "PMARCACHEN_CONSUMER_SEC")
               (System/getenv "PMARCACHEN_ACCESS_TOKEN")
               (System/getenv "PMARCACHEN_ACCESS_TOKEN_SEC")))

(defn timeline []
  (api/statuses-home-timeline :oauth-creds my-creds))

(defn fetch-tweets []
  (set (map :id (:body (timeline)))))

(defn retweet! [tweet]
  (do (api/statuses-retweet-id :oauth-creds my-creds
                               :params {:id tweet})
      (prn "Tweeted " tweet)))

(defn maybe-retweet!
  "Retweet a tweet 10% of the time."
  [tweet]
  (if (= (rand-int 10) 9)
    (retweet! tweet)
    (prn (str "Discarded tweet " tweet))))

(defn fetch-and-retweet!
  "Fetches tweets, retweets some, and calculates new tweets, and maybe
  retweets them. Adds new tweets to old tweets set."
  []
  (let [all-tweets (fetch-tweets)
        new-tweets (set/difference all-tweets @old-tweets)]
    (do (println (str "Found " (count new-tweets) " new tweets."))
        (dorun (map maybe-retweet! new-tweets))
        (swap! old-tweets set/union new-tweets))))

(defn periodically! [f ms]
  (future (while true (do (Thread/sleep ms) (f)))))

(comment
  (def pmarca-chen (periodically! fetch-and-retweet! (* 1000 60 5)))
  (future-cancel pmarca-chen)
  )
