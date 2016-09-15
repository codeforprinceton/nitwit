/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.nitwit;

import org.codeforprinceton.nitwit.spider.TwitterSpiderIntegrationTests;
import org.codeforprinceton.nitwit.twitter.TwitterSearchAgentIntegrationTests;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * JUnit Test Suite for Integration Tests. It excludes all Unit Tests.
 * 
 * @author BoggyBumblebee
 */
@RunWith(Categories.class)
@IncludeCategory(IntegrationTestCategory.class)
@ExcludeCategory(UnitTestCategory.class)
@SuiteClasses({ TwitterSearchAgentIntegrationTests.class, TwitterSpiderIntegrationTests.class })
public class IntegrationTestsSuite {

	// Purposefully empty
}
