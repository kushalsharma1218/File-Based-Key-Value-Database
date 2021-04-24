package com.file_based.main;

import java.io.Serializable;

import org.json.JSONObject;

public class DataConsumer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("firstName", "xyz");
		jsonObject.put("lastName", "abc");
		jsonObject.put("address", "Chennai");

		System.out.println("========================CREATE ==============================");

		DataStore myDataStore = new DataStore(
				"C:\\Users\\kushal\\Desktop\\Data");
		System.out.println("Step 0 comleted");
		System.out.println(myDataStore.create("1", jsonObject, 10));// success
		System.out.println(myDataStore.create("1", jsonObject));// failure
		System.out.println(myDataStore.create("1", jsonObject, 10));// failure
		System.out.println(myDataStore.create("2", jsonObject));// success
		System.out.println(myDataStore.create(
				"helloDataStoreThisIsKeyNameValidation", new JSONObject()));// failure
		try {
			// wait for 10 seconds
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("====================AFTER WAIT===============");
		jsonObject.put("age", "25");
		System.out.println(myDataStore.create("1", jsonObject, 10));// success
		System.out.println(myDataStore.create("1", jsonObject));// failure
		System.out.println(myDataStore.create("1", jsonObject, 10));// failure
		System.out.println(myDataStore.create("2", jsonObject));// failure

		// Read Operation
		System.out
				.println("=============================================================");
		System.out
				.println("==========================READ===============================");
		System.out
				.println("=============================================================");
		System.out.println(myDataStore.read("1"));// success
		System.out.println(myDataStore.read("2"));// success
		System.out.println(myDataStore.read("3"));// failure
		System.out.println(myDataStore
				.read("helloDataStoreThisIsKeyNameValidation"));// failure
		try {
			// wait for 10 seconds
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("====================AFTER WAIT===============");
		System.out.println(myDataStore.read("1"));// failure
		System.out.println(myDataStore.read("2"));// success

		System.out
				.println("=============================================================");
		System.out
				.println("========================DELETE ==============================");
		System.out
				.println("=============================================================");
		System.out.println(myDataStore.delete("1"));// failure
		System.out.println(myDataStore.delete("2"));// success
		System.out.println(myDataStore.delete("2"));// failure
		System.out.println(myDataStore.delete("3"));// failure
		System.out.println(myDataStore
				.delete("helloDataStoreThisIsKeyNameValidation"));// failure
		
		/*
		 * DataStore myDataStore1 = new
		 * DataStore("C:\\Users\\John\\Documents\\DataStore");
		 * System.out.println(myDataStore1.create("1", new JSONObject(), 10));
		 */
	}
}
