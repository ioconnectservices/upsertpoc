package com.iocs.manual;

import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

public class CSVFileReader implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
MuleMessage message = eventContext.getMessage();
		
		BufferedReader reader = message.getInvocationProperty("newInputStream");
		Integer batchSize = Integer.parseInt(message.getInvocationProperty("batchSize").toString());
		List<Map<String, String>> parsedFields = message.getInvocationProperty("parsedFields");
		
		int current = 0;
		String line = null;
		
		while((line = reader.readLine()) != null) {
			current = current + 1;
			String[] csvs = line.split(",");
			if (csvs == null || csvs.length < 40) {
				continue;
			}
			
			buildMap(parsedFields, csvs);
			
			if (current >= batchSize) {
				break;
			}
			
		}
		
		// Reader got to the last line in the file, so we need to freed the resource
		if (line == null) {
			reader.close();
		}
		
		return message;		
	}

	private void buildMap(List<Map<String, String>> list, String[] csvs) {
		Map<String, String>  map = new LinkedHashMap<String, String>();
		list.add(map);
		
		for (int i=0; i<csvs.length; i++) {
			String key = "Column_" + (i + 1) + "_In_Row_0";
			String value = csvs[i];
			map.put(key, value);
		}
	}
}
