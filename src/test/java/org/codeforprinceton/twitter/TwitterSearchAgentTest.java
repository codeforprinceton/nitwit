package org.codeforprinceton.twitter;

import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TwitterSearchAgentTest {

	private static final String SIMPLE_QUERY_STRING = "boggybumblebee";
	
	@Before
	public void setUp() throws Exception {

		
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void simpleQueryForHashtags_Test() {

		TwitterSearchAgent agent = new TwitterSearchAgent();
		
		Set<String> hashtags = agent.simpleQueryForHashTags(SIMPLE_QUERY_STRING);
		
		assertNotNull("Simple Query for Hashtags should return at least one result!", hashtags);
	}

}
