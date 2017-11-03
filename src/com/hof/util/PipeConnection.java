package com.hof.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class PipeConnection {
	public String getResp(String token,String view)
	{
		String result = null;
		try {
			URL url = new URL("https://api.pipedrive.com/v1/"+view+"?api_token="+token);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			
			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			
			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
		return result;
	}
	public JSONArray getFields(String token, int maxRecords,String views)
	{
		
		JSONArray resultArr = new JSONArray();
		String result = null;
		int count =1;
		int a,b;
		if(maxRecords >500){
			a = (int) Math.ceil(maxRecords/500.0)+1;
			b = 500;
		}
		else{
			a = 2;
			b = maxRecords;
		}
		
		//System.out.println(a);
		
		while(count <a){
			int start = (count-1)*500;
			if(count == a-1 && maxRecords%500 !=0)
				b=maxRecords%500;
			try {
				URL url = new URL("https://api.pipedrive.com/v1/"+views+"?start="+start+"&limit="+b+"&api_token="+token);
				//System.out.println(url);
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				//System.out.println(connection.getResponseCode());
				
				InputStream is = connection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				
				int numCharsRead;
				char[] charArray = new char[1024];
				StringBuffer sb = new StringBuffer();
				while ((numCharsRead = isr.read(charArray)) > 0) {
					sb.append(charArray, 0, numCharsRead);
				}
				result = sb.toString();
				JSONObject obj = new JSONObject(result);
				
				JSONArray arr = obj.getJSONArray("data");
				
			    for (int i = 0; i < arr.length(); i++) {
		            resultArr.put(arr.get(i));
		        }

				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
			count++;
		}
		
		return resultArr;
	}

}
