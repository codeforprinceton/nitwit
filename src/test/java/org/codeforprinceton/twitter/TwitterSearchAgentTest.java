package org.codeforprinceton.twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twitter4j.Status;
import twitter4j.TwitterFactory;

public class TwitterSearchAgentTest {

	private static final String SIMPLE_QUERY_STRING = "#codeforprinceton";

	private static final int SIMPLE_RETURN_SIZE = 0;

	TwitterSearchAgent agent = null;

	@Before
	public void setUp() throws Exception {

		agent = new TwitterSearchAgent(TwitterFactory.getSingleton());
	}

	@After
	public void tearDown() throws Exception {

		agent = null;
	}

	@Test
	public void simpleQueryForHashtags_Test() {

		List<Status> hashtags = agent.simpleQuery(SIMPLE_QUERY_STRING);

		assertNotNull("Simple Query for Hashtags should not return a null response!", hashtags);
		assertEquals("Simple Query for Hashtags should return zero results!", SIMPLE_RETURN_SIZE, hashtags.size());
	}

}
