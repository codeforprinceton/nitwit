/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.nitwit.spider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
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

import twitter4j.ExtendedMediaEntity;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Scopes;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

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
public class TwitterSpiderStubbedUnitTests {

	private static final String QUERY_STRING = "#codeforprinceton";

	private static final int ONE_QUERY_RESULT = 1;

	private static final int TWO_QUERY_RESULTS = 2;

	private static final int ONE_HASHTAG_RESULT = 1;

	private static final int TWO_HASHTAG_RESULTS = 2;

	private static final int THREE_HASHTAG_RESULTS = 3;

	private static final int FOUR_HASHTAG_RESULTS = 4;

	private static final int HASHTAG_ZERO_POSITION = 0;

	private static final int MAXIMUM_LEVELS_ONE = 1;

	private static final int MAXIMUM_LEVELS_TWO = 2;

	private static final int MAXIMUM_LEVELS_THREE = 3;

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

		List<Status> statuses = buildStatusList(ONE_QUERY_RESULT, ONE_HASHTAG_RESULT);

		when(agent.exhaustiveQuery(QUERY_STRING)).thenReturn(statuses);

		Map<String, Integer> hashtags = spider.spiderHashtags(QUERY_STRING);

		assertNotNull("Spider for Hashtags should not return a null response!", hashtags);
		assertEquals("Spider for Hashtags should return one result!", TWO_HASHTAG_RESULTS, hashtags.size());

