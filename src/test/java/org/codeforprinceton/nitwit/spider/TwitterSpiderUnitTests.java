/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.nitwit.spider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import twitter4j.HashtagEntity;
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

	private static final String QUERY_STRING = "codeforprinceton";

	private static final int ONE_QUERY_RESULT = 1;

	private static final int TWO_QUERY_RESULTS = 2;

	private static final int HUNDRED_QUERY_RESULTS = 100;

	private static final int ONE_HASHTAG_RESULT = 1;

	private static final int TWO_HASHTAG_RESULTS = 2;
	
	private static final int HASHTAG_ZERO_POSITION = 0;

	private static final int HASHTAG_ONE_POSITION = 1;

	private static final int MAXIMUM_LEVELS = 2;

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

		when(agent.exhaustiveQuery(QUERY_STRING)).thenReturn(new ArrayList<Status>());

		Map<String, Integer> hashtags = spider.spiderHashtags(QUERY_STRING);

		assertNotNull("Spider for Hashtags should not return a null response!", hashtags);
		assertEquals("Spider for Hashtags should return zero results!", ONE_HASHTAG_RESULT, hashtags.size());

		verify(agent).exhaustiveQuery(QUERY_STRING);
	}

	@Test
	public void spider_TestLevelOneOneHashtag() throws Exception {

		List<Status> statuses = buildStatusList(ONE_QUERY_RESULT);

		when(agent.exhaustiveQuery(QUERY_STRING)).thenReturn(statuses);
		when(statuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities())
				.thenReturn(buildHashtagEntityArray(ONE_QUERY_RESULT));

		Map<String, Integer> hashtags = spider.spiderHashtags(QUERY_STRING);

		assertNotNull("Spider for Hashtags should not return a null response!", hashtags);
		assertEquals("Spider for Hashtags should return one result!", TWO_HASHTAG_RESULTS, hashtags.size());

		verify(agent).exhaustiveQuery(QUERY_STRING);
		verify(statuses.get(HASHTAG_ZERO_POSITION)).getHashtagEntities();
	}

	@Test
	public void spider_TestLevelOneTwoHashtags() throws Exception {

		List<Status> statuses = buildStatusList(TWO_QUERY_RESULTS);

		when(agent.exhaustiveQuery(QUERY_STRING)).thenReturn(statuses);
		when(statuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities())
				.thenReturn(buildHashtagEntityArray(ONE_QUERY_RESULT));
		when(statuses.get(HASHTAG_ONE_POSITION).getHashtagEntities())
				.thenReturn(buildHashtagEntityArray(ONE_QUERY_RESULT));

		Map<String, Integer> hashtags = spider.spiderHashtags(QUERY_STRING);

		assertNotNull("Spider for Hashtags should not return a null response!", hashtags);
		assertEquals("Spider for Hashtags should return two results!", TWO_HASHTAG_RESULTS, hashtags.size());

		verify(agent).exhaustiveQuery(QUERY_STRING);
		verify(statuses.get(HASHTAG_ZERO_POSITION)).getHashtagEntities();
		verify(statuses.get(HASHTAG_ONE_POSITION)).getHashtagEntities();
	}

	@Test
	public void spider_TestLevelTwoTwoHashtags() throws Exception {

		List<Status> levelOneStatuses = buildStatusList(ONE_QUERY_RESULT);
		List<Status> levelTwoStatuses = buildStatusList(ONE_QUERY_RESULT);

		when(agent.exhaustiveQuery(QUERY_STRING)).thenReturn(levelOneStatuses);
		when(levelOneStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities())
				.thenReturn(buildHashtagEntityArray(ONE_QUERY_RESULT));

		String secondHashtag = levelOneStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities()[0].getText();

		when(agent.exhaustiveQuery(secondHashtag)).thenReturn(levelTwoStatuses);
		when(levelTwoStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities())
				.thenReturn(buildHashtagEntityArray(ONE_QUERY_RESULT));

		Map<String, Integer> hashtags = spider.spiderHashtags(QUERY_STRING);

		assertNotNull("Spider for Hashtags should not return a null response!", hashtags);
		assertEquals("Spider for Hashtags should return two results!", TWO_HASHTAG_RESULTS, hashtags.size());

		verify(agent).exhaustiveQuery(QUERY_STRING);
		verify(levelOneStatuses.get(HASHTAG_ZERO_POSITION), atLeastOnce()).getHashtagEntities();

		verify(agent).exhaustiveQuery(secondHashtag);
		verify(levelOneStatuses.get(HASHTAG_ZERO_POSITION), atLeastOnce()).getHashtagEntities();
	}

	@Test
	public void spider_TestLevelThreeLimitedToLevelTwoTwoHashtags() throws Exception {

		List<Status> levelOneStatuses = buildStatusList(ONE_QUERY_RESULT);
		List<Status> levelTwoStatuses = buildStatusList(ONE_QUERY_RESULT);
		List<Status> levelThreeStatuses = buildStatusList(ONE_QUERY_RESULT);

		when(agent.exhaustiveQuery(QUERY_STRING)).thenReturn(levelOneStatuses);
		when(levelOneStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities())
				.thenReturn(buildHashtagEntityArray(ONE_QUERY_RESULT));

		String secondHashtag = levelOneStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities()[0].getText();

		when(agent.exhaustiveQuery(secondHashtag)).thenReturn(levelTwoStatuses);
		when(levelTwoStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities())
				.thenReturn(buildHashtagEntityArray(ONE_QUERY_RESULT));
		
		Map<String, Integer> hashtags = spider.spiderHashtags(QUERY_STRING, MAXIMUM_LEVELS);

		assertNotNull("Spider for Hashtags should not return a null response!", hashtags);
		assertEquals("Spider for Hashtags should return two results!", TWO_HASHTAG_RESULTS, hashtags.size());

		verify(agent).exhaustiveQuery(QUERY_STRING);
		verify(levelOneStatuses.get(HASHTAG_ZERO_POSITION), atLeastOnce()).getHashtagEntities();

		verify(agent).exhaustiveQuery(secondHashtag);
		verify(levelOneStatuses.get(HASHTAG_ZERO_POSITION), atLeastOnce()).getHashtagEntities();

		verify(levelThreeStatuses.get(HASHTAG_ZERO_POSITION), never()).getHashtagEntities();
	}

	@Test
	public void spider_TestLimitedToLevelTwoWithTwoHashtags() throws Exception {

		List<Status> levelOneStatuses = buildStatusList(ONE_QUERY_RESULT);
		List<Status> levelTwoStatuses = buildStatusList(ONE_QUERY_RESULT);

		when(agent.exhaustiveQuery(QUERY_STRING)).thenReturn(levelOneStatuses);
		when(levelOneStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities())
				.thenReturn(buildHashtagEntityArray(ONE_QUERY_RESULT));

		String secondHashtag = levelOneStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities()[0].getText();

		when(agent.exhaustiveQuery(secondHashtag)).thenReturn(levelTwoStatuses);
		when(levelTwoStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities())
				.thenReturn(buildHashtagEntityArray(HUNDRED_QUERY_RESULTS));

		Map<String, Integer> hashtags = spider.spiderHashtags(QUERY_STRING);

		assertNotNull("Spider for Hashtags should not return a null response!", hashtags);
		assertEquals("Spider for Hashtags should return two results!", TWO_HASHTAG_RESULTS, hashtags.size());

		verify(agent).exhaustiveQuery(QUERY_STRING);
		verify(levelOneStatuses.get(HASHTAG_ZERO_POSITION), atLeastOnce()).getHashtagEntities();

		verify(agent).exhaustiveQuery(secondHashtag);
		verify(levelOneStatuses.get(HASHTAG_ZERO_POSITION), atLeastOnce()).getHashtagEntities();
	}

	private List<Status> buildStatusList(int number) {

		List<Status> statuses = new ArrayList<>();

		for (int position = 0; position < number; position++) {
			statuses.add(mock(Status.class));
		}
		return statuses;
	}

	private HashtagEntity[] buildHashtagEntityArray(int number) {

		List<HashtagEntity> hashtagEntities = new ArrayList<>();

		for (int position = 0; position < number; position++) {

			hashtagEntities.add(mock(HashtagEntity.class));
		}
		return hashtagEntities.toArray(new HashtagEntity[hashtagEntities.size()]);
	}
}
