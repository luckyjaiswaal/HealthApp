package com.example.healthapp.model;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SafeJSONObject  {
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mObject.toString();
	}

	private JSONObject mObject;
	
	public SafeJSONObject() {
		mObject = new JSONObject();
	}
	
	public SafeJSONObject(JSONObject object) {
		mObject = object;	
	}
	
	public JSONObject getObject() {return mObject;}
	
	public SafeJSONObject(String value) {
		try {
			mObject = new JSONObject(value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
	}
	
	public SafeJSONObject getJSONObject(String key) {
		try {
			return new SafeJSONObject(mObject.getJSONObject(key));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SafeJSONObject();
        /*ToDo for safejson*/
//        return  null;
	}
	
	public SafeJSONArray getJSONArray(String key) {
		try {
			return new SafeJSONArray(mObject.getJSONArray(key));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new SafeJSONArray();
        /*ToDo for safejson*/
//        return  null;
	}

	public void putString(String key, String value) {
		try {
			mObject.put(key, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void putBitmap(String key, Bitmap value) {
		try {
			mObject.put(key, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getString(String key) {
		String returnString = "";
		try {
			if (!mObject.has(key))
				return returnString;
			returnString = mObject.getString(key);
			if(returnString.equals("null"))
				return "";
			return mObject.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnString;
	}
	
	public int getInt(String key) {

		return mObject.optInt(key, 0);

	}
	
	public void putInt(String key, int value) {
		try {
			mObject.put(key, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void putSafeJSONObject(String key, SafeJSONObject jsonObject) {
		try {
			mObject.put(key, jsonObject.getObject());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void putLong(String key, long l) {
		try {
			mObject.put(key, l);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public long getLong(String key) {
		try {
			return mObject.getLong(key);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public double getDouble(String key) {
		try {
			return mObject.getDouble(key);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	

	public void putBoolean(String key, boolean value) {
		try {
			mObject.put(key, value);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean getBoolean(String key) {
		try {
			return mObject.getBoolean(key);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public void putByte(String key, Object value) {
		try {
			mObject.put(key, value);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public byte[] getByte(String key) {
		try {
			return (byte[]) mObject.get(key);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void putSafeJSONArray(String name) {
		// TODO Auto-generated method stub
		try {
			mObject.put(name, new JSONArray());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void putSafeJSONArray(String name, SafeJSONArray array) {
		// TODO Auto-generated method stub
		try {
			mObject.put(name, array.getJSONArrayObject());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void putSafeJSONArray(String name, JSONArray newArray) {
		// TODO Auto-generated method stub
		try {
			mObject.put(name, newArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isNull (String key){
		return mObject.isNull(key);
	}

	public boolean has (String key){
		return mObject.has(key);
	}

	public boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			// edited, to include @Arthur's comment
			// e.g. in case JSONArray is valid as well...
			try {
				new JSONArray(test);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}

	public void putNull(String key){
		try {
			mObject.put(key, JSONObject.NULL);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void putObject(String key, Object object){
		try {
			mObject.put(key, object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void putDouble(String key, double value) {
		try {
			mObject.put(key, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Object get (String key){
		try {
			return mObject.get(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
