#!/usr/bin/env python

import datastore
import contentdetector
import traceback
import ast
import threading

# from start -> (stop-1)
def grabContent(start, stop):
  if stop < 0 or start < 0 or stop < start:
    print "Invalid arguments"
    return
  
  articles = []
  tweets = list(datastore.load('twitter_tweets', start, stop-start))
  #tweets = tweets[start:stop]
  print "Loaded " + str(len(tweets)) + " tweets from `twitter_tweets` table, " + str(start) + " -> " + str(stop-1)
  index = start
  url_counter = 0
  for tweet in tweets:
    tweet_id = tweet['id']
    user_id = tweet['user_id']
    user_screen_name = tweet['user_screen_name']
    text = tweet['text']
    urls = tweet['urls']
    print str(index) + ": Processing tweet " + str(tweet_id)
    if urls is not None and urls.strip() != '':
      #urls = dict(json.loads(urls, encoding="utf-8"))
      try:
        urls = ast.literal_eval(urls)
      except Exception as e:
        continue
      print "Found " + str(len(urls)) + " urls"
      for url in urls:
        url_counter = url_counter + 1
        display_url = urls[url]
        print "Grabbing content from " + url + " ..."
        content = None
        try:
          content = contentdetector.upgradeLink(url)
        except Exception as e:
          print "Exception occurs when trying to grab content from " + url
          traceback.print_exc()
        if content is not None and content.strip() != '':
          articles.append({ "tweet_id": tweet_id, "user_id": user_id, "user_screen_name": user_screen_name,
                          "text": text, "url": url, "display_url": display_url, "content": content  })
    index = index + 1
  print "Processed tweets " + str(start) + " -> " + str(stop-1)
  print "Total urls processed: " + str(url_counter)
  print "Total articles grabbed: " + str(len(articles))
  
  datastore.store(articles, 'twitter_articles_' + str(start) + '_' + str(stop-1), '')

class GrabbingThread(threading.Thread):
  def __init__(self, begin, end):
    threading.Thread.__init__(self)
    self.begin = begin
    self.end = end
    
  def run(self):
    print str(self.getName()) + " has started to grab content from " + str(self.begin) + "->" + str(self.end-1)
    grabContent(self.begin, self.end)
  
  
if __name__ == "__main__":
  # from start -> (stop-1)
  grabContent(50000, 60000)
