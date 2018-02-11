package com.test.automation.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static Date date;
	public static SimpleDateFormat dateFormat;
	public static String currentDate;
	
	
	public static String getSimpleDateFormat(){
		date = new Date();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		currentDate = dateFormat.format(date);
		return currentDate;
	}
	

}
