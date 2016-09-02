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

/**
 * Twitter Search Agent Unit Tests.
 * <p>
 * Covers all possible paths, happy or exception.
 * </p>
 * 
 * @author BoggyBumblebee
 */
@RunWith(MockitoJUnitRunner.class)
@Category(UnitTestCategory.class)
public class TwitterSearchAgentUnitTests {

	private static final String SIMPLE_QUERY_STRING = "#codeforprinceton";

	private static final int SIMPLE_RETURN_ZERO = 0;

	private static final int SIMPLE_RETURN_ONE = 1;

	private static final int SIMPLE_RETURN_FIFTEEN = 15;

	private static final int SIMPLE_RETURN_HUNDRED = 100;

	private static final int SIMPLE_RETURN_HUNDRED_ONE = 101;

	@Mock
	private Twitter twitter;

	@InjectMocks
	private TwitterSearchAgent agent = null;

	@Before
	public void setUp() throws Exception {

		agent = new TwitterSearchAgent(twitter);
	}

	@After
	public void tearDown() throws Exception {

		agent = null;
	}

	@Test
	public void simpleQueryForHashtags_TestZeroTweets() throws TwitterException {

		when(twitter.search(new Query(SIMPLE_QUERY_STRING))).thenReturn(mock(QueryResult.class));

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return zero results!", SIMPLE_RETURN_ZERO, hashtags.size());

		verify(twitter).search(new Query(SIMPLE_QUERY_STRING));
	}

	@Test
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
	public void simpleQueryForHashtags_TestFifteenTweets() throws TwitterException {

		QueryResult queryResult = mock(QueryResult.class);

		List<Status> statuses = new ArrayList<Status>();

		for (int position = 0; position < SIMPLE_RETURN_FIFTEEN; position++) {
			statuses.add(mock(Status.class));
		}

		when(twitter.search(new Query(SIMPLE_QUERY_STRING))).thenReturn(queryResult);
		when(queryResult.getTweets()).thenReturn(statuses);

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return fifteen results!", SIMPLE_RETURN_FIFTEEN,
				hashtags.size());

		verify(twitter).search(new Query(SIMPLE_QUERY_STRING));
	}

	@Test
	public void simpleQueryForHashtags_TestHundredTweets() throws TwitterException {

		QueryResult queryResult = mock(QueryResult.class);

		List<Status> statuses = new ArrayList<Status>();

		for (int position = 0; position < SIMPLE_RETURN_HUNDRED; position++) {
			statuses.add(mock(Status.class));
		}

		Query query = new Query(SIMPLE_QUERY_STRING);
		query.setCount(SIMPLE_RETURN_HUNDRED);
		
		when(twitter.search(query)).thenReturn(queryResult);
		when(queryResult.getTweets()).thenReturn(statuses);

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING, SIMPLE_RETURN_HUNDRED);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return hundreed results!", SIMPLE_RETURN_HUNDRED,
				hashtags.size());

		verify(twitter).search(query);
	}

	@Test
	public void simpleQueryForHashtags_TestHundredOneTweets() throws TwitterException {

		QueryResult queryResult = mock(QueryResult.class);

		List<Status> statuses = new ArrayList<Status>();

		for (int position = 0; position < SIMPLE_RETURN_HUNDRED; position++) {
			statuses.add(mock(Status.class));
		}

		Query query = new Query(SIMPLE_QUERY_STRING);
		query.setCount(SIMPLE_RETURN_HUNDRED);
		
		when(twitter.search(query)).thenReturn(queryResult);
		when(queryResult.getTweets()).thenReturn(statuses);

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING, SIMPLE_RETURN_HUNDRED_ONE);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return one hundred (not one hundred and one) results!",
				SIMPLE_RETURN_HUNDRED, hashtags.size());

		verify(twitter).search(query);
	}

	@Test
	public void simpleQueryForHashtags_TwitterException() throws TwitterException {

		when(twitter.search(new Query(SIMPLE_QUERY_STRING))).thenThrow(TwitterException.class);

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return zero results!", SIMPLE_RETURN_ZERO, hashtags.size());

		verify(twitter).search(new Query(SIMPLE_QUERY_STRING));
	}

	@Test
	public void simpleQueryForHashtags_Null() throws TwitterException {

		when(twitter.search(new Query(null))).thenThrow(TwitterException.class);

		List<Status> hashtags = agent.simpleQuery(null);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return zero results!", SIMPLE_RETURN_ZERO, hashtags.size());

		verify(twitter).search(new Query(null));
	}

}