		verify(agent).exhaustiveQuery(QUERY_STRING);
	}

	@Test
	public void spider_TestLevelOneTwoHashtags() throws Exception {

		List<Status> statuses = buildStatusList(TWO_QUERY_RESULTS, ONE_HASHTAG_RESULT);

		when(agent.exhaustiveQuery(QUERY_STRING)).thenReturn(statuses);

		Map<String, Integer> hashtags = spider.spiderHashtags(QUERY_STRING);

		assertNotNull("Spider for Hashtags should not return a null response!", hashtags);
		assertEquals("Spider for Hashtags should return two results!", THREE_HASHTAG_RESULTS, hashtags.size());

		verify(agent).exhaustiveQuery(QUERY_STRING);
	}

	@Test
	public void spider_TestLevelTwoOneHashtagEach() throws Exception {

		List<Status> levelOneStatuses = buildStatusList(ONE_QUERY_RESULT, ONE_HASHTAG_RESULT);
		List<Status> levelTwoStatuses = buildStatusList(ONE_QUERY_RESULT, ONE_HASHTAG_RESULT);

		when(agent.exhaustiveQuery(QUERY_STRING)).thenReturn(levelOneStatuses);

		String secondHashtag = levelOneStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities()[0].getText();

		when(agent.exhaustiveQuery(secondHashtag)).thenReturn(levelTwoStatuses);

		Map<String, Integer> hashtags = spider.spiderHashtags(QUERY_STRING);

		assertNotNull("Spider for Hashtags should not return a null response!", hashtags);
		assertEquals("Spider for Hashtags should return two results!", THREE_HASHTAG_RESULTS, hashtags.size());

		verify(agent).exhaustiveQuery(QUERY_STRING);
		verify(agent).exhaustiveQuery(secondHashtag);
	}

	@Test
	public void spider_TestLimitedToLevelOneWithTwoHashtags() throws Exception {

		List<Status> levelOneStatuses = buildStatusList(ONE_QUERY_RESULT, ONE_HASHTAG_RESULT);

		when(agent.exhaustiveQuery(QUERY_STRING)).thenReturn(levelOneStatuses);

		String secondHashtag = levelOneStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities()[0].getText();

		Map<String, Integer> hashtags = spider.spiderHashtags(QUERY_STRING, MAXIMUM_LEVELS_ONE);

		assertNotNull("Spider for Hashtags should not return a null response!", hashtags);
		assertEquals("Spider for Hashtags should return two results!", TWO_HASHTAG_RESULTS, hashtags.size());

		verify(agent).exhaustiveQuery(QUERY_STRING);
		verify(agent, never()).exhaustiveQuery(secondHashtag);
	}

	@Test
	public void spider_TestLimitedToLevelTwoWithThreeHashtags() throws Exception {

		List<Status> levelOneStatuses = buildStatusList(ONE_QUERY_RESULT, ONE_HASHTAG_RESULT);
		List<Status> levelTwoStatuses = buildStatusList(ONE_QUERY_RESULT, ONE_HASHTAG_RESULT);

		when(agent.exhaustiveQuery(QUERY_STRING)).thenReturn(levelOneStatuses);

		String secondHashtag = levelOneStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities()[0].getText();

		when(agent.exhaustiveQuery(secondHashtag)).thenReturn(levelTwoStatuses);

		String thirdHashtag = levelTwoStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities()[0].getText();

		Map<String, Integer> hashtags = spider.spiderHashtags(QUERY_STRING, MAXIMUM_LEVELS_TWO);

		assertNotNull("Spider for Hashtags should not return a null response!", hashtags);
		assertEquals("Spider for Hashtags should return three results!", THREE_HASHTAG_RESULTS, hashtags.size());

		verify(agent).exhaustiveQuery(QUERY_STRING);
		verify(agent).exhaustiveQuery(secondHashtag);
		verify(agent, never()).exhaustiveQuery(thirdHashtag);
	}

	@Test
	public void spider_TestLimitedToLevelThreeWithFourHashtags() throws Exception {

		List<Status> levelOneStatuses = buildStatusList(ONE_QUERY_RESULT, ONE_HASHTAG_RESULT);
		List<Status> levelTwoStatuses = buildStatusList(ONE_QUERY_RESULT, ONE_HASHTAG_RESULT);
		List<Status> levelThreeStatuses = buildStatusList(ONE_QUERY_RESULT, ONE_HASHTAG_RESULT);

		when(agent.exhaustiveQuery(QUERY_STRING)).thenReturn(levelOneStatuses);

		String secondHashtag = levelOneStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities()[0].getText();

		when(agent.exhaustiveQuery(secondHashtag)).thenReturn(levelTwoStatuses);

		String thirdHashtag = levelTwoStatuses.get(HASHTAG_ZERO_POSITION).getHashtagEntities()[0].getText();

		when(agent.exhaustiveQuery(thirdHashtag)).thenReturn(levelThreeStatuses);

		Map<String, Integer> hashtags = spider.spiderHashtags(QUERY_STRING, MAXIMUM_LEVELS_THREE);

		assertNotNull("Spider for Hashtags should not return a null response!", hashtags);
		assertEquals("Spider for Hashtags should return four results!", FOUR_HASHTAG_RESULTS, hashtags.size());

		verify(agent).exhaustiveQuery(QUERY_STRING);
		verify(agent).exhaustiveQuery(secondHashtag);
		verify(agent).exhaustiveQuery(thirdHashtag);
	}

	private List<Status> buildStatusList(int number, int hashtagCount) {

		List<Status> statuses = new ArrayList<>();

		for (int position = 0; position < number; position++) {

			statuses.add(new StubStatus(buildHashtagEntityArray(hashtagCount)));
		}
		return statuses;
	}

	private HashtagEntity[] buildHashtagEntityArray(int number) {

		List<HashtagEntity> hashtagEntities = new ArrayList<>();

		for (int position = 0; position < number; position++) {

			hashtagEntities.add(new StubHashtagEntity(QUERY_STRING + java.util.UUID.randomUUID()));
		}
		return hashtagEntities.toArray(new HashtagEntity[hashtagEntities.size()]);
	}

	private class StubStatus implements Status {

		private static final long serialVersionUID = 1L;

		HashtagEntity[] hashtagEntities;

		public StubStatus(HashtagEntity[] hashtagEntities) {

			this.hashtagEntities = hashtagEntities;
		}

		@Override
		public int compareTo(Status object) {

			throw new UnsupportedOperationException();
		}

		@Override
		public RateLimitStatus getRateLimitStatus() {

			throw new UnsupportedOperationException();
		}

		@Override
		public int getAccessLevel() {

			throw new UnsupportedOperationException();
		}

		@Override
		public UserMentionEntity[] getUserMentionEntities() {

			throw new UnsupportedOperationException();
		}

		@Override
		public URLEntity[] getURLEntities() {

			throw new UnsupportedOperationException();
		}

		@Override
		public HashtagEntity[] getHashtagEntities() {

			return hashtagEntities;
		}

		@Override
		public MediaEntity[] getMediaEntities() {

			throw new UnsupportedOperationException();
		}

		@Override
		public ExtendedMediaEntity[] getExtendedMediaEntities() {

			throw new UnsupportedOperationException();
		}

		@Override
		public SymbolEntity[] getSymbolEntities() {

			throw new UnsupportedOperationException();
		}

		@Override
		public Date getCreatedAt() {

			throw new UnsupportedOperationException();
		}

		@Override
		public long getId() {

			throw new UnsupportedOperationException();
		}

		@Override
		public String getText() {

			throw new UnsupportedOperationException();
		}

		@Override
		public String getSource() {

			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isTruncated() {

			throw new UnsupportedOperationException();
		}

		@Override
		public long getInReplyToStatusId() {

			throw new UnsupportedOperationException();
		}

		@Override
		public long getInReplyToUserId() {

			throw new UnsupportedOperationException();
		}

		@Override
		public String getInReplyToScreenName() {

			throw new UnsupportedOperationException();
		}

		@Override
		public GeoLocation getGeoLocation() {

			throw new UnsupportedOperationException();
		}

		@Override
		public Place getPlace() {

			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isFavorited() {

			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isRetweeted() {

			throw new UnsupportedOperationException();
		}

		@Override
		public int getFavoriteCount() {

			throw new UnsupportedOperationException();
		}

		@Override
		public User getUser() {

			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isRetweet() {

			throw new UnsupportedOperationException();
		}

		@Override
		public Status getRetweetedStatus() {

			throw new UnsupportedOperationException();
		}

		@Override
		public long[] getContributors() {

			throw new UnsupportedOperationException();
		}

		@Override
		public int getRetweetCount() {

			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isRetweetedByMe() {

			throw new UnsupportedOperationException();
		}

		@Override
		public long getCurrentUserRetweetId() {

			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isPossiblySensitive() {

			throw new UnsupportedOperationException();
		}

		@Override
		public String getLang() {

			throw new UnsupportedOperationException();
		}

		@Override
		public Scopes getScopes() {

			throw new UnsupportedOperationException();
		}

		@Override
		public String[] getWithheldInCountries() {

			throw new UnsupportedOperationException();
		}

		@Override
		public long getQuotedStatusId() {

			throw new UnsupportedOperationException();
		}

		@Override
		public Status getQuotedStatus() {

			throw new UnsupportedOperationException();
		}

	}

	private class StubHashtagEntity implements HashtagEntity {

		private static final long serialVersionUID = 1L;

		private String hashtag;

		private int start = 0;

		private int end = 0;

		public StubHashtagEntity(String hashtag) {

			this.hashtag = hashtag;
			this.end = hashtag.length() - 1;
		}

		@Override
		public String getText() {

			return this.hashtag;
		}

		@Override
		public int getStart() {

			return this.start;
		}

		@Override
		public int getEnd() {

			return this.end;
		}
	}
}
