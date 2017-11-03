package com.hof.util;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Activity {
	
	final static Logger LOGGER = Logger.getLogger(Activity.class.getName());
	
	Object []id;	
	Object []company_id;	
	Object []user_id;	
	Object []done;	
	Object []type;	
	Object []reference_type;	
	Object []reference_id;	
	Object []due_date;
	
	Object []date;
	Object []timeStamp;
	Object []monthStartDate;
	Object []quarterStartDate;
	Object []weekStartDate;
	Object []yearStartDate;
	Object []dayOfWeek;
	Object []hour;
	Object []monthNumber;
	Object []quarter;
	Object []year;
	Object []dayOfMonth;
	Object []dayOfWeekName;
	Object []monthName;
	
	Object []due_time;	
	Object []duration;	
	Object []add_time;
	
	Object []date1;
	Object []timeStamp1;
	Object []monthStartDate1;
	Object []quarterStartDate1;
	Object []weekStartDate1;
	Object []yearStartDate1;
	Object []dayOfWeek1;
	Object []hour1;
	Object []monthNumber1;
	Object []quarter1;
	Object []year1;
	Object []dayOfMonth1;
	Object []dayOfWeekName1;
	Object []monthName1;
	
	Object []marked_as_done_time;	
	Object []subject;	
	Object [] deal_id;	
	Object []org_id;	
	Object []person_id;	
	Object []active_flag;	
	Object []update_time;
	
	Object []date2;
	Object []timeStamp2;
	Object []monthStartDate2;
	Object []quarterStartDate2;
	Object []weekStartDate2;
	Object []yearStartDate2;
	Object []dayOfWeek2;
	Object []hour2;
	Object []monthNumber2;
	Object []quarter2;
	Object []year2;
	Object []dayOfMonth2;
	Object []dayOfWeekName2;
	Object []monthName2;
	
	Object []gcal_event_id;	
	Object []google_calendar_id;	
	Object []google_calendar_etag;	
	Object []person_name;	
	Object []org_name;	
	Object []note;	
	Object []deal_title;	
	Object []assigned_to_user_id;	
	Object []created_by_user_id;	
	Object []owner_name;	
	Object []person_dropbox_bcc;	
	Object []deal_dropbox_bcc;	
	
	int size;
	
	public JSONArray getFields(String fname, String token, int maxRecords)
	{
		/*String result = null;
		try {
			URL url = new URL("https://api.pipedrive.com/v1/activities:("+fname+")?start=0&limit=500&api_token="+token);
			System.out.println(fname);
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

		return result;*/
		JSONArray resultArr = new JSONArray();
		String result = null;
		int count =1;
		int a,b;
		if(maxRecords >500){
			a = (int) Math.ceil(maxRecords/500.0) + 1;
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
				URL url = new URL("https://api.pipedrive.com/v1/activities:("+fname+")?start="+start+"&limit="+b+"&api_token="+token);
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
	
	
	public String getResp(String token)
	{
		String result = null;
		try {
			URL url = new URL("https://api.pipedrive.com/v1/activities?api_token="+token);
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
	
	
	public void formatData(JSONArray resp)
	{
		//LOGGER.info("---ACTIVITY RESPONSE-----" + resp);
		/*JSONObject obj = new JSONObject(resp);
		
		JSONArray arr = obj.getJSONArray("data");*/
		
		this.size = resp.length();
		//System.out.println(this.size);
		this.id = new Object[size];
		this.company_id = new Object[size];		
		this.user_id = new Object[size];		
		this.done = new Object[size];		
		this.type = new Object[size];		
		this.reference_type = new Object[size];		
		this.reference_id = new Object[size];		
		this.due_date = new Object[size];		
		this.due_time = new Object[size];		
		this.duration  = new Object[size];		
		this.add_time = new Object[size];		
		this.marked_as_done_time = new Object[size];		
		this.subject = new Object[size];		
		this.deal_id = new Object[size];		
		this.org_id = new Object[size];		
		this.person_id = new Object[size];		
		this.active_flag = new Object[size];		
		this.update_time = new Object[size];		
		this.gcal_event_id = new Object[size];		
		this.google_calendar_id = new Object[size];		
		this.google_calendar_etag = new Object[size];		
		this.person_name = new Object[size];		
		this.org_name = new Object[size];		
		this.note = new Object[size];		
		this.deal_title = new Object[size];		
		this.assigned_to_user_id = new Object[size];		
		this.created_by_user_id = new Object[size];		
		this.owner_name = new Object[size];		
		this.person_dropbox_bcc = new Object[size];		
		this.deal_dropbox_bcc = new Object[size];
		
		List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
		List<java.sql.Timestamp> timeStampList = new ArrayList<java.sql.Timestamp>();
		List<java.sql.Date> monthStartDateList = new ArrayList<java.sql.Date>();
		List<java.sql.Date> quarterStartDateList = new ArrayList<java.sql.Date>();
		List<java.sql.Date> weekStartDateList = new ArrayList<java.sql.Date>();
		List<java.sql.Date> yearStartDateList = new ArrayList<java.sql.Date>();
		List<Integer> dayOfWeekList = new ArrayList<Integer>(); 
		List<Integer> hourList = new ArrayList<Integer>();
		List<Integer> monthNumberList = new ArrayList<Integer>();
		List<Integer> quarterList = new ArrayList<Integer>();
		List<Integer> yearList = new ArrayList<Integer>();
		List<Integer> dayOfMonthList = new ArrayList<Integer>();
		List<String> dayOfWeekNameList = new ArrayList<String>();
		List<String> monthNameList = new ArrayList<String>();
		
		
		List<java.sql.Date> dateList1 = new ArrayList<java.sql.Date>();
		List<java.sql.Timestamp> timeStampList1 = new ArrayList<java.sql.Timestamp>();
		List<java.sql.Date> monthStartDateList1 = new ArrayList<java.sql.Date>();
		List<java.sql.Date> quarterStartDateList1= new ArrayList<java.sql.Date>();
		List<java.sql.Date> weekStartDateList1 = new ArrayList<java.sql.Date>();
		List<java.sql.Date> yearStartDateList1 = new ArrayList<java.sql.Date>();
		List<Integer> dayOfWeekList1 = new ArrayList<Integer>(); 
		List<Integer> hourList1 = new ArrayList<Integer>();
		List<Integer> monthNumberList1 = new ArrayList<Integer>();
		List<Integer> quarterList1 = new ArrayList<Integer>();
		List<Integer> yearList1 = new ArrayList<Integer>();
		List<Integer> dayOfMonthList1 = new ArrayList<Integer>();
		List<String> dayOfWeekNameList1 = new ArrayList<String>();
		List<String> monthNameList1 = new ArrayList<String>();
		
		List<java.sql.Date> dateList2 = new ArrayList<java.sql.Date>();
		List<java.sql.Timestamp> timeStampList2 = new ArrayList<java.sql.Timestamp>();
		List<java.sql.Date> monthStartDateList2 = new ArrayList<java.sql.Date>();
		List<java.sql.Date> quarterStartDateList2 = new ArrayList<java.sql.Date>();
		List<java.sql.Date> weekStartDateList2 = new ArrayList<java.sql.Date>();
		List<java.sql.Date> yearStartDateList2 = new ArrayList<java.sql.Date>();
		List<Integer> dayOfWeekList2 = new ArrayList<Integer>(); 
		List<Integer> hourList2 = new ArrayList<Integer>();
		List<Integer> monthNumberList2 = new ArrayList<Integer>();
		List<Integer> quarterList2 = new ArrayList<Integer>();
		List<Integer> yearList2 = new ArrayList<Integer>();
		List<Integer> dayOfMonthList2 = new ArrayList<Integer>();
		List<String> dayOfWeekNameList2 = new ArrayList<String>();
		List<String> monthNameList2 = new ArrayList<String>();

	
		for(int i=0; i<size; i++)
		{
			
			JSONObject x = resp.getJSONObject(i);
			try {
				this.id[i] = x.get("id");
			} catch (JSONException e6) {
				// TODO Auto-generated catch block
			}
			try {
				this.company_id[i] = x.get("company_id");
			} catch (JSONException e5) {
				// TODO Auto-generated catch block
			}		
			try {
				this.user_id[i] = x.get("user_id");
			} catch (JSONException e4) {
				// TODO Auto-generated catch block
			}		
			try {
				this.done[i] = x.get("done");
			} catch (JSONException e3) {
				// TODO Auto-generated catch block
			}		
			try {
				this.type[i] = x.get("type");
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
			}		
			try {
				this.reference_type[i] = x.get("reference_type");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
			}		
			try {
				this.reference_id[i] = x.get("reference_id");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}		
			try {
				String dt = x.getString("due_date");
				/*try {
					this.due_date[i] = x.get("due_date");
				} catch (JSONException e3) {
					// TODO Auto-generated catch block
				}*/
				Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					cal.setTime(dateformat.parse(dt));
				} catch (ParseException e) {
					
				}
				java.sql.Timestamp sq = new java.sql.Timestamp(cal.getTimeInMillis());
				DateFields df = new DateFields(sq);
				timeStampList.add(df.getTimestamp());
				monthStartDateList.add(df.getMonthStartDate());
				quarterStartDateList.add(df.getQuarterStartDate());
				weekStartDateList.add(df.getWeekStartDate());
				yearStartDateList.add(df.getYearStartDate());
				dayOfWeekList.add(df.getWeekdayNumber());
				hourList.add(df.getHour());
				monthNumberList.add(df.getMonthNumber());
				quarterList.add(df.getQuarter());
				yearList.add(df.getYear());
				dayOfMonthList.add(df.getDayOfMonth());
				dayOfWeekNameList.add(df.getWeekdayName());
				monthNameList.add(df.getMonthName());
				dateList.add(df.getDate());
			} catch (JSONException e3) {
				// TODO Auto-generated catch block
			}
			try {
				this.due_time[i] = x.get("due_time");
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
			}		
			try {
				this.duration[i]  = x.get("duration");
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
			}
			
			//this.add_time[i] = x.getString("add_time");	
			try {
				String dt1 = x.getString("add_time");
				//System.out.println(dt1);
				Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
				SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					cal1.setTime(dateformat1.parse(dt1));
				} catch (ParseException e) {
					
				}
				java.sql.Timestamp sq1 = new java.sql.Timestamp(cal1.getTimeInMillis());
				DateFields df1 = new DateFields(sq1);
				timeStampList1.add(df1.getTimestamp());
				monthStartDateList1.add(df1.getMonthStartDate());
				quarterStartDateList1.add(df1.getQuarterStartDate());
				weekStartDateList1.add(df1.getWeekStartDate());
				yearStartDateList1.add(df1.getYearStartDate());
				dayOfWeekList1.add(df1.getWeekdayNumber());
				hourList1.add(df1.getHour());
				monthNumberList1.add(df1.getMonthNumber());
				quarterList1.add(df1.getQuarter());
				yearList1.add(df1.getYear());
				dayOfMonthList1.add(df1.getDayOfMonth());
				dayOfWeekNameList1.add(df1.getWeekdayName());
				monthNameList1.add(df1.getMonthName());
				dateList1.add(df1.getDate());
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
			}
			try {
				this.marked_as_done_time[i] = x.get("marked_as_done_time");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
			}		
			try {
				this.subject[i] = x.get("subject");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
			}		
			try {
				this.deal_id[i] = x.get("deal_id");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
			}		
			try {
				this.org_id[i] = x.get("org_id");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
			}		
			try {
				this.person_id[i] = x.get("person_id");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
			}		
			try {
				this.active_flag[i] = x.get("active_flag");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
			}		
			//this.update_time[i] = x.getString("update_time");
			try {
				String dt2 = x.getString("update_time");

				Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
				SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					cal2.setTime(dateformat2.parse(dt2));
				} catch (ParseException e) {
					
				}
				java.sql.Timestamp sq2 = new java.sql.Timestamp(cal2.getTimeInMillis());
				DateFields df2 = new DateFields(sq2);
				timeStampList2.add(df2.getTimestamp());
				monthStartDateList2.add(df2.getMonthStartDate());
				quarterStartDateList2.add(df2.getQuarterStartDate());
				weekStartDateList2.add(df2.getWeekStartDate());
				yearStartDateList2.add(df2.getYearStartDate());
				dayOfWeekList2.add(df2.getWeekdayNumber());
				hourList2.add(df2.getHour());
				monthNumberList2.add(df2.getMonthNumber());
				quarterList2.add(df2.getQuarter());
				yearList2.add(df2.getYear());
				dayOfMonthList2.add(df2.getDayOfMonth());
				dayOfWeekNameList2.add(df2.getWeekdayName());
				monthNameList2.add(df2.getMonthName());
				dateList2.add(df2.getDate());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
			}
			
			try {
				this.gcal_event_id[i] = x.get("gcal_event_id");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}		
			try {
				this.google_calendar_id[i] = x.get("google_calendar_id");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}		
			try {
				this.google_calendar_etag[i] = x.get("google_calendar_etag");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}		
			try {
				this.person_name[i] = x.get("person_name");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}		
			try {
				this.org_name[i] = x.get("org_name");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}		
			try {
				this.note[i] = x.get("note");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}		
			try {
				this.deal_title[i] = x.get("deal_title");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}		
			try {
				this.assigned_to_user_id[i] = x.get("assigned_to_user_id");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}		
			try {
				this.created_by_user_id[i] = x.get("created_by_user_id");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}		
			try {
				this.owner_name[i] = x.get("owner_name");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}		
			try {
				this.person_dropbox_bcc[i] = x.get("person_dropbox_bcc");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}				
			try {
				this.deal_dropbox_bcc[i] = x.get("deal_dropbox_bcc");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}
		}

		date=dateList.toArray();
		timeStamp = timeStampList.toArray();
		monthStartDate = monthStartDateList.toArray();
		quarterStartDate = quarterStartDateList.toArray();
		weekStartDate = weekStartDateList.toArray();
		yearStartDate = yearStartDateList.toArray();
		dayOfWeek = dayOfWeekList.toArray();
		hour = hourList.toArray();
		monthNumber = monthNumberList.toArray();
		quarter = quarterList.toArray();
		year = yearList.toArray();
		dayOfMonth = dayOfMonthList.toArray();
		dayOfWeekName = dayOfWeekNameList.toArray();
		monthName = monthNameList.toArray();
		
		date1=dateList1.toArray();
		timeStamp1 = timeStampList1.toArray();
		monthStartDate1 = monthStartDateList1.toArray();
		quarterStartDate1 = quarterStartDateList1.toArray();
		weekStartDate1 = weekStartDateList1.toArray();
		yearStartDate1 = yearStartDateList1.toArray();
		dayOfWeek1 = dayOfWeekList1.toArray();
		hour1 = hourList1.toArray();
		monthNumber1 = monthNumberList1.toArray();
		quarter1 = quarterList1.toArray();
		year1 = yearList1.toArray();
		dayOfMonth1 = dayOfMonthList1.toArray();
		dayOfWeekName1 = dayOfWeekNameList1.toArray();
		monthName1 = monthNameList1.toArray();
		
		date2=dateList2.toArray();
		timeStamp2 = timeStampList2.toArray();
		monthStartDate2 = monthStartDateList2.toArray();
		quarterStartDate2 = quarterStartDateList2.toArray();
		weekStartDate2 = weekStartDateList2.toArray();
		yearStartDate2 = yearStartDateList2.toArray();
		dayOfWeek2 = dayOfWeekList2.toArray();
		hour2 = hourList2.toArray();
		monthNumber2 = monthNumberList2.toArray();
		quarter2 = quarterList2.toArray();
		year2 = yearList2.toArray();
		dayOfMonth2 = dayOfMonthList2.toArray();
		dayOfWeekName2 = dayOfWeekNameList2.toArray();
		monthName2 = monthNameList2.toArray();
		
	}
	
	public int getSize(){
		
		return this.size;
	}
	
	
	public Object getId(int index){
		
		return this.id[index];
		
	}
	
	public Object getCompanyId(int index){
		
		return this.company_id[index];
		
	}

	public Object getUserId(int index){
	
		return this.user_id[index];
		
	}
	
	public Object getDone(int index){
		
		return this.done[index];
		
	}
	
	public Object getType(int index){
		
		return this.type[index];
		
	}
	
	public Object getReference_Id(int index){
		
		return this.reference_id[index];
		
	}
	

	public Object getReferenceType(int index){
		
		return this.reference_type[index];
		
	}
	
	public Object getDueTime(int index){
		
		return this.due_time[index];
		
	}
	

	public Object getDuration(int index){
		
		return this.duration[index];
		
	}	
	
	public Object getMarkedAsDoneTime(int index){
		
		return this.marked_as_done_time[index];
		
	}
	

	public Object getSubject(int index){
		
		return this.subject[index];
		
	}
	

	public Object getDealId(int index){
		
		return this.deal_id[index];
		
	}

	public Object getOrdId(int index){
		
		return this.org_id[index];
		
	}
	

	public Object getPersonId(int index){
		
		return this.person_id[index];
		
	}
	

	public Object getActiveFlag(int index){
		
		return this.active_flag[index];
		
	}
	

	public Object getGcalEventId(int index){
		
		return this.gcal_event_id[index];
		
	}
	

	public Object getGoogleCalendarId(int index){
		
		return this.google_calendar_id[index];
		
	}
	
	public Object getGoogleCalendarEtag(int index){
		
		return this.google_calendar_etag[index];
		
	}
	
	public Object getPersonName(int index){
		
		return this.person_name[index];
		
	}

	public Object getOrgName(int index){
		
		return this.org_name[index];
		
	}
	
	public Object getNote(int index){
		
		return this.note[index];
		
	}
	
	public Object getDealTitle(int index){
		
		return this.deal_title[index];
		
	}
	
	public Object getAssignedToUserId(int index){
		
		return this.assigned_to_user_id[index];
		
	}
	
	public Object getCreatedByUserId(int index){
		
		return this.created_by_user_id[index];
		
	}
	
	public Object getOwnerName(int index){
		
		return this.owner_name[index];
		
	}
	
	public Object getPersonDropboxBcc(int index){
		
		return this.person_dropbox_bcc[index];
		
	}
	
	public Object getDealDropboxBcc(int index){
		
		return this.deal_dropbox_bcc[index];
		
	}
	
	public Object getDueDate(int index){
		
		return this.due_date[index];
	}
	
	public Object getDate(int index){
		return date[index];
	}
	
	public Object getTimeStamp(int index) {
		return timeStamp[index];
	}

	public Object getMonthStartDate(int index) {
		return monthStartDate[index];
	}

	public Object getQuarterStartDate(int index) {
		return quarterStartDate[index];
	}

	public Object getWeekStartDate(int index) {
		return weekStartDate[index];
	}

	public Object getYearStartDate(int index) {
		return yearStartDate[index];
	}

	public Object getDayOfWeek(int index) {
		return dayOfWeek[index];
	}

	public Object getHour(int index) {
		return hour[index];
	}

	public Object getMonthNumber(int index) {
		return monthNumber[index];
	}

	public Object getQuarter(int index) {
		return quarter[index];
	}

	public Object getYear(int index) {
		return year[index];
	}

	public Object getDayOfMonth(int index) {
		return dayOfMonth[index];
	}

	public Object getDayOfWeekName(int index) {
		return dayOfWeekName[index];
	}

	public Object getMonthName(int index) {
		return monthName[index];
	}
	
	
	public Object getDate1(int index){
		return date1[index];
	}
	
	public Object getTimeStamp1(int index) {
		return timeStamp1[index];
	}

	public Object getMonthStartDate1(int index) {
		return monthStartDate1[index];
	}

	public Object getQuarterStartDate1(int index) {
		return quarterStartDate1[index];
	}

	public Object getWeekStartDate1(int index) {
		return weekStartDate1[index];
	}

	public Object getYearStartDate1(int index) {
		return yearStartDate1[index];
	}

	public Object getDayOfWeek1(int index) {
		return dayOfWeek1[index];
	}

	public Object getHour1(int index) {
		return hour1[index];
	}

	public Object getMonthNumber1(int index) {
		return monthNumber1[index];
	}

	public Object getQuarter1(int index) {
		return quarter1[index];
	}

	public Object getYear1(int index) {
		return year1[index];
	}

	public Object getDayOfMonth1(int index) {
		return dayOfMonth1[index];
	}

	public Object getDayOfWeekName1(int index) {
		return dayOfWeekName1[index];
	}

	public Object getMonthName1(int index) {
		return monthName1[index];
	}
	
	
	public Object getDate2(int index){
		return date2[index];
	}
	
	public Object getTimeStamp2(int index) {
		return timeStamp2[index];
	}

	public Object getMonthStartDate2(int index) {
		return monthStartDate2[index];
	}

	public Object getQuarterStartDate2(int index) {
		return quarterStartDate2[index];
	}

	public Object getWeekStartDate2(int index) {
		return weekStartDate2[index];
	}

	public Object getYearStartDate2(int index) {
		return yearStartDate2[index];
	}

	public Object getDayOfWeek2(int index) {
		return dayOfWeek2[index];
	}

	public Object getHour2(int index) {
		return hour2[index];
	}

	public Object getMonthNumber2(int index) {
		return monthNumber2[index];
	}

	public Object getQuarter2(int index) {
		return quarter2[index];
	}

	public Object getYear2(int index) {
		return year2[index];
	}

	public Object getDayOfMonth2(int index) {
		return dayOfMonth2[index];
	}

	public Object getDayOfWeekName2(int index) {
		return dayOfWeekName2[index];
	}

	public Object getMonthName2(int index) {
		return monthName2[index];
	}
		
}
