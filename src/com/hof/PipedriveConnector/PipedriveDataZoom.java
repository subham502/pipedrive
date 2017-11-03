package com.hof.PipedriveConnector;

import com.hof.util.UtilString;

public class PipedriveDataZoom {
	/*This function allows to get the translated text for a given key. 
	 * String translationKey is the unique identifier of the text that should be returned.
	 * If no translationKey was found then the function returns the default english text (passed in variable englishText)*/ 
	public static String getText(String englishText, String translationKey) 
	{		
	    String text = UtilString.getResourceString(translationKey);
	    if (text==null) return englishText;
	    return text;
	}
	
	
	/*This function can be used when the text you wish to translate contains a placeholder for another translatable text.
	 * Parameter translationKey is the key to main translatable text and paramKey is the key of text that should be inserted into main text
	 * If the main translatable text was not found the function will return the default englishText*/
	public static String getText(String englishText, String translationKey, String paramKey) {
		String key="";
		if(UtilString.getResourceString(paramKey)!=null)
		{
			key=UtilString.getResourceString(paramKey);
		}
		String text = UtilString.getResourceString(translationKey);
	    if (text==null) 
	    {
	    	return englishText;
	    }
	    else if (text.contains("{0}"))
	    {
	    	text=text.replace("{0}", "'"+key+"'");
	    }
	    
	    return text;
}	

}
