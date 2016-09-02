/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.language;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import twitter4j.Status;

public class NLPTwitterTokenizerTest {

	@InjectMocks
	NLPTwitterTokenizer tokenzier = null;

	@Before
	public void setUp() throws Exception {

		tokenzier = new NLPTwitterTokenizer();
	}

	@After
	public void tearDown() throws Exception {

		tokenzier = null;
	}

	@Test
	public void tokenizeStatuses_Test() {

		List<Status> statuses = new ArrayList<>();

		tokenzier.tokenizeStatuses(statuses);
	}

}
