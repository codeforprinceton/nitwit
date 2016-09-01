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

	private Twitter twitter;

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

		Query query = new Query(simpleQueryString);
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
