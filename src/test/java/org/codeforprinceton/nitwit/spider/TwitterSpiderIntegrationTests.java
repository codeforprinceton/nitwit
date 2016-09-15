/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.nitwit.spider;

import java.util.List;

import org.codeforprinceton.nitwit.IntegrationTestCategory;
import org.codeforprinceton.nitwit.twitter.TwitterSearchAgent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 * Twitter Spider Integration Tests.
 * <p>
 * Covers all possible paths, happy or exception. Will connect to Twitter using OAuth credentials, that need to provided
 * as Runtime or Environment variables.
 * </p>
 * 
 * @author BoggyBumblebee
 */
@Category(IntegrationTestCategory.class)
public class TwitterSpiderIntegrationTests {

	private static final String SIMPLE_QUERY_STRING_ALWAYS = "#codeforprinceton";

	private Twitter twitter = null;

	private TwitterSearchAgent agent = null;

	private TwitterSpider spider = null;

	@Before
	public void setUp() throws Exception {

		twitter = TwitterFactory.getSingleton();
		agent = new TwitterSearchAgent(twitter);
		spider = new TwitterSpider(agent);
	}

	@After
	public void tearDown() throws Exception {

		spider = null;
		agent = null;
		twitter = null;
	}

	@Test
	public void test() {

		List<String> hashtags = spider.spiderHashtags(SIMPLE_QUERY_STRING_ALWAYS);

		for (String hashtag : hashtags) {
			
			System.out.println(hashtag);
		}
	}
}
