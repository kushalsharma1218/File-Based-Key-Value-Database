package com.file_based.main;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

import com.file_based.main.bean.Data;
import com.file_based.main.utils.CommonUtils;
import com.file_based.main.utils.Constant;


public final class DataStore{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dataStoreLoc = "";
	private String dataStoreName = "";


	public DataStore() {
		try {
			dataStoreLoc = Constant.defaultDataStoreLoc;
			dataStoreName = "datastore-" + CommonUtils.getProcessName();
		} catch (Exception exception) {

		}
	}

	public DataStore(String filePath) {
		try {
			dataStoreLoc = filePath;
			dataStoreName = "datastore-" + CommonUtils.getProcessName();
		} catch (Exception exception) {

		}

	}

	// Operations

		public synchronized String create(String key, JSONObject value) {
		try {
			System.out.println("Step 1");
			return create(key, value, -1);
		} catch (Exception exception) {
			return Constant.FAILURE_CREATE;
		}
	}

	
	public synchronized String create(String key, JSONObject value,
			int timeToLive) {
		try {
			System.out.println("Step 2");
			String filePath = dataStoreLoc + "/" + dataStoreName;
			// validate the key
			if (!CommonUtils.isKeyNameValid(key)) {
				return Constant.FAILURE_KEY_LENGTH_EXCEEDED;
			}
			if (CommonUtils.isKeyExists(key, filePath)) {
				return Constant.FAILURE_KEY_ALREADY_AVAILABLE;
			}
			// success flow
			Data data = new Data();
			data.setKey(key);
			if (timeToLive > 0) {
				data.setTimeToLive(timeToLive);
			} else {
				data.setTimeToLive(-1);
			}
			data.setValue(value);
			data.setCreationDateTimeMillis(new Date().getTime());

			if (CommonUtils.writeData(data, filePath)) {
				return Constant.SUCCESS_CREATE;
			} else {
				return Constant.FAILURE_CREATE;
			}
		} catch (Exception exception) {
			return Constant.FAILURE_CREATE;
		}
	}

	/**
	 * Method to read an element from the DataStore
	 * 
	 * @param key
	 *            The key of the element to read the element
	 * @return The value as type of JSONObject
	 */
	public synchronized Object read(String key) {
		try {
			String filePath = dataStoreLoc + "/" + dataStoreName;
			// validate the key
			if (!CommonUtils.isKeyNameValid(key)) {
				return Constant.FAILURE_KEY_LENGTH_EXCEEDED;
			}
			if (!CommonUtils.isKeyExists(key, filePath)) {
				return Constant.FAILURE_KEY_NOT_AVAILABLE;
			}
			// success flow

			Data data = CommonUtils.readData(key, filePath);
			if (null != data) {
				return data.getValue();
			}
			return Constant.FAILURE_READ;
		} catch (Exception exception) {
			exception.printStackTrace();
			return Constant.FAILURE_READ;
		}
	}

	public synchronized Object delete(String key) {
		try {
			String filePath = dataStoreLoc + "/" + dataStoreName;
			// validate the key
			if (!CommonUtils.isKeyNameValid(key)) {
				return Constant.FAILURE_KEY_LENGTH_EXCEEDED;
			}
			if (!CommonUtils.isKeyExists(key, filePath)) {
				return Constant.FAILURE_KEY_NOT_AVAILABLE;
			}
			// success flow

			if (CommonUtils.deleteData(key, filePath)) {
				return Constant.SUCCESS_DELETE;
			}
			return Constant.FAILURE_DELETE;
		} catch (Exception exception) {
			exception.printStackTrace();
			return Constant.FAILURE_DELETE;
		}
	}
}