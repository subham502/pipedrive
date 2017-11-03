package com.hof.PipedriveConnector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hof.mi.thirdparty.interfaces.AbstractDataSet;
import com.hof.mi.thirdparty.interfaces.AbstractDataSource;
import com.hof.mi.thirdparty.interfaces.ColumnMetaData;
import com.hof.mi.thirdparty.interfaces.DataType;
import com.hof.mi.thirdparty.interfaces.FilterData;
import com.hof.mi.thirdparty.interfaces.FilterMetaData;
import com.hof.mi.thirdparty.interfaces.ScheduleDefinition;
import com.hof.mi.thirdparty.interfaces.ScheduleDefinition.FrequencyTypeCode;
import com.hof.mi.thirdparty.interfaces.ThirdPartyException;
import com.hof.pool.JDBCMetaData;
import com.hof.util.Activity;
import com.hof.util.DateFields;
import com.hof.util.DealFields;
import com.hof.util.PipeConnection;
import com.hof.util.Pipelines;
import com.hof.util.Stages;

public class PipedriveDataSource extends AbstractDataSource 
{
	final static Logger LOGGER = Logger.getLogger(PipedriveDataSource.class.getName());
	String fname = null;
	Map<String , String> dateFields = new HashMap<String, String>(){{
	       put("Get Date","DATE"); put("Hour","INTEGER");put("Month Number","INTEGER");
	       put("Month Name" , "TEXT");put("Day of Week Name" , "TEXT" );put("Day of Week Name Number" ,  "INTEGER" );
	       put("Week Start Date" , "DATE" );put("Month Start Date" , "DATE" );put("Quarter Start Date" , "DATE" );
	       put("Year Start Date"  , "DATE" );put("Quarter" , "INTEGER" );put("Year" , "INTEGER" );put("Day Of Month" , "INTEGER");}};
	public String getDataSourceName() {
		
		return PipedriveDataZoom.getText("Pipedrive Data Source", "mi.text.pipedrive.datasource.name");
		
	}
	
	//final static Logger LOGGER = Logger.getLogger(PipedriveSourceMetaData.class.getName());
	
	public static String resp = null;
	
