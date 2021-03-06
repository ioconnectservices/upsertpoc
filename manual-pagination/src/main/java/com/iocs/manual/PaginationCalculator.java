package com.iocs.manual;

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
		Integer batchSize = Integer.parseInt(message.getInvocationProperty("batchSize").toString());
		InputStream inputStream = message.getPayload(InputStream.class);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		int lines = (int) reader.lines().count();
		reader.close();
		
		int numberOfBatches = (lines%batchSize == 0) ? lines/batchSize : lines/batchSize + 1;
		List<Integer> pages = new ArrayList<>();
		for (int i=0; i<numberOfBatches; i++) {
			pages.add(i);
		}
		
		message.setInvocationProperty("pages", pages);
		
		return message;
	}

}
