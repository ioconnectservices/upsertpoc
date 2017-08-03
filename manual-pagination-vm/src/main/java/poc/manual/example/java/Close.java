package poc.manual.example.java;

import java.io.BufferedReader;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class Close implements Callable{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		BufferedReader reader = (BufferedReader)eventContext.getMessage().getInvocationProperty("fileReader");
		
		if(reader != null)
			reader.close();
		
		return eventContext;
	}

}
