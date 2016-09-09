/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.spider;

import static org.junit.Assert.fail;

import org.codeforprinceton.IntegrationTestCategory;
import org.codeforprinceton.twitter.TwitterSearchAgent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Twitter Spider Integration Tests.
 * <p>
 * Covers all possible paths, happy or exception. Will connect to Twitter using OAuth credentials, that need to provided
 * as Runtime or Environment variables.
 * </p>
 * 
 * @author BoggyBumblebee
 */
@Category(IntegrationTestCategory.class)
public class TwitterSpiderIntegrationTests {

	private TwitterSearchAgent agent = null;
	
	private TwitterSpider spider = null;
	
	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void test() {

		fail("Not yet implemented");
	}

}
