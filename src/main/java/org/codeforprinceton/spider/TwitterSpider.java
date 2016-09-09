package org.codeforprinceton.spider;

import java.util.ArrayList;
import java.util.List;

import org.codeforprinceton.twitter.TwitterSearchAgent;

import twitter4j.HashtagEntity;
import twitter4j.Status;

public class TwitterSpider {

	private TwitterSearchAgent agent;

	public TwitterSpider(TwitterSearchAgent agent) {

		this.agent = agent;
	}

	public List<String> spiderHashtags(String hashtag) {

		return extractHashtagsFromStatuses(agent.exhaustiveQuery(hashtag));
	}

	private List<String> extractHashtagsFromStatuses(List<Status> statuses) {

		List<String> hashtags = new ArrayList<>();

		if (null != statuses && !statuses.isEmpty()) {

			for (Status status : statuses) {

				hashtags.addAll(extractHashtagFromHashtagEntities(status.getHashtagEntities()));
			}
		}
		return hashtags;
	}

	private List<String> extractHashtagFromHashtagEntities(HashtagEntity[] hashtagEntities) {

		List<String> hashtags = new ArrayList<>();

		for (HashtagEntity hashtag : hashtagEntities) {

			hashtags.add(hashtag.getText());
		}
		return hashtags;
	}
}
