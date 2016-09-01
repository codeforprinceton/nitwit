package org.codeforprinceton.twitter;

import java.util.HashSet;
import java.util.Set;

/**
 * Twitter Search Agent.
 * <p>
 * Agent that conducts Twitter searches based upon the passed criteria.
 * </p>
 * 
 * @author BoggyBumblebee
 */
public class TwitterSearchAgent {

	/**
	 * Simple Query, accepting just a single query String.
	 * 
	 * @param simpleQueryString the simple query String
	 * @return a Set of Hashtags
	 */
	public Set<String> simpleQueryForHashTags(String simpleQueryString) {

		Set<String> hashtags = new HashSet<>();

		hashtags.add("Dummy!");
		return hashtags;
	}

}
