/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.codeforprinceton.IntegrationTestCategory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

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
@Category(IntegrationTestCategory.class)
public class TwitterSearchAgentIntegrationTests {

	private static final String SIMPLE_QUERY_STRING_ZERO = "#NotExpectingAResultForThisHashtag";

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
	public void simpleQueryForStatuses_TestZeroStatuses() throws TwitterException {

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING_ZERO);

		assertNotNull("Simple Query for Statuses should not return a null response!", hashtags);
		assertEquals("Simple Query for Statuses should return zero results!", SIMPLE_RETURN_ZERO, hashtags.size());
	}

	@Test
	public void simpleQueryForStatuses_TestFifteenStatuses() throws TwitterException {

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING_ALWAYS);

		assertNotNull("Simple Query for Statuses should not return a null response!", hashtags);
		assertEquals("Simple Query for Statuses should return fifteen results!", SIMPLE_RETURN_FIFTEEN,
				hashtags.size());
	}

	@Test
	public void simpleQueryForStatuses_TestHundredStatuses() throws TwitterException {

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING_ALWAYS, SIMPLE_RETURN_HUNDRED);

		assertNotNull("Simple Query for Statuses should not return a null response!", hashtags);
		assertEquals("Simple Query for Statuses should return one hundred results!", SIMPLE_RETURN_HUNDRED,
				hashtags.size());
	}

	@Test
	public void simpleQueryForStatuses_TestHundredOneStatuses() throws TwitterException {

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING_ALWAYS, SIMPLE_RETURN_HUNDRED_ONE);

		assertNotNull("Simple Query for Statuses should not return a null response!", hashtags);
		assertEquals("Simple Query for Statuses should return one hundred (not one hundred and one) results!",
				SIMPLE_RETURN_HUNDRED, hashtags.size());
	}

	@Test
	public void simpleQueryForStatuses_Null() throws TwitterException {

		List<Status> hashtags = agent.simpleQuery(null);

		assertNotNull("Simple Query for Statuses should not return a null response!", hashtags);
		assertEquals("Simple Query for Statuses should return zero results!", SIMPLE_RETURN_ZERO, hashtags.size());
	}

	@Test
	public void exhaustiveQueryForStatuses_TestZeroStatuses() throws TwitterException {

		List<Status> hashtags = agent.exhaustiveQuery(SIMPLE_QUERY_STRING_ZERO);

		assertNotNull("Exhaustive Query for Statuses should not return a null response!", hashtags);
		assertEquals("Exhaustive Query for Statuses should return zero results!", SIMPLE_RETURN_ZERO, hashtags.size());
	}

	@Test
	public void exhaustiveQueryForStatuses_TestManyStatuses() throws TwitterException {

		List<Status> hashtags = agent.exhaustiveQuery(SIMPLE_QUERY_STRING_ALWAYS);

		assertNotNull("Exhaustive Query for Statuses should not return a null response!", hashtags);
		assertTrue("Exhaustive Query for Statuses should return many results!", SIMPLE_RETURN_ZERO < hashtags.size());
	}

	@Test
	public void exhaustiveQueryForStatuses_Null() throws TwitterException {

		List<Status> hashtags = agent.exhaustiveQuery(null);

		assertNotNull("Exhaustive Query for Statuses should not return a null response!", hashtags);
		assertEquals("Exhaustive Query for Statuses should return zero results!", SIMPLE_RETURN_ZERO, hashtags.size());
	}

}
