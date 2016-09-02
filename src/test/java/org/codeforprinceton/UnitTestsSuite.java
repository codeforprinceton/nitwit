/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton;

import org.codeforprinceton.twitter.TwitterSearchAgentUnitTests;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * JUnit Test Suite for Unit Tests. It excludes all Integration Tests.
 * 
 * @author BoggyBumblebee
 */
@RunWith(Categories.class)
@IncludeCategory(UnitTestCategory.class)
@ExcludeCategory(IntegrationTestCategory.class)
@SuiteClasses({ TwitterSearchAgentUnitTests.class })
public class UnitTestsSuite {

	// Purposefully empty
}
