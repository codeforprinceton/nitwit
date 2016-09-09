/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.spider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.codeforprinceton.UnitTestCategory;
import org.codeforprinceton.twitter.TwitterSearchAgent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import twitter4j.Status;

/**
 * Twitter Spider Unit Tests.
 * <p>
 * Covers all possible paths, happy or exception.
 * </p>
 * 
 * @author BoggyBumblebee
 */
@RunWith(MockitoJUnitRunner.class)
@Category(UnitTestCategory.class)
public class TwitterSpiderUnitTests {

	private static final String LEVEL_ZERO_QUERY_STRING = "";

	private static final String LEVEL_ONE_QUERY_STRING = "#codeforprinceton";

	private static final int LEVEL_ZERO_QUERY_RESULTS = 0;
	
	private static final int LEVEL_ONE_QUERY_RESULTS = 1;

	@Mock
	private TwitterSearchAgent agent;

	@Mock
	private ArrayList<Status> mockArrayListStatuses;

	@InjectMocks
	private TwitterSpider spider = null;

	@Before
	public void setUp() throws Exception {

		spider = new TwitterSpider(agent);
	}

	@After
	public void tearDown() throws Exception {

		spider = null;
	}

	@Test
	public void spider_TestNoHashtags() throws Exception {

		when(agent.exhaustiveQuery(LEVEL_ZERO_QUERY_STRING)).thenReturn(new ArrayList<Status>());
		
		List<String> hashtags = spider.spiderHashtags(LEVEL_ZERO_QUERY_STRING);

		assertNotNull("Spider for Hashtags should not return a null response!", hashtags);
		assertEquals("Spider for Hashtags should return zero results!", LEVEL_ZERO_QUERY_RESULTS, hashtags.size());

		verify(agent).exhaustiveQuery(LEVEL_ZERO_QUERY_STRING);
	}

	@Test
	public void spider_TestOneHashtag() throws Exception {

		when(agent.exhaustiveQuery(LEVEL_ONE_QUERY_STRING)).thenReturn(mockArrayListStatuses);
		
		List<String> hashtags = spider.spiderHashtags(LEVEL_ONE_QUERY_STRING);

		assertNotNull("Spider for Hashtags should not return a null response!", hashtags);
		assertEquals("Spider for Hashtags should return zero results!", LEVEL_ONE_QUERY_RESULTS, hashtags.size());

		verify(agent).exhaustiveQuery(LEVEL_ONE_QUERY_STRING);
	}
}
