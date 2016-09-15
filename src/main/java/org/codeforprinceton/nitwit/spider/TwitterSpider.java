
/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.nitwit.spider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codeforprinceton.nitwit.twitter.TwitterSearchAgent;

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

	private TwitterSearchAgent agent;

	public TwitterSpider(TwitterSearchAgent agent) {

		this.agent = agent;
	}

	/**
	 * @param hashtag
	 * @return
	 */
	public List<String> spiderHashtags(String hashtag) {

		Set<String> hashtags = new HashSet<>();

		hashtags.add(stripHashFromHashtag(hashtag));
		extractHashtagsFromStatuses(agent.exhaustiveQuery(hashtag), hashtags);

		return new ArrayList<>(hashtags);
	}

	private void extractHashtagsFromStatuses(List<Status> statuses, Set<String> hashtags) {

		if (null != statuses && !statuses.isEmpty()) {

			for (Status status : statuses) {

				extractHashtagFromHashtagEntities(status.getHashtagEntities(), hashtags);
			}
		}
	}

	private void extractHashtagFromHashtagEntities(HashtagEntity[] hashtagEntities, Set<String> hashtags) {

		for (HashtagEntity hashtag : hashtagEntities) {

			if (!hashtags.contains(hashtag.getText())) {

				hashtags.add(hashtag.getText());
				hashtags.addAll(spiderHashtags(hashtag.getText()));
			}
		}
	}
	
	private String stripHashFromHashtag(String hashtag) {
		
		return hashtag.replaceAll("#[A-Za-z]+","");
	}
}
