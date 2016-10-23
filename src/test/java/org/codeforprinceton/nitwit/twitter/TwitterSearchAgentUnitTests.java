/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.nitwit.twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.codeforprinceton.nitwit.UnitTestCategory;
import org.codeforprinceton.nitwit.twitter.TwitterSearchAgent;
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

	private static final String SIMPLE_QUERY_STRING = "codeforprinceton";

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
	public void simpleQueryForStatuses_TestZeroStatuses() throws TwitterException {

		when(twitter.search(new Query(SIMPLE_QUERY_STRING))).thenReturn(mock(QueryResult.class));

		List<Status> statuses = agent.simpleQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Simple Query for Statuses should not return a null response!", statuses);
		assertEquals("Simple Query for Statuses should return zero results!", SIMPLE_RETURN_ZERO, statuses.size());

		verify(twitter).search(new Query(SIMPLE_QUERY_STRING));
	}

	@Test
	public void simpleQueryForStatuses_TestOneStatus() throws TwitterException {

		QueryResult queryResult = mock(QueryResult.class);

		when(twitter.search(new Query(SIMPLE_QUERY_STRING))).thenReturn(queryResult);
		when(queryResult.getTweets()).thenReturn(buildStatusList(SIMPLE_RETURN_ONE));

		List<Status> statuses = agent.simpleQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Simple Query for Statuses should not return a null response!", statuses);
		assertEquals("Simple Query for Statuses should return one results!", SIMPLE_RETURN_ONE, statuses.size());

		verify(twitter).search(new Query(SIMPLE_QUERY_STRING));
		verify(queryResult).getTweets();
	}

	@Test
	public void simpleQueryForStatuses_TestFifteenStatuses() throws TwitterException {

		QueryResult queryResult = mock(QueryResult.class);

		when(twitter.search(new Query(SIMPLE_QUERY_STRING))).thenReturn(queryResult);
		when(queryResult.getTweets()).thenReturn(buildStatusList(SIMPLE_RETURN_FIFTEEN));

		List<Status> statuses = agent.simpleQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Simple Query for Statuses should not return a null response!", statuses);
		assertEquals("Simple Query for Statuses should return fifteen results!", SIMPLE_RETURN_FIFTEEN,
				statuses.size());

		verify(twitter).search(new Query(SIMPLE_QUERY_STRING));
		verify(queryResult).getTweets();
	}

	@Test
	public void simpleQueryForStatuses_TestHundredStatuses() throws TwitterException {

		QueryResult queryResult = mock(QueryResult.class);

		Query query = new Query(SIMPLE_QUERY_STRING);
		query.setCount(SIMPLE_RETURN_HUNDRED);

		when(twitter.search(query)).thenReturn(queryResult);
		when(queryResult.getTweets()).thenReturn(buildStatusList(SIMPLE_RETURN_HUNDRED));

		List<Status> statuses = agent.simpleQuery(SIMPLE_QUERY_STRING, SIMPLE_RETURN_HUNDRED);

		assertNotNull("Simple Query for Statuses should not return a null response!", statuses);
		assertEquals("Simple Query for Statuses should return one hundred results!", SIMPLE_RETURN_HUNDRED,
				statuses.size());

		verify(twitter).search(query);
		verify(queryResult).getTweets();
	}

	@Test
	public void simpleQueryForStatuses_TestHundredOneStatuses() throws TwitterException {

		QueryResult queryResult = mock(QueryResult.class);

		Query query = new Query(SIMPLE_QUERY_STRING);
		query.setCount(SIMPLE_RETURN_HUNDRED);

		when(twitter.search(query)).thenReturn(queryResult);
		when(queryResult.getTweets()).thenReturn(buildStatusList(SIMPLE_RETURN_HUNDRED));

		List<Status> statuses = agent.simpleQuery(SIMPLE_QUERY_STRING, SIMPLE_RETURN_HUNDRED_ONE);

		assertNotNull("Simple Query for Statuses should not return a null response!", statuses);
		assertEquals("Simple Query for Statuses should return one hundred (not one hundred and one) results!",
				SIMPLE_RETURN_HUNDRED, statuses.size());

		verify(twitter).search(query);
		verify(queryResult).getTweets();
	}

	@Test
	public void simpleQueryForStatuses_TwitterException() throws TwitterException {

		when(twitter.search(new Query(SIMPLE_QUERY_STRING))).thenThrow(TwitterException.class);

		List<Status> statuses = agent.simpleQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Simple Query for Statuses should not return a null response!", statuses);
		assertEquals("Simple Query for Statuses should return zero results!", SIMPLE_RETURN_ZERO, statuses.size());

		verify(twitter).search(new Query(SIMPLE_QUERY_STRING));
	}

	@Test
	public void simpleQueryForStatuses_Null() throws TwitterException {

		when(twitter.search(new Query(null))).thenThrow(TwitterException.class);

		List<Status> statuses = agent.simpleQuery(null);

		assertNotNull("Simple Query for Statuses should not return a null response!", statuses);
		assertEquals("Simple Query for Statuses should return zero results!", SIMPLE_RETURN_ZERO, statuses.size());

		verify(twitter).search(new Query(null));
	}

	@Test
	public void exhaustiveQueryForHashtags_TestZeroStatuses() throws TwitterException {

		Query query = new Query(SIMPLE_QUERY_STRING);
		query.setCount(SIMPLE_RETURN_HUNDRED);

		when(twitter.search(query)).thenReturn(mock(QueryResult.class));

		List<Status> statuses = agent.exhaustiveQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Exhaustive Query for Statuses should not return a null response!", statuses);
		assertEquals("Exhaustive Query for Statuses should return zero results!", SIMPLE_RETURN_ZERO, statuses.size());

		verify(twitter).search(query);
	}

	@Test
	public void exhaustiveQueryForHashtags_TestOneStatus() throws TwitterException {

		QueryResult queryResult = mock(QueryResult.class);

		Query query = new Query(SIMPLE_QUERY_STRING);
		query.setCount(SIMPLE_RETURN_HUNDRED);

		when(twitter.search(query)).thenReturn(queryResult);
		when(queryResult.getTweets()).thenReturn(buildStatusList(SIMPLE_RETURN_ONE));

		List<Status> statuses = agent.exhaustiveQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Exhaustive Query for Statuses should not return a null response!", statuses);
		assertEquals("Exhaustive Query for Statuses should return one result!", SIMPLE_RETURN_ONE, statuses.size());

		verify(twitter).search(query);
		verify(queryResult).getTweets();
	}

	@Test
	public void exhaustiveQueryForHashtags_TestFifteenStatuses() throws TwitterException {

		QueryResult queryResult = mock(QueryResult.class);

		Query query = new Query(SIMPLE_QUERY_STRING);
		query.setCount(SIMPLE_RETURN_HUNDRED);

		when(twitter.search(query)).thenReturn(queryResult);
		when(queryResult.getTweets()).thenReturn(buildStatusList(SIMPLE_RETURN_FIFTEEN));

		List<Status> statuses = agent.exhaustiveQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Exhaustive Query for Statuses should not return a null response!", statuses);
		assertEquals("Exhaustive Query for Statuses should return fifteen results!", SIMPLE_RETURN_FIFTEEN,
				statuses.size());

		verify(twitter).search(query);
		verify(queryResult).getTweets();
	}

	@Test
	public void exhaustiveQueryForHashtags_TestHundredStatuses() throws TwitterException {

		QueryResult queryResult = mock(QueryResult.class);

		Query query = new Query(SIMPLE_QUERY_STRING);
		query.setCount(SIMPLE_RETURN_HUNDRED);

		when(twitter.search(query)).thenReturn(queryResult);
		when(queryResult.getTweets()).thenReturn(buildStatusList(SIMPLE_RETURN_HUNDRED));

		List<Status> statuses = agent.exhaustiveQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Exhaustive Query for Statuses should not return a null response!", statuses);
		assertEquals("Exhaustive Query for Statuses should return one hundred results!", SIMPLE_RETURN_HUNDRED,
				statuses.size());

		verify(twitter).search(query);
		verify(queryResult).getTweets();
	}

	@Test
	public void exhaustiveQueryForHashtags_TestHundredOneStatuses() throws TwitterException {

		QueryResult queryResultHundred = mock(QueryResult.class);
		QueryResult queryResultOne = mock(QueryResult.class);

		Query query = new Query(SIMPLE_QUERY_STRING);
		query.setCount(SIMPLE_RETURN_HUNDRED);

		when(twitter.search(query)).thenReturn(queryResultHundred);
		when(queryResultHundred.getTweets()).thenReturn(buildStatusList(SIMPLE_RETURN_HUNDRED));

		when(queryResultHundred.hasNext()).thenReturn(Boolean.TRUE);
		when(twitter.search(queryResultHundred.nextQuery())).thenReturn(queryResultOne);
		when(queryResultOne.getTweets()).thenReturn(buildStatusList(SIMPLE_RETURN_ONE));
		when(queryResultOne.hasNext()).thenReturn(Boolean.FALSE);

		List<Status> statuses = agent.exhaustiveQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Exhaustive Query for Statuses should not return a null response!", statuses);
		assertEquals("Exhaustive Query for Statuses should return oen hundred and one results!",
				SIMPLE_RETURN_HUNDRED_ONE, statuses.size());

		verify(twitter).search(query);
		verify(queryResultHundred).getTweets();
		verify(queryResultOne).getTweets();
	}

	@Test
	public void exhaustiveQueryForStatuses_TwitterException() throws TwitterException {

		Query query = new Query(SIMPLE_QUERY_STRING);
		query.setCount(SIMPLE_RETURN_HUNDRED);

		when(twitter.search(query)).thenThrow(TwitterException.class);

		List<Status> statuses = agent.exhaustiveQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Simple Query for Statuses should not return a null response!", statuses);
		assertEquals("Simple Query for Statuses should return zero results!", SIMPLE_RETURN_ZERO, statuses.size());

		verify(twitter).search(query);
	}

	@Test
	public void exhaustiveQueryForStatuses_Null() throws TwitterException {

		Query query = new Query(null);
		query.setCount(SIMPLE_RETURN_HUNDRED);

		when(twitter.search(query)).thenThrow(TwitterException.class);

		List<Status> statuses = agent.exhaustiveQuery(null);

		assertNotNull("Simple Query for Statuses should not return a null response!", statuses);
		assertEquals("Simple Query for Statuses should return zero results!", SIMPLE_RETURN_ZERO, statuses.size());

		verify(twitter).search(query);
	}

	
	private List<Status> buildStatusList(int number) {

		List<Status> statuses = new ArrayList<>();

		for (int position = 0; position < number; position++) {
			statuses.add(mock(Status.class));
		}
		return statuses;
	}
	
}
