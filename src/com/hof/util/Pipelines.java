package com.hof.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

public class Pipelines {
		
		public final static Logger logger = Logger.getLogger(Pipelines.class.getName());
		
		String[] id = null;
		String[] name = null;
		String[] url_title = null;
		String[] order_nr = null;
		String[] active = null;
		String[] add_time = null;
		String[] update_time = null;
		String[] selected = null;
	    int pipeSize;
	    
	    Map pipeMap = new HashMap();
	    //Format data for Lead   
		public void getPipe(JSONArray arr)
		{
			
		    //JSONArray arr = obj.getJSONArray("data");		        
		    pipeSize = arr.length();
	        
		    id = new String[pipeSize];
		    name = new String[pipeSize];
		    url_title = new String[pipeSize];
		    order_nr = new String[pipeSize];
		    active = new String[pipeSize];
		    add_time = new String[pipeSize];
		    update_time = new String[pipeSize];
		    selected = new String[pipeSize];
	        
	        for(int i=0;i<pipeSize;i++)
	        {
	        	try{
	        		try{
	        			id[i] = (String) arr.getJSONObject(i).get("id").toString();
	        		}catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			//e.printStackTrace();
	        		}
	        		try {
	        			name[i] = (String) arr.getJSONObject(i).get("name").toString();
	        		}catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			//e.printStackTrace();
	        		}
	        		try {
	        			url_title[i] = (String) arr.getJSONObject(i).get("url_title").toString();
	        		}catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			//e.printStackTrace();
	        		}
	        		try {
	        			order_nr[i] = (String) arr.getJSONObject(i).get("order_nr").toString();
	        		}catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			//e.printStackTrace();
	        		}
	        		try {
	        			active[i] = (String) arr.getJSONObject(i).get("active").toString();
	        		}catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			//e.printStackTrace();
	        		}
	        		try {
	        			add_time[i] = (String) arr.getJSONObject(i).get("add_time").toString();
	        		}catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			//e.printStackTrace();
	        		}
	        		try {
	        			update_time[i] = (String) arr.getJSONObject(i).get("update_time").toString();
	        		}catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			//e.printStackTrace();
	        		}
	        		try {
	        			selected[i] = (String) arr.getJSONObject(i).get("selected").toString();
	        		}catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			//e.printStackTrace();
	        		}
	        		
	        		pipeMap.put(id[i], name[i]);
	        	
	        	}catch(Exception e)
	        	{
	        		//e.printStackTrace();
	        	}
	        }
		}
		public String getId(int index) {
			return id[index];
		}
		public String getName(int index) {
			return name[index];
		}
		public String getUrl_title(int index) {
			return url_title[index];
		}
		public String getOrder_nr(int index) {
			return order_nr[index];
		}
		public String getActive(int index) {
			return active[index];
		}
		public String getAdd_time(int index) {
			return add_time[index];
		}
		public String getUpdate_time(int index) {
			return update_time[index];
		}
		public String getSelected(int index) {
			return selected[index];
		}
		public int getPipeSize() {
			return pipeSize;
		}
		public Map getPipeMap() {
			return pipeMap;
		}
		
}
