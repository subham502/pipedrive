package com.hof.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

public class DealFields {
	
	String[] id;
	static String[] key;
	static String[] field_type;
	static String[] key1;
	static String[] field_type1;
	static int size1;
	Map<String, List<String>> map1 = new HashMap<String, List<String>>();
	Map<String, String> fieldKey = new HashMap<String, String>();
	Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL).addOptions(Option.SUPPRESS_EXCEPTIONS).addOptions(Option.ALWAYS_RETURN_LIST);
	static int size;
	static int sizeX;
	
	public void formatData(JSONArray resp)
	{
			
		this.size = resp.length();
		
		this.id = new String[size];
		this.key = new String[size];
		this.field_type = new String[size];

		for(int i=0; i<size; i++)
		{
			
			JSONObject x = resp.getJSONObject(i);			
			try {
				this.id[i] = x.get("id").toString();
			} catch (JSONException e9) {
				// TODO Auto-generated catch block
			}
			try {
				this.key[i] = x.get("key").toString();
			} catch (JSONException e9) {
				// TODO Auto-generated catch block
			}
			try {
				String obj = x.get("field_type").toString();
				if(obj.equals("varchar"))
					this.field_type[i] = "TEXT";
				else if(obj.equals("double"))
					this.field_type[i] = "NUMERIC";
				else if(obj.equals("monetary"))
					this.field_type[i] = "NUMERIC";
				else if(obj.equals("int"))
					this.field_type[i] = "NUMERIC";
				/*else if(obj.equals("stage"))
					this.field_type[i] = "NUMERIC";
				else if(obj.equals("stage"))
					this.field_type[i] = "NUMERIC";*/
				else if(obj.equals("date"))
					this.field_type[i] = "DATE";
				else
					this.field_type[i] = "TEXT";
			} catch (JSONException e10) {
			}
		}
		for(int i=0; i<size; i++)
		{
			fieldKey.put(key[i], field_type[i]);
		}
		
	}
	public void formatDealsData(JSONArray deals)
	{		
		//System.out.println("=======deals data======"+deals.toString());
		JSONObject resultObj1 = deals.getJSONObject(0);
		sizeX = resultObj1.length();
		this.key1 = new String[sizeX];
		this.field_type1 = new String[sizeX];
		int z = 0;
		
		size1 = deals.length();		
		
		/*try{	
			for (Map.Entry<String, String> entry2 :fieldKey.entrySet())
			{
				System.out.println(entry2.getKey() + "/" + entry2.getValue());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		//List<String> keysList = new ArrayList<String>();
		
		Iterator keys = resultObj1.keys();
		while(keys.hasNext()) 
		{
		    String key2 = (String) keys.next();
		    key1[z] = key2;
		    if(fieldKey.get(key2) == null)
		    	 field_type1[z] = "TEXT";
		    else
		    	field_type1[z] = fieldKey.get(key2);
		    //keysList.add(key2);
		    z++;
		}
		/*System.out.println("=========================================================");
		for(int y = 0;y<sizeX;y++)
			System.out.println(key1[y]+"/"+field_type1[y]);*/
		
		//String[] kesyArray = keysList.toArray(new String[keysList.size()]);		
		//System.out.println(keysList.toString());		
		
		
		DocumentContext tt = JsonPath.using(conf).parse(deals.toString());
		
		//System.out.println("=======keys data======"+getKey(0)+" ===="+key[0]);
		for(int j=0;j<sizeX;j++)
		{			
			List<String> array2 = tt.read("$[*]."+key1[j]);
			String arr3 = array2.toString();
			if(arr3.contains("name"))
				map1.put(key1[j], (List<String>) tt.read("$[*]."+key1[j]+".name"));
			else
				map1.put(key1[j], (List<String>) tt.read("$[*]."+key1[j]));
		}
		/*System.out.println("8888888888888888888888888888***********"+map1.size());
		try {
			for (Map.Entry<String, List<String>> entry :map1.entrySet())
			{
				System.out.println(entry.getKey() + "/" + entry.getValue());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
	}
	
	public Map<String, List<String>> getMap1() {
		return map1;
	}
	
	public String getId(int index){

		   return this.id[index];

	}

	public String getKey(int index){

	   return this.key[index];

	}
	public String getFieldType(int index){

	   return this.field_type[index];

	}
	
	public String getKey1(int index){

	   return this.key1[index];

	}
	public String getFieldType1(int index){

	   return this.field_type1[index];

	}
	
	public int getSize(){
		
		return this.size;
		
	}
	
	public int getSizeX(){
			
		return this.sizeX;
		
	}
	
	public int getSize1(){
			
			return this.size1;
			
		}
}
