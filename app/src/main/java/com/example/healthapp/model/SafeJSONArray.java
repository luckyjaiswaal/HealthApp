package com.example.healthapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SafeJSONArray {
	private JSONArray mObject;
	
	public SafeJSONArray() {
		mObject = new JSONArray();
	}
	
	public SafeJSONArray(JSONArray object) {
		mObject = object;
	}
	
	public SafeJSONArray(String value) {
		try {
			mObject = new JSONArray(value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SafeJSONArray(ArrayList<SafeJSONObject> list) {
		mObject = new JSONArray();
		for (int idx = 0; idx < list.size(); idx++) {
			mObject.put(list.get(idx).getObject());
		}
	}
	
	public SafeJSONObject getJSONObject(int idx) {
		try {
			return new SafeJSONObject(mObject.getJSONObject(idx));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SafeJSONObject();
		/*ToDo for safejson*/
//        return  null;
	}
	public SafeJSONObject getJSONObject1(int idx) {
		try {
			return (SafeJSONObject) mObject.get(idx);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SafeJSONObject();
		/*ToDo for safejson*/
//        return  null;
	}
	

	public JSONArray getJSONArrayObject (){
		return mObject;
	}

	
	public SafeJSONArray getJSONArray(int idx) {
		try {
			return new SafeJSONArray(mObject.getJSONArray(idx));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new SafeJSONArray();
		/*ToDo for safejson*/
//        return  null;
	}
	
	public String getString (int idx) {
		try {
			return mObject.getString(idx);
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
		/*ToDo for safejson*/
//        return  null;;
	}
	
	public int getInt (int idx) {
		try {
			return mObject.getInt(idx);
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	public double getDouble (int idx) {
		try {
			return mObject.getDouble(idx);
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	public int length() {
		return mObject.length();
	}
	
	public void sortArray(Comparator<SafeJSONObject> comparator) {
		List<SafeJSONObject> safeJsonValues = new ArrayList<SafeJSONObject>();
		for (int idx = 0; idx < mObject.length(); idx++) {
			safeJsonValues.add(getJSONObject(idx));
		}
		
		Collections.sort(safeJsonValues, comparator);
		
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		for (int idx = 0; idx < safeJsonValues.size(); idx++) {
			jsonValues.add(safeJsonValues.get(idx).getObject());
		}
		
		mObject = new JSONArray(jsonValues);
	}

	public SafeJSONArray toSafeJsonArray(ArrayList<SafeJSONObject> list) {
		SafeJSONArray toSafeJsonArray = new SafeJSONArray();
		for (int idx = 0; idx < list.size(); idx++) {
			toSafeJsonArray.put(list.get(idx).getObject());
		}
		return toSafeJsonArray;
	}

	public SafeJSONArray toSafeJsonArray2(ArrayList<JSONObject> list) {
		SafeJSONArray toSafeJsonArray = new SafeJSONArray();
		for (int idx = 0; idx < list.size(); idx++) {
			toSafeJsonArray.put(list.get(idx));
		}
		return toSafeJsonArray;
	}
	
	public ArrayList<String> toStringArray() {
		try {
			ArrayList<String> list = new ArrayList<String>();
			for (int idx = 0; idx < mObject.length(); idx++) {
				list.add(mObject.getString(idx));
			}
		
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<String>();
		/*ToDo for safejson*/
//        return  null;
	}

	public ArrayList<Integer> toIntegerArray() {
		try {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int idx = 0; idx < mObject.length(); idx++) {
				list.add(mObject.getInt(idx));
			}
		
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Integer>();
		/*ToDo for safejson*/
//        return  null;
	}
	
	/**
	 * @author anonymous
	 * @return
	 */
	public String[] toStringInArray() {
		try {
			String[] list = new String[mObject.length()];
			for (int idx = 0; idx < mObject.length(); idx++) {
				list[idx] = mObject.getString(idx);
			}
		
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<JSONObject> toJSONObjectArray() {
		try {
			ArrayList<JSONObject> list = new ArrayList<JSONObject>();
			for (int idx = 0; idx < mObject.length(); idx++) {
				list.add(mObject.getJSONObject(idx));
			}
			
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<JSONObject>();
		/*ToDo for safejson*/
//        return  null;
	}
	
	public ArrayList<SafeJSONObject> toSafeJSONObjectArray() {
		ArrayList<SafeJSONObject> list = new ArrayList<SafeJSONObject>();
		for (int idx = 0; idx < mObject.length(); idx++) {
			list.add(getJSONObject(idx));
		}
		
		return list;
				
	}
	
	public JSONArray remove (int index, SafeJSONArray arrayToArray){
		JSONArray Njarray=new JSONArray();
		try{
			for(int i=0;i<arrayToArray.length();i++){     
				if(i!=index)
					Njarray.put(arrayToArray.getString(i));     
			}
			return Njarray;
		}catch (Exception e){e.printStackTrace();}
		return new JSONArray();
		/*ToDo for safejson*/
//        return  null;
	}

	public SafeJSONArray removeValue (int value, SafeJSONArray array){
		SafeJSONArray Njarray=new SafeJSONArray();
		try{
			for(int i=0;i<array.length();i++){
				int checkId = array.getInt(i);
				if(checkId != value)
					Njarray.put(array.getInt(i));
			}
			return Njarray;
		}catch (Exception e){e.printStackTrace();}
		return new SafeJSONArray();
		/*ToDo for safejson*/
//        return  null;
	}

	public SafeJSONArray removeSafeJsonObject (int index, SafeJSONArray arrayToArray){
		SafeJSONArray Njarray=new SafeJSONArray();
		try{
			for(int i=0;i<arrayToArray.length();i++){     
				if(i!=index)
					Njarray.put(arrayToArray.getJSONObject(i).getObject());     
			}
			return Njarray;
		}catch (Exception e){e.printStackTrace();}
		return new SafeJSONArray();
		/*ToDo for safejson*/
//        return  null;
	}
	
	public void addJSONObject(SafeJSONObject jsonObject) {
		if (mObject != null)
			mObject.put(jsonObject.getObject());
	}

	public void addJSONObject(SafeJSONObject jsonObject, int index) {
		try {
			mObject.put(index, jsonObject.getObject());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addString(String string) {
		if (mObject != null)
			mObject.put(string);
	}

	public void addInteger(int integer) {
		if (mObject != null)
			mObject.put(integer);
	}
	
	public String toString() {
		return mObject.toString();
	}
	
	public void put(JSONObject jsonObject) {
		mObject.put(jsonObject);
	}
	
	public void put(String string) {
		mObject.put(string);
	}
	
	public void put(int value) {
		mObject.put(value);
	}
	
	public void put(int idx, int value) {
		try {
			mObject.put(idx, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isJSONValid(String test) {
		try {
			new JSONArray(test);
			return true;
		} catch (JSONException ex1) {
			return false;
		}
	}
}
