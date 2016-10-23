/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.nitwit.rest;

import java.util.HashMap;
import java.util.Map;

/**
 * Twitter Response Map POJO.
 * 
 * @author BoggyBumblebee
 */
public class TwitterResponseMap {

	private Map<String, Integer> hashtags = new HashMap<>();

	public TwitterResponseMap(Map<String, Integer> hashtags) {

		this.hashtags = hashtags;
	}

	public Map<String, Integer> getHashtags() {

		return hashtags;
	}

	public void setHashtags(Map<String, Integer> hashtags) {

		this.hashtags = hashtags;
	}

}
