package com.iocs.manual;

import java.io.BufferedReader;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

public class QueryBuilder implements Callable {
	private static final String BREAK_LINE = System.getProperty("line.separator");

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage message = eventContext.getMessage();
		
		BufferedReader reader = message.getInvocationProperty("newInputStream");
		Integer batchSize = Integer.parseInt(message.getInvocationProperty("batchSize").toString());
		
		StringBuilder query = message.getInvocationProperty("query");
		int current = 0;
		String line = null;
		
		while((line = reader.readLine()) != null) {
			String stmt = buildQuery(line);
			if (stmt == null || stmt.isEmpty()) {
				continue;
			}
			
			query.append(stmt);
			current = current + 1;
			
			if (current >= batchSize) {
				break;
			}
			
		}

		message.setInvocationProperty("query", query);
		
		return message;
	}

	private String buildQuery(String line) {
		String[] csvs = line.split(",");
		if (csvs == null || csvs.length < 40) {
			return "";
		}
		
		return "INSERT INTO superbig VALUES ('" + csvs[0] + "','" + csvs[1] + "','" + csvs[2] + "','" + csvs[3] + "','"
				+ csvs[4] + "','" + csvs[5] + "','" + csvs[6] + "','" + csvs[7] + "','" + csvs[8] + "','" + csvs[9]
				+ "','" + csvs[10] + "','" + csvs[11] + "','" + csvs[12] + "','" + csvs[13] + "','" + csvs[14] + "','"
				+ csvs[15] + "','" + csvs[16] + "','" + csvs[17] + "','" + csvs[18] + "','" + csvs[19] + "','"
				+ csvs[20] + "','" + csvs[21] + "','" + csvs[22] + "','" + csvs[23] + "','" + csvs[24] + "','"
				+ csvs[25] + "','" + csvs[26] + "','" + csvs[27] + "','" + csvs[28] + "','" + csvs[29] + "','"
				+ csvs[30] + "','" + csvs[31] + "','" + csvs[32] + "','" + csvs[33] + "','" + csvs[34] + "','"
				+ csvs[35] + "','" + csvs[36] + "','" + csvs[37] + "','" + csvs[38] + "','" + csvs[39] + "') "
				+ "ON DUPLICATE KEY UPDATE Column_2_In_Row_0 = VALUES(Column_2_In_Row_0), "
				+ "Column_3_In_Row_0 = VALUES(Column_3_In_Row_0), Column_4_In_Row_0 = VALUES(Column_4_In_Row_0), "
				+ "Column_5_In_Row_0 = VALUES(Column_5_In_Row_0), Column_6_In_Row_0 = VALUES(Column_6_In_Row_0), "
				+ "Column_7_In_Row_0 = VALUES(Column_7_In_Row_0), Column_8_In_Row_0 = VALUES(Column_8_In_Row_0), "
				+ "Column_9_In_Row_0 = VALUES(Column_9_In_Row_0), Column_10_In_Row_0 = VALUES(Column_10_In_Row_0), "
				+ "Column_11_In_Row_0 = VALUES(Column_11_In_Row_0), Column_12_In_Row_0 = VALUES(Column_12_In_Row_0), "
				+ "Column_13_In_Row_0 = VALUES(Column_13_In_Row_0), Column_14_In_Row_0 = VALUES(Column_14_In_Row_0), "
				+ "Column_15_In_Row_0 = VALUES(Column_15_In_Row_0), Column_16_In_Row_0 = VALUES(Column_16_In_Row_0), "
				+ "Column_17_In_Row_0 = VALUES(Column_17_In_Row_0), Column_18_In_Row_0 = VALUES(Column_18_In_Row_0), "
				+ "Column_19_In_Row_0 = VALUES(Column_19_In_Row_0), Column_20_In_Row_0 = VALUES(Column_20_In_Row_0), "
				+ "Column_21_In_Row_0 = VALUES(Column_21_In_Row_0), Column_22_In_Row_0 = VALUES(Column_22_In_Row_0), "
				+ "Column_23_In_Row_0 = VALUES(Column_23_In_Row_0), Column_24_In_Row_0 = VALUES(Column_24_In_Row_0), "
				+ "Column_25_In_Row_0 = VALUES(Column_25_In_Row_0), Column_26_In_Row_0 = VALUES(Column_26_In_Row_0), "
				+ "Column_27_In_Row_0 = VALUES(Column_27_In_Row_0), Column_28_In_Row_0 = VALUES(Column_28_In_Row_0), "
				+ "Column_29_In_Row_0 = VALUES(Column_29_In_Row_0), Column_30_In_Row_0 = VALUES(Column_30_In_Row_0), "
				+ "Column_31_In_Row_0 = VALUES(Column_31_In_Row_0), Column_32_In_Row_0 = VALUES(Column_32_In_Row_0), "
				+ "Column_33_In_Row_0 = VALUES(Column_33_In_Row_0), Column_34_In_Row_0 = VALUES(Column_34_In_Row_0), "
				+ "Column_35_In_Row_0 = VALUES(Column_35_In_Row_0), Column_36_In_Row_0 = VALUES(Column_36_In_Row_0), "
				+ "Column_37_In_Row_0 = VALUES(Column_37_In_Row_0), Column_38_In_Row_0 = VALUES(Column_38_In_Row_0), "
				+ "Column_39_In_Row_0 = VALUES(Column_39_In_Row_0), Column_40_In_Row_0 = VALUES(Column_40_In_Row_0);"
				+ BREAK_LINE;
	}
}
