package com.test.automation.utilities;

import java.util.Map;
import java.util.Map.Entry;

public class MapUtils {

	public static void setValueInMapObjectByIdentifier(Map<Integer, Object[]> map, String identifierFieldValue, String fieldName, String fieldValueToSet) throws Exception {
		int key = 0;
		int index = 0;
		key = MapUtils.getMapObjectKeyByIdentifier(map, identifierFieldValue);
		Object[] obj = map.get(key);
		index = MapUtils.getMapObjectIndexByHeader(map, fieldName);
		obj[index] = fieldValueToSet;
		map.put(key, obj);
	}
	
	
	
	
	public static String getValueInMapObjectByIdentifier(Map<Integer, Object[]> map, String identifierFieldValue, String fieldName) throws Exception {
		int key = 0;
		int index = 0;
		key = MapUtils.getMapObjectKeyByIdentifier(map, identifierFieldValue);
		Object[] obj = map.get(key);
		index = MapUtils.getMapObjectIndexByHeader(map, fieldName);
		return obj[index].toString();
	}
	

	public static int getMapObjectKeyByIdentifier(Map<Integer, Object[]> map, String identifierFieldValue) throws Exception {
		int key = 0;
		for(Entry<Integer, Object[]> entry : map.entrySet()) {
			Object[] obj = entry.getValue();
			for(Object ob: obj){
				if(ob!=null && ob.toString().equals(identifierFieldValue))
					key = entry.getKey();
			}
		}
		return key;
	}
	
	public static int getObjectIndexByValue(Map<Integer, Object[]> map, String identifierFieldValue) throws Exception {
		int index = 0;
		for(Entry<Integer, Object[]> entry : map.entrySet()) {
			Object[] obj = entry.getValue();
			for(int i = 0; i < obj.length; i++){
				if(obj[i] != null && obj[i].toString().equals(identifierFieldValue)){
					index = i;
				}
			}
		}
		return index;
	}
	
	public static Object[] getMapEntryByIdentifier(Map<Integer, Object[]> map, String identifierFieldValue) throws Exception {
		Object[] mapObject = new Object[100];
		for(Entry<Integer, Object[]> entry : map.entrySet()) {
			Object[] obj = entry.getValue();
			for(Object ob: obj){
				if(ob!=null && ob.toString().equals(identifierFieldValue))
					mapObject = obj;
			}
		}
		return mapObject;
	}
	
	public static int getMapObjectIndexByHeader(Map<Integer, Object[]> map, String fieldName) throws Exception {
		Object obj[] = map.get(0);
		int index = 0;
		for(int i = 0; i < obj.length; i++){
			if(obj[i]!=null && obj[i].toString().equals(fieldName)){
				index = i;
			}
		}
		return index;
	}
	
	public static void setMapObjectValueByHeader(Map<Integer, Object[]> map, Object[] obj, String fieldName, String fieldValueToSet) throws Exception {
		int index;
		index = MapUtils.getMapObjectIndexByHeader(map, fieldName);
		obj[index] = fieldValueToSet;		
		
	}
	
	public static void setMapHeaderField(Map<Integer, Object[]> map, int fieldIndex, String fieldName) throws Exception {
		Object obj[] = map.get(0);
		obj[fieldIndex] = fieldName;
	}

	
	public static void setValueToEmptyFieldInAllMapObjects(Map<Integer, Object[]> map, String fieldName) throws Exception {
		int index = 0;
		int key = 0;
		for(Entry<Integer, Object[]> entry : map.entrySet()) {
			Object[] obj = entry.getValue();
			key = entry.getKey();
			index = MapUtils.getMapObjectIndexByHeader(map, fieldName);
			Object[] previousObject = map.get(key-1);
			if(obj[index].toString().equals(""))
				obj[index] = previousObject[index].toString();
		}
	}
	
