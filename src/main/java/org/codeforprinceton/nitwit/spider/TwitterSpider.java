
/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.nitwit.spider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	private TwitterSearchAgent agent;

	public TwitterSpider(TwitterSearchAgent agent) {

		this.agent = agent;
	}

	/**
	 * Spiders Twitter based upon a starting Hashtag.
	 * 
	 * @param hashtag the Hashtag
	 * @return a List of Hashtags
	 */
	public List<String> spiderHashtags(String hashtag) {

		return spiderHashtags(hashtag, DEFAULT_LEVELS);
	}

	/**
	 * Spiders Twitter based upon a starting Hashtag, and will limit how many maxLevels down we can go.
	 * 
	 * @param hashtag the Hashtag
	 * @param maxLevels the Number of Levels down we can go
	 * @return a List of Hashtags
	 */
	public List<String> spiderHashtags(String hashtag, int maxLevels) {

		return spiderHashtags(hashtag, maxLevels, DEFAULT_LEVELS);
	}

	private List<String> spiderHashtags(String hashtag, int maxLevels, int currentLevel) {

		Set<String> hashtags = new HashSet<>();

		hashtags.add(hashtag);

		logger.info("Executing search using hashtag '" + hashtag + "' at level " + currentLevel + " of " + maxLevels);
		extractHashtagsFromStatuses(agent.exhaustiveQuery(hashtag), hashtags, maxLevels, currentLevel);

		return new ArrayList<>(hashtags);
	}

	private void extractHashtagsFromStatuses(List<Status> statuses, Set<String> hashtags, int maxLevels,
			int currentLevel) {

		currentLevel++;

		if (null != statuses && !statuses.isEmpty()) {

			for (Status status : statuses) {
				extractHashtagFromHashtagEntities(status.getHashtagEntities(), hashtags, maxLevels, currentLevel);
			}
		}

		currentLevel--;
	}

	private void extractHashtagFromHashtagEntities(HashtagEntity[] hashtagEntities, Set<String> hashtags, int maxLevels,
			int currentLevel) {

		for (HashtagEntity hashtag : hashtagEntities) {

			if (!hashtags.contains(hashtag.getText())) {

				hashtags.add(hashtag.getText());

				if (!isMaxLevel(maxLevels, currentLevel)) {

					hashtags.addAll(spiderHashtags(hashtag.getText(), maxLevels, currentLevel));
				}
			}
		}

	}

	private Boolean isMaxLevel(int maxLevels, int currentLevel) {

		if (DEFAULT_LEVELS == maxLevels || currentLevel < maxLevels) {

			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}
