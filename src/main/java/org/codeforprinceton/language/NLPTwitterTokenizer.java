/**
 * Copyright of Code for Princeton (c) 2016.
 */
package org.codeforprinceton.language;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import opennlp.tools.tokenize.TokenizerModel;
import twitter4j.Status;

/**
 * Natural Language Parser (NLP) Twitter Tokenizer.
 * <p>
 * Tokenizer that takes Twitter Statuses and tokenize them so that they can be analyzed.
 * </p>
 * 
 * @author BoggyBumblebee
 */
public class NLPTwitterTokenizer {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Takes a List of Twitter Statuses and tokenize them so they can be analyzed.
	 * 
	 * @param statuses a List of Twitter Statuses
	 */
	public void tokenizeStatuses(List<Status> statuses) {

		TokenizerModel model = openTokenizerModel();

		if (model != null) {

			for (Status status : statuses) {

				status.getHashtagEntities();
			}

		}
	}

	private TokenizerModel openTokenizerModel() {

		InputStream modelIn = openTokenizerStream();
		TokenizerModel model = null;

		try {

			model = new TokenizerModel(modelIn);
		}
		catch (IOException exception) {

			logger.error("Failed to open (I/O) the Tokenizer Model!", exception);
			return null;
		}
		finally {

			if (modelIn != null) {

				try {

					modelIn.close();
					modelIn = null;
				}
				catch (IOException exception) {

					logger.error("Failed to close (I/O) the Tokenizer Model!", exception);
				}
			}
		}
		return model;
	}

	private InputStream openTokenizerStream() {

		try {

			return new FileInputStream("en-token.bin");
		}
		catch (FileNotFoundException exception) {

			logger.error("File not found!", exception);
			return null;
		}
	}

}
