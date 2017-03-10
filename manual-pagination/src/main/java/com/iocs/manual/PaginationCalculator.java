package com.iocs.manual;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

public class PaginationCalculator implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage message = eventContext.getMessage();
		Integer batchSize = Integer.parseInt(message.getInvocationProperty("batchSize", "100").toString());
		InputStream inputStream = message.getPayload(InputStream.class);
		BufferedInputStream in = new BufferedInputStream(inputStream);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		int lines = (int) reader.lines().count();
		reader.close();

		int numberOfBatches = (lines/batchSize);
		List<Integer> pages = new ArrayList<>();
		for (int i=0; i<=numberOfBatches; i++) {
			pages.add(i * numberOfBatches);
		}
		
		message.setInvocationProperty("pages", pages);
		
		return message;
	}

}
