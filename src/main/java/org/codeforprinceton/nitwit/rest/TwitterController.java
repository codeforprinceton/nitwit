/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.nitwit.rest;

import java.util.Map;

import org.codeforprinceton.nitwit.spider.TwitterSpider;
import org.codeforprinceton.nitwit.twitter.TwitterSearchAgent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 * Twitter REST Controller.
 * 
 * @author BoggyBumblebee
 */
@RestController
public class TwitterController {

	private Twitter twitter = null;

	private TwitterSearchAgent agent = null;

	private TwitterSpider spider = null;

	public TwitterController() {

		super();
		twitter = TwitterFactory.getSingleton();
		agent = new TwitterSearchAgent(twitter);
		spider = new TwitterSpider(agent);
	}

	@RequestMapping("hashtags")
	public TwitterResponseMap hashtags(@RequestParam(value = "keywords", defaultValue = "West Windsor") String keywords,
			@RequestParam(value = "levels", defaultValue = "1") Integer levels,
			@RequestParam(value = "top", defaultValue = "8") Integer top) {

		Map<String, Integer> hashtags = spider.spiderHashtags(keywords, levels);

		return new TwitterResponseMap(hashtags);
	}
}
