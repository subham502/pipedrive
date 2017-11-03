package com.hof.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

public class Stages {
		
		public final static Logger logger = Logger.getLogger(Stages.class.getName());
		
		String[] id = null;
		String[] order_nr = null;
		String[] name = null;
		String[] active_flag = null;
		String[] deal_probability = null;
		String[] pipeline_id = null;
		String[] rotten_flag = null;
		String[] rotten_days = null;
		String[] add_time = null;
		String[] update_time = null;
		String[] pipeline_name = null;
	    int stageSize;
	    
	    Map stageMap = new HashMap();
	    //Format data for Lead   
		public void getStage(JSONArray arr)
		{
			
		   // JSONArray arr = obj.getJSONArray("data");		        
		    stageSize = arr.length();
	        
		    id = new String[stageSize];
		    order_nr = new String[stageSize];
		    name = new String[stageSize];
		    active_flag = new String[stageSize];
		    deal_probability = new String[stageSize];
		    pipeline_id = new String[stageSize];
		    rotten_flag = new String[stageSize];
		    rotten_days = new String[stageSize];
		    add_time = new String[stageSize];
		    update_time = new String[stageSize];
		    pipeline_name = new String[stageSize];
	        
	        for(int i=0;i<stageSize;i++)
	        {
	        	try{
	        		try{
	        			id[i] = (String) arr.getJSONObject(i).get("id").toString();
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
	        			name[i] = (String) arr.getJSONObject(i).get("name").toString();
	        		}catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			//e.printStackTrace();
	        		}
	        		
	        		try {
	        			active_flag[i] = (String) arr.getJSONObject(i).get("active_flag").toString();
	        		}catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			//e.printStackTrace();
	        		}
	        		try {
	        			deal_probability[i] = (String) arr.getJSONObject(i).get("deal_probability").toString();
	        		}catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			//e.printStackTrace();
	        		}
	        		try {
	        			pipeline_id[i] = (String) arr.getJSONObject(i).get("pipeline_id").toString();
	        		}catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			//e.printStackTrace();
	        		}
	        		try {
	        			rotten_flag[i] = (String) arr.getJSONObject(i).get("rotten_flag").toString();
	        		}catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			//e.printStackTrace();
	        		}
	        		try {
	        			rotten_days[i] = (String) arr.getJSONObject(i).get("rotten_days").toString();
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
	        			pipeline_name[i] = (String) arr.getJSONObject(i).get("pipeline_name").toString();
	        		}catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			//e.printStackTrace();
	        		}
	        		
	        		stageMap.put(id[i], name[i]);
	        	
	        	}catch(Exception e)
	        	{
	        		//e.printStackTrace();
	        	}
	        }
		}
		public String getId(int index) {
			return id[index];
		}
		public String getOrder_nr(int index) {
			return order_nr[index];
		}
		public String getName(int index) {
			return name[index];
		}
		public String getActive_flag(int index) {
			return active_flag[index];
		}
		public String getDeal_probability(int index) {
			return deal_probability[index];
		}
		public String getPipeline_id(int index) {
			return pipeline_id[index];
		}
		public String getRotten_flag(int index) {
			return rotten_flag[index];
		}
		public String getRotten_days(int index) {
			return rotten_days[index];
		}
		public String getAdd_time(int index) {
			return add_time[index];
		}
		public String getUpdate_time(int index) {
			return update_time[index];
		}
		public String getPipeline_name(int index) {
			return pipeline_name[index];
		}
		public int getStageSize() {
			return stageSize;
		}
		public Map getStageMap() {
			return stageMap;
		}
}