	public ScheduleDefinition getScheduleDefinition() { 
		/*In this function define the frequency with which Yellowfin should execute the autorun function*/
		return new ScheduleDefinition(FrequencyTypeCode.DAILY, null, 5); 
	};
	
	
	public List<AbstractDataSet> getDataSets() 
	{

		List<AbstractDataSet> p = new ArrayList<AbstractDataSet>();		
		
		p.add(DealFields());
		//p.add(ActivityFields());
		return p;
		
	}
	Map<String,String> dataTypeF = new HashMap<String,String>();
	private AbstractDataSet DealFields()
	{

		AbstractDataSet DealFieldsDataset = new AbstractDataSet() 
		{	
			JSONArray dealFld = new JSONArray();
			JSONArray dealObj = new JSONArray();
			DealFields df = new DealFields();
			
			@Override
			public List<ColumnMetaData> getColumns() 
			{
				// TODO Auto-generated method stub
				List<ColumnMetaData> cm = new ArrayList<ColumnMetaData>();

				byte[] ids = loadBlob("DEALFIELDS");					
				dealFld = new JSONArray(new String(ids));
				df.formatData(dealFld);
				
				byte[] ids1 = loadBlob("DEALS");
				dealObj = new JSONArray(new String(ids1));
				df.formatDealsData(dealObj);					
				
				//System.out.println("====================="+dealObj.toString());
				
				try {
					for(int dealF = 0; dealF < df.getSizeX();dealF++)
					{
						if(df.getFieldType1(dealF).toString().equals("DATE"))
						{
							for (Map.Entry<String, String> entry1 : dateFields.entrySet())
							{
								cm.add(new ColumnMetaData(df.getKey1(dealF).toString()+" "+entry1.getKey(),DataType.valueOf(entry1.getValue())));
								dataTypeF.put(df.getKey1(dealF).toString()+" "+entry1.getKey(),"DATE");
							}						
						}
						else
						{
							cm.add(new ColumnMetaData(df.getKey1(dealF).toString(),DataType.valueOf(df.getFieldType1(dealF).toString())));
							dataTypeF.put(df.getKey1(dealF).toString(),df.getFieldType1(dealF).toString());
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				/*try {
					for (Map.Entry<String, String> entry2 :dataTypeF.entrySet())
					{
						System.out.println(entry2.getKey() + "/" + entry2.getValue());
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				return cm;
			}
			SimpleDateFormat x1 = null;
			Calendar cal1 = null;
			java.sql.Timestamp sq1 = null;
			DateFields df1 = null;
			@Override
			public Object[][] execute(List<ColumnMetaData> arg0, List<FilterData> arg1)
			{		
				
				//System.out.println("=======================++++++++++++++++++++++++++++++++++=====");				
				if(PipedriveDataSource.this.loadBlob("LASTRUN") == null)
					throw new ThirdPartyException(PipedriveDataZoom.getText("The database is not yet populated! Please try in 10 minutes", "mi.text.pipe.data.exception"));
				else
				{
					byte[] ids = loadBlob("DEALS");
					dealObj = new JSONArray(new String(ids));
					df.formatDealsData(dealObj);
				}
				//System.out.println("====================="+dealObj1.toString());
				//System.out.println("=======================++++++++++++++++++++++++++++++++++=====");
				//System.out.println("=======================++++++++++++++++++++++++++++++++++=====");
				//System.out.println(df.getMap1().size());
				
				Map <String,List<String>> dataArr = df.getMap1();
				/*SimpleDateFormat dateFormat = null;
				Calendar cal = null;
				DateFields dateConversion = null;*/
				/*try {
					for (Map.Entry<String, List<String>> entry :dataArr.entrySet())
					{
						System.out.println(entry.getKey() + "/" + entry.getValue());
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				
				Object[][]data=new Object[df.getSize1()][arg0.size()];
				int i, j;
				String amt = null;
				String colName = null;
				String colNameDate = null;
				String colType = null;
				List<String>  data2 = null;
				//List<Object>  data2 = null;
				for(i=0; i<arg0.size(); i++)
				{
					//System.out.println("++++++++++++++++++inside execute");
					colName = arg0.get(i).getColumnName();
					colNameDate = arg0.get(i).getColumnName();
					//System.out.println("===========colname============"+colName);
					colType = dataTypeF.get(colName);
					//System.out.println("======================="+colType);
					if(colName.contains("Get Date"))
						colNameDate = colNameDate.replace(" Get Date","");
					else if(colName.contains("Hour"))
						colNameDate = colNameDate.replace(" Hour","");
					else if(colName.contains("Month Number"))
						colNameDate = colNameDate.replace(" Month Number","");
					else if(colName.contains("Month Name"))
						colNameDate = colNameDate.replace(" Month Name","");
					else if(colName.contains("Day of Week Name"))
						colNameDate = colNameDate.replace(" Day of Week Name","");
					else if(colName.contains("Day of Week Name Number"))
						colNameDate = colNameDate.replace(" Day of Week Name Number","");
					else if(colName.contains("Week Start Date"))
						colNameDate = colNameDate.replace(" Week Start Date","");
					else if(colName.contains("Month Start Date"))
						colNameDate = colNameDate.replace(" Month Start Date","");
					else if(colName.contains("Quarter Start Date"))
						colNameDate = colNameDate.replace(" Quarter Start Date","");
					else if(colName.contains("Year Start Date"))
						colNameDate = colNameDate.replace(" Year Start Date","");
					else if(colName.contains("Quarter"))
						colNameDate = colNameDate.replace(" Quarter","");
					else if(colName.contains("Year"))
						colNameDate = colNameDate.replace(" Year","");
					else if(colName.contains("Day Of Month"))
						colNameDate = colNameDate.replace(" Day Of Month","");
					
						data2 = dataArr.get(colNameDate);
					for(j=0;j<df.getSize1(); j++)
					{	
					/*	if(colName.equals("id"))
								System.out.println("==========dsreg45411111554reaf=============data2"+colNameDate+"==="+data2.toString());*/
						try {
							if(colType.equals("NUMERIC"))
							{								
								/*System.out.println("================================="
										+String.valueOf(data2.get(j))
										+"==="+data2.toString());*/
								try {								
									if(String.valueOf(data2.get(j)).length()>0)
										data[j][i] = Double.parseDouble(String.valueOf(data2.get(j)));
									else
										data[j][i] = 0;
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									data[j][i] = 0;
								}
							}
							else if(colType.equals("DATE"))
							{
								//System.out.println("==========stage iddd=========date===="+colNameDate+"===");
								//System.out.println("============================="+data2.get(j));
								//System.out.println("============================="+dataTypeF.get(colName));
								
								cal1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
								x1 = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
								try {
									cal1.setTime(x1.parse(String.valueOf(data2.get(j))));
								} catch (ParseException e) {
									//e.printStackTrace();
									//data[j][i] = "UNKNOWN";
								}
								sq1 = new java.sql.Timestamp(cal1.getTimeInMillis());
								df1 = new DateFields(sq1);
								
								if(colName.contains("Get Date"))							
									data[j][i] = df1.getDate();
								else if(colName.contains("Hour"))
									data[j][i] = df1.getHour();
								else if(colName.contains("Month Number"))
									data[j][i] = df1.getMonthNumber();
								else if(colName.contains("Month Name"))
									data[j][i] = df1.getMonthName();
								else if(colName.contains("Day of Week Name"))
									data[j][i] = df1.getWeekdayName();
								else if(colName.contains("Day of Week Name Number"))
									data[j][i] = df1.getWeekdayNumber();
								else if(colName.contains("Week Start Date"))
									data[j][i] = df1.getWeekStartDate();
								else if(colName.contains("Month Start Date"))
									data[j][i] = df1.getMonthStartDate();
								else if(colName.contains("Quarter Start Date"))
									data[j][i] = df1.getQuarterStartDate();
								else if(colName.contains("Year Start Date"))
									data[j][i] = df1.getYearStartDate();
								else if(colName.contains("Quarter"))
									data[j][i] = df1.getQuarter();
								else if(colName.contains("Year"))
									data[j][i] = df1.getYear();
								else if(colName.contains("Day Of Month"))
									data[j][i] = df1.getDayOfMonth();
							}
							else
							{
								try {
									try {
										amt = String.valueOf(data2.get(j));
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										amt = "";
									}									
									if(amt.length()>0)
										data[j][i] = amt;
									else
										data[j][i] = "UNKNOWN";
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									data[j][i] = "UNKNOWN";
								}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return data;
			}

			@Override
			public boolean getAllowsAggregateColumns() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean getAllowsDuplicateColumns() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getDataSetName() {
				// TODO Auto-generated method stub
				return "Deals";
			}

			@Override
			public List<FilterMetaData> getFilters() {
				// TODO Auto-generated method stub
				return null;
			}

		};
		return DealFieldsDataset;
	}
	
	public JDBCMetaData getDataSourceMetaData() {
		return new PipedriveSourceMetaData();
	}


	public boolean authenticate() 
	{
		return true;
	}
	
	public void disconnect(){
		
	}
	public void getAutorunData()
	{
		//System.out.println("===========get auto run============");
		String pin=(String)getAttribute("KEY");
		String maxRec = "1";
				
		PipeConnection conn = new PipeConnection();
		JSONArray respdealF = new JSONArray();
		JSONArray respdeal = new JSONArray();
		JSONArray respPipe = new JSONArray();
		JSONArray respStage = new JSONArray();
		JSONArray dealsArr = new JSONArray();
		
		Pipelines pipe = new Pipelines();
		Stages stage = new Stages();
		
		respdealF = conn.getFields(pin,100,"dealFields");		
		saveBlob("DEALFIELDS",respdealF.toString().getBytes());					
		
		//pipelines data
		respPipe = conn.getFields(pin,Integer.parseInt(maxRec),"pipelines");		
		saveBlob("PIPELINES",respPipe.toString().getBytes());
		
		//stages data
		respStage = conn.getFields(pin,Integer.parseInt(maxRec),"stages");		
		saveBlob("STAGES",respStage.toString().getBytes());
		
		if(loadBlob("PIPELINES") == null)
			pipe.getPipe(respPipe);
		else{
			byte[] ids = loadBlob("PIPELINES");
			JSONArray pipeArr = new JSONArray(new String(ids));
			pipe.getPipe(pipeArr);
		}
		
		if(loadBlob("STAGES") == null)
			stage.getStage(respStage);
		else{
			byte[] idstage = loadBlob("STAGES");
			JSONArray stageArr = new JSONArray(new String(idstage));
			stage.getStage(stageArr);
		}
		
		//Deals data
		respdeal = conn.getFields(pin,Integer.parseInt(maxRec),"deals");		
		saveBlob("DEALS",respdeal.toString().getBytes());
		
		if(loadBlob("DEALS") == null)
			dealsArr = respdeal;
		else{
			byte[] idDEALS = loadBlob("DEALS");
			dealsArr = new JSONArray(new String(idDEALS));
		}
		
		String stage_id = "";
		String pipe_id = "";
		
		for(int i=0;i<dealsArr.length();i++)
		{			
			pipe_id = (String) dealsArr.getJSONObject(i).get("pipeline_id").toString();
			stage_id = (String) dealsArr.getJSONObject(i).get("stage_id").toString();
						
			JSONObject resultObj1 = dealsArr.getJSONObject(i);
			resultObj1.put("pipeline_name", pipe.getPipeMap().get(pipe_id));
			resultObj1.put("stage_name", stage.getStageMap().get(stage_id));
		}
		
		saveBlob("DEALS",dealsArr.toString().getBytes());
		//System.out.println("===================="+dealsArr.toString());		
	}

	public Map<String,Object> testConnection()
	{

		/*In this function you should define the actions that the connector should perform
		 * if the user clicks the 'Test Connection' button. 
		 * If you want to tell Yellowfin that the connection was not successful then the 
		 * Map that you return should contain a value with key "ERROR"*/
		Map<String,Object> p = new HashMap<String, Object>();
		
		try {
			String pin=(String)getAttribute("KEY");
			String maxRec = (String)getAttribute("MREC");
			try {
				if(maxRec.length() < 1)
					maxRec= "5";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				maxRec = "5";
			}
			Activity et = new Activity();
			        
			if(!pin.isEmpty())
			{
				resp = et.getResp(pin);

				if(resp.contains("error"))
				{
						p.put("ERROR", PipedriveDataZoom.getText("Invalid Token ID! Please Provide New Authentication Token","mi.text.pipedrive.test.connection.error1"));
				}
				else
				{
					getAutorunData();
					p.put("SUCCESS",PipedriveDataZoom.getText("CONNECTION SUCCESSFUL","mi.text.quick.conection.success"));
				}
			}
			else
			{	
				/*//Deals data
				resp1 = conn.getFields(pin,Integer.parseInt(maxRec),"deals");		
				saveBlob("DEALS",resp.toString().getBytes());*/
				
				p.put("ERROR", PipedriveDataZoom.getText("Provide Correct Authentication Key","mi.text.pipedrive.test.connection.error2"));
			}
		}catch(NullPointerException e)
		{
			e.printStackTrace();
			p.put("ERROR", PipedriveDataZoom.getText("Provide Correct Authentication Key","mi.text.pipedrive.test.connection.error2"));
		}
		return p;
	}	
	
	public boolean autoRun()
	{
		//System.out.println("======================inside auto run==============");
		Pipelines pipe = new Pipelines();
		Stages stage = new Stages();
		//System.out.println("======================complete  pipe and stage create==============");
		String key = (String)getAttribute("KEY");
		String maxR = (String)getAttribute("MREC");
		PipeConnection conn = new PipeConnection();
		JSONArray resp = new JSONArray();
		
		//Dealfields data
		resp = conn.getFields(key,Integer.parseInt(maxR),"dealFields");		
		saveBlob("DEALFIELDS",resp.toString().getBytes());
		
		
		//pipelines data
		resp = conn.getFields(key,Integer.parseInt(maxR),"pipelines");		
		saveBlob("PIPELINES",resp.toString().getBytes());
		
		//stages data
		resp = conn.getFields(key,Integer.parseInt(maxR),"stages");		
		saveBlob("STAGES",resp.toString().getBytes());
		
		
		if(loadBlob("PIPELINES") == null)
			pipe.getPipe(resp);
		else{
			byte[] ids = loadBlob("PIPELINES");
			JSONArray pipeArr = new JSONArray(new String(ids));
			pipe.getPipe(pipeArr);
		}
		
		if(loadBlob("STAGES") == null)
			stage.getStage(resp);
		else{
			byte[] idstage = loadBlob("STAGES");
			JSONArray stageArr = new JSONArray(new String(idstage));
			stage.getStage(stageArr);
		}
				
		//Deals data
		JSONArray dealsArr = new JSONArray();
		
		dealsArr = conn.getFields(key,Integer.parseInt(maxR),"deals");		
		saveBlob("DEALS",dealsArr.toString().getBytes());
		
		if(loadBlob("DEALS") == null);
		else{
			byte[] idDEALS = loadBlob("DEALS");
			dealsArr = new JSONArray(new String(idDEALS));
		}
		
		String stage_id = "";
		String pipe_id = "";
		
		for(int i=0;i<dealsArr.length();i++)
		{			
			pipe_id = (String) dealsArr.getJSONObject(i).get("pipeline_id").toString();
			stage_id = (String) dealsArr.getJSONObject(i).get("stage_id").toString();
						
			JSONObject resultObj1 = dealsArr.getJSONObject(i);
			resultObj1.put("pipeline_name", pipe.getPipeMap().get(pipe_id));
			resultObj1.put("stage_name", stage.getStageMap().get(stage_id));
		}		
		//System.out.println("===================="+dealsArr.toString());
	
		saveBlob("DEALS",dealsArr.toString().getBytes());
		
		saveBlob("LASTRUN", new Date(System.currentTimeMillis()).toLocaleString().getBytes());
		return true;
	}

}