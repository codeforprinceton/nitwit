/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.twitter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Twitter Search Agent.
 * <p>
 * Agent that conducts Twitter searches based upon the passed criteria.
 * </p>
 * 
 * @author BoggyBumblebee
 */
public class TwitterSearchAgent {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final int DEFAULT_RESULTS = 15;

	private static final int MAX_RESULTS = 100;

	private Twitter twitter;

	/**
	 * Default constructor.
	 * 
	 * @param twitter the Twitter Singleton
	 */
	public TwitterSearchAgent(Twitter twitter) {

		super();
		this.twitter = twitter;
	}

	/**
	 * Simple Query, accepting just a single query String.
	 * 
	 * @param simpleQueryString the simple query String
	 * @return a List of Tweets
	 */
	public List<Status> simpleQuery(String simpleQueryString) {

		return simpleQuery(simpleQueryString, DEFAULT_RESULTS);
	}

	/**
	 * Simple Query, accepting just a single query String.
	 * 
	 * @param simpleQueryString the simple query String
	 * @param maxResults the maximum number of results to return (maxs out at 100)
	 * @return a List of Tweets
	 */
	public List<Status> simpleQuery(String simpleQueryString, int maxResults) {

		Query query = new Query(simpleQueryString);

		if (maxResults != DEFAULT_RESULTS) {

			if (maxResults <= MAX_RESULTS) {

				query.setCount(maxResults);
			}
			else {

				query.setCount(MAX_RESULTS);
			}
		}

		QueryResult result = null;

		try {

			result = twitter.search(query);
		}
		catch (TwitterException exception) {

			logger.warn("Failed to query Twitter!", exception);
			return new ArrayList<>();
		}
		return result.getTweets();
	}

}
