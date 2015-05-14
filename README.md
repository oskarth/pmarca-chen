# pmarca-chen

For rationale, see my post on http://experiments.oskarth.com.

What is it? A twitter bot that retweets 10% of pmarca's tweets. You
can just go ahead and follow it at https://twitter.com/pmarca_chen,
but if you want to create your own bot, look in the next section.

pmarca-chen is under 50 lines of code, so don't be afraid to read the
source!

## Usage

1. Register a Twitter account and create an app.

2. Set the environment variables `PMARCACHEN_CONSUMER_KEY`,
   `PMARCACHEN_CONSUMER_SEC`, `PMARCACHEN_ACCESS_TOKEN`,
   `PMARCACHEN_ACCESS_TOKEN_SEC` correctly.

3. Run `lein repl` and write `(def pmarca-chen (periodically!
   fetch-and-retweet! (* 1000 60 5)))` to start polling and
   retweeting.

## How does it work?

It polls the timeline of https://twitter.com/pmarca_chen every 5
minutes using the Twitter REST API, removes the tweets it has already
seen, and retweets 10% of the new tweets. It then adds the newly seen
tweets to the set of old tweets.

## TODO / Bugs

- Uberjar only tweets/discards 7 tweets when there are 20 new tweets
  found. Works in REPL though.

## License

Copyright © 2015 oskarth

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