	public static boolean verifyIfStringValuePresentInMap(Map<Integer, Object[]> map, String fieldName, String valueToSearch) throws Exception {
		int index = 0;
		index = MapUtils.getMapObjectIndexByHeader(map, fieldName);
		for(Entry<Integer, Object[]> entry : map.entrySet()) {
			Object[] obj = entry.getValue();			
			if(obj[index].toString().equals(valueToSearch))
				return true;
		}
		return false;
	}
	
	public static void replaceStringValueinMap(Map<Integer, Object[]> map, String valueToSearch, String valueToReplace) throws Exception {
		for(Entry<Integer, Object[]> entry : map.entrySet()){
			Object[] obj = entry.getValue();
			for(Object ob : obj){
				if (ob != null && ob.toString().equals(valueToSearch)){
					valueToSearch.replace(" ", valueToReplace);
				}
			}
		}
	}
	
	public static int getNumberOfMapEntriesBySingleValue(Map<Integer, Object[]> map, String identifier) throws Exception {
		int numberOfItems = 0;
		int index1 = MapUtils.getObjectIndexByValue(map, identifier);
		for(Entry<Integer, Object[]> entry : map.entrySet()) {
			Object[] obj = entry.getValue();
			if(obj[index1].toString().equals(identifier))
				numberOfItems++;
		}
		return numberOfItems;
	}
	
	
	public static int getNumberOfMapEntriesByTwoValues(Map<Integer, Object[]> map, String identifier, String value) throws Exception {
		int numberOfItems = 0;
		int index1 = MapUtils.getObjectIndexByValue(map, identifier);
		int index2 = MapUtils.getObjectIndexByValue(map, value);
		for(Entry<Integer, Object[]> entry : map.entrySet()) {
			Object[] obj = entry.getValue();
			if(obj[index1].toString().equals(identifier) && obj[index2].toString().equals(value))
				numberOfItems++;
		}
		return numberOfItems;
	}
	
	
	public static void createMapHeader(Map<String, Object[]> map, String[] header) throws Exception{
		Object[] mapObject = new Object[header.length];
		for (int i = 0; i < header.length; i++) {
			mapObject[i] = header[i];
		}
		map.put("0", mapObject);
	}
	
	public static int getAltMapObjectIndexByHeader(Map<String, Object[]> map, String fieldName) throws Exception {
		Object obj[] = map.get("0");
		int index = 0;
		for(int i = 0; i < obj.length; i++){
			if(obj[i]!=null && obj[i].toString().equals(fieldName)){
				index = i;
			}
		}
		return index;
	}
	
	public static int getTotalOfColumn(Map<String, Object[]> map, String columnName) throws Exception {
		int total = 0;
		int index = MapUtils.getAltMapObjectIndexByHeader(map, columnName);
		for (Entry<String, Object[]> entry : map.entrySet()) {
			if(entry.getKey() != "0"){
				Object[] obj = entry.getValue();
				total += Integer
						.parseInt(obj[index].toString());
			}
			
		}
		return total;
	}
	
	public static String getMapObjectValueFromSingleIdentifier(Map<Integer, Object[]> map, Object[] obj, String identifierFieldValue, String field) throws Exception {
		int index = MapUtils.getObjectIndexByValue(map, identifierFieldValue);
		int columnIndex = MapUtils.getMapObjectIndexByHeader(map, field);
		String value = null;
		if(obj[index].toString().equals(identifierFieldValue)){
			value = obj[columnIndex].toString();
			
		}
		return value;
			
			
	}
	
	public static String getMapObjectValueFromMultipleIdentifiers(Map<Integer, Object[]> map, Object[] obj, String identifierFieldValue1, String identifierFieldValue2, String field) throws Exception {
		int index1 = MapUtils.getObjectIndexByValue(map, identifierFieldValue1);
		int index2 = MapUtils.getObjectIndexByValue(map, identifierFieldValue2);
		int columnIndex = MapUtils.getMapObjectIndexByHeader(map, field);
		String value = null;
		if(obj[index1].toString().equals(identifierFieldValue1) && obj[index2].toString().equals(identifierFieldValue2)){
			value = obj[columnIndex].toString();
			
		}
		return value;
			
	}
	
	
	
}
