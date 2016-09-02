/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.codeforprinceton.IntegrationTestCategory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * Twitter Search Agent Integration Tests.
 * <p>
 * Covers all possible paths, happy or exception. Will connect to Twitter using OAuth credentials, that need to provided
 * as Runtime or Environment variables.
 * </p>
 * 
 * @author BoggyBumblebee
 */
@RunWith(MockitoJUnitRunner.class)
@Category(IntegrationTestCategory.class)
public class TwitterSearchAgentIntegrationTests {

	private static final String SIMPLE_QUERY_STRING_ZERO = "#NotExpectingAResultForThisHashTag";

	private static final String SIMPLE_QUERY_STRING_ALWAYS = "#Always";

	private static final int SIMPLE_RETURN_ZERO = 0;

	private static final int SIMPLE_RETURN_FIFTEEN = 15;

	private static final int SIMPLE_RETURN_HUNDRED = 100;

	private static final int SIMPLE_RETURN_HUNDRED_ONE = 101;

	private Twitter twitter = null;

	private TwitterSearchAgent agent = null;

	@Before
	public void setUp() throws Exception {

		twitter = TwitterFactory.getSingleton();

		agent = new TwitterSearchAgent(twitter);
	}

	@After
	public void tearDown() throws Exception {

		agent = null;
	}

	@Test
	public void simpleQueryForHashtags_TestZeroTweets() throws TwitterException {

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING_ZERO);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return zero results!", SIMPLE_RETURN_ZERO, hashtags.size());
	}

	@Test
	public void simpleQueryForHashtags_TestFifteenTweets() throws TwitterException {

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING_ALWAYS);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return fifteen results!", SIMPLE_RETURN_FIFTEEN,
				hashtags.size());
	}

	@Test
	public void simpleQueryForHashtags_TestHundredTweets() throws TwitterException {

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING_ALWAYS, SIMPLE_RETURN_HUNDRED);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return one hundred results!", SIMPLE_RETURN_HUNDRED,
				hashtags.size());
	}

	@Test
	public void simpleQueryForHashtags_TestHundredOneTweets() throws TwitterException {

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING_ALWAYS, SIMPLE_RETURN_HUNDRED_ONE);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return one hundred (not one hundred and one) results!",
				SIMPLE_RETURN_HUNDRED, hashtags.size());
	}

	@Test
	public void simpleQueryForHashtags_Null() throws TwitterException {

		List<Status> hashtags = agent.simpleQuery(null);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return zero results!", SIMPLE_RETURN_ZERO, hashtags.size());
	}

}
