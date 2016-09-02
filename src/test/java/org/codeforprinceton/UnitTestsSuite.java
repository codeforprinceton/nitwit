/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@IncludeCategory(UnitTestCategory.class)
@ExcludeCategory(IntegrationTestCategory.class)
@SuiteClasses({})
public class UnitTestsSuite {

	// Purposefully empty
}
