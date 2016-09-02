/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.codeforprinceton.IntegrationTestCategory;
import org.codeforprinceton.UnitTestCategory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@RunWith(MockitoJUnitRunner.class)
public class TwitterSearchAgentIntegrationTests {

	private static final String SIMPLE_QUERY_STRING = "#codeforprinceton";

	private static final int SIMPLE_RETURN_ZERO = 0;

	private static final int SIMPLE_RETURN_ONE = 1;

	private static final int SIMPLE_RETURN_TEN = 10;

	@Mock
	Twitter twitter;

	@InjectMocks
	TwitterSearchAgent agent = null;

	@Before
	public void setUp() throws Exception {

		agent = new TwitterSearchAgent(twitter);
	}

	@After
	public void tearDown() throws Exception {

		agent = null;
	}

	@Test
	@Category(IntegrationTestCategory.class)
	public void simpleQueryForHashtags_TestZeroTweets() throws TwitterException {

		when(twitter.search(new Query(SIMPLE_QUERY_STRING))).thenReturn(mock(QueryResult.class));

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return zero results!", SIMPLE_RETURN_ZERO, hashtags.size());

		verify(twitter).search(new Query(SIMPLE_QUERY_STRING));
	}

	@Test
	@Category(IntegrationTestCategory.class)
	public void simpleQueryForHashtags_TestOneTweet() throws TwitterException {

		QueryResult queryResult = mock(QueryResult.class);

		List<Status> statuses = new ArrayList<Status>();
		statuses.add(mock(Status.class));

		when(twitter.search(new Query(SIMPLE_QUERY_STRING))).thenReturn(queryResult);
		when(queryResult.getTweets()).thenReturn(statuses);

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return one results!", SIMPLE_RETURN_ONE, hashtags.size());

		verify(twitter).search(new Query(SIMPLE_QUERY_STRING));
	}

	@Test
	@Category(IntegrationTestCategory.class)
	public void simpleQueryForHashtags_TestTenTweets() throws TwitterException {

		QueryResult queryResult = mock(QueryResult.class);

		List<Status> statuses = new ArrayList<Status>();

		for (int position = 0; position < SIMPLE_RETURN_TEN; position++) {
			statuses.add(mock(Status.class));
		}

		when(twitter.search(new Query(SIMPLE_QUERY_STRING))).thenReturn(queryResult);
		when(queryResult.getTweets()).thenReturn(statuses);

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return ten results!", SIMPLE_RETURN_TEN, hashtags.size());

		verify(twitter).search(new Query(SIMPLE_QUERY_STRING));
	}

	@Test
	@Category(IntegrationTestCategory.class)
	public void simpleQueryForHashtags_TwitterException() throws TwitterException {

		when(twitter.search(new Query(SIMPLE_QUERY_STRING))).thenThrow(TwitterException.class);

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return zero results!", SIMPLE_RETURN_ZERO, hashtags.size());

		verify(twitter).search(new Query(SIMPLE_QUERY_STRING));
	}

	@Test
	@Category(UnitTestCategory.class)
	public void simpleQueryForHashtags_Null() throws TwitterException {

		when(twitter.search(new Query(null))).thenThrow(TwitterException.class);

		List<Status> hashtags = agent.simpleQuery(null);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return zero results!", SIMPLE_RETURN_ZERO, hashtags.size());

		verify(twitter).search(new Query(null));
	}

}
