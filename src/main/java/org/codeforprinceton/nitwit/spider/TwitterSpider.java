/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.nitwit.spider;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.codeforprinceton.nitwit.twitter.TwitterSearchAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.HashtagEntity;
import twitter4j.Status;

/**
 * Twitter Spider.
 * <p>
 * Spider that runs Twitter searches based upon the passed criteria.
 * </p>
 * 
 * @author BoggyBumblebee
 */
public class TwitterSpider {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final int DEFAULT_LEVELS = 0;

	private static final int DEFAULT_COUNT = 1;

	private TwitterSearchAgent agent;

	public TwitterSpider(TwitterSearchAgent agent) {

		this.agent = agent;
	}

	/**
	 * Spiders Twitter based upon a starting Hashtag.
	 * 
	 * @param hashtag the Hashtag
	 * @return a Map of Hashtags
	 */
	public Map<String, Integer> spiderHashtags(String hashtag) {

		return spiderHashtags(hashtag, DEFAULT_LEVELS);
	}

	/**
	 * Spiders Twitter based upon a starting Hashtag, and will limit how many maxLevels down we can go.
	 * 
	 * @param hashtag the Hashtag
	 * @param maxLevels the Number of Levels down we can go
	 * @return a Map of Hashtags
	 */
	public Map<String, Integer> spiderHashtags(String hashtag, int maxLevels) {

		return spiderHashtags(hashtag, maxLevels, DEFAULT_LEVELS);
	}

	private Map<String, Integer> spiderHashtags(String hashtag, int maxLevels, int currentLevel) {

		Map<String, Integer> hashtags = new HashMap<>();

		hashtags.put(hashtag, DEFAULT_COUNT);

		logger.info("Executing search using hashtag '" + hashtag + "' at level " + currentLevel + " of " + maxLevels);
		extractHashtagsFromStatuses(agent.exhaustiveQuery(hashtag), hashtags, maxLevels, currentLevel);

		return sortByValue(hashtags);
	}

	private void extractHashtagsFromStatuses(List<Status> statuses, Map<String, Integer> hashtags, int maxLevels,
			int currentLevel) {

		currentLevel++;

		if (null != statuses && !statuses.isEmpty()) {

			for (Status status : statuses) {
				extractHashtagFromHashtagEntities(status.getHashtagEntities(), hashtags, maxLevels, currentLevel);
			}
		}

		currentLevel--;
	}

	private void extractHashtagFromHashtagEntities(HashtagEntity[] hashtagEntities, Map<String, Integer> hashtags,
			int maxLevels, int currentLevel) {

		for (HashtagEntity hashtag : hashtagEntities) {

			if (!hashtags.containsKey(hashtag.getText())) {

				hashtags.put(hashtag.getText(), DEFAULT_COUNT);

				if (!isMaxLevel(maxLevels, currentLevel)) {

					merge(hashtags, spiderHashtags(hashtag.getText(), maxLevels, currentLevel));
				}
			}
			else {

				hashtags.put(hashtag.getText(), hashtags.get(hashtag.getText()) + DEFAULT_COUNT);
			}
		}
	}

	private void merge(Map<String, Integer> runningHashtags, Map<String, Integer> newHashtags) {

		for (String key : newHashtags.keySet()) {

			if (runningHashtags.containsKey(key)) {

				runningHashtags.put(key, runningHashtags.get(key) + newHashtags.get(key));
			}
			else {

				runningHashtags.put(key, newHashtags.get(key));
			}
		}
	}

	private Boolean isMaxLevel(int maxLevels, int currentLevel) {

		if (DEFAULT_LEVELS == maxLevels || currentLevel < maxLevels) {

			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {

		return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}
}
