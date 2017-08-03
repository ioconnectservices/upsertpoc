package poc.manual.example.java;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

public class SendData implements Callable{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage msj = eventContext.getMessage();
		
		BufferedReader reader = (BufferedReader)msj.getInvocationProperty("fileReader");
		List<Map> lista = new ArrayList<>();
		int registers = msj.getInvocationProperty("registers");
		
		for(int r=0; r<registers;r++){
			Map<String, String> map = new HashMap<>();
			String line = reader.readLine();
			
			if(line == null){
				reader.close();
				break;
			}
			
			String[] columns = line.split(",");
			
			for(int i=1; i<=40; i++){
				map.put("Column_"+i+"_In_Row_0", columns[i-1]);
			}
			lista.add(map);
		}		
		
		
		return lista;
	}

}
