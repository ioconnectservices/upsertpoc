package poc.manual.example.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

public class PageNumber implements Callable{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		FileInputStream is = (FileInputStream) eventContext.getMessage().getPayload();
		BufferedReader stream = new BufferedReader(new InputStreamReader(is));
		
		String header = stream.readLine();
		eventContext.getMessage().setInvocationProperty("pageHeader", header);
		
		int lines = 0;
		while(stream.readLine() != null)
			lines++;
		
		stream.close();
		
		int rgPgNb = 850;
		int pagesNb = lines % rgPgNb == 0 ? lines/rgPgNb : (lines/rgPgNb) + 1;
		
		eventContext.getMessage().setInvocationProperty("registers", rgPgNb);
		eventContext.getMessage().setInvocationProperty("pages", pagesNb);
		eventContext.getMessage().setInvocationProperty("lines", lines);
		
		return pagination(eventContext);
		
	}
	
	private List<String> pagination(MuleEventContext eventContext) throws IOException{
		MuleMessage mMsj= eventContext.getMessage();
		
		StringBuilder filepaTh = new StringBuilder(mMsj.getInvocationProperty("moveToDirectory"));
		filepaTh.append(File.separatorChar).append((String)mMsj.getInvocationProperty("originalFilename"));
		
		FileInputStream is = new FileInputStream(new File(filepaTh.toString()));
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		reader.readLine();
		
		mMsj.setInvocationProperty("fileReader", reader);
		
		int pagesC = mMsj.getInvocationProperty("pages");
		String header = mMsj.getInvocationProperty("pageHeader");
		
		List<String> pages = new ArrayList<String>(pagesC);
		
		for(int i=0; i<pagesC; i++)
			pages.add(header);
		
		return pages;
	}
}