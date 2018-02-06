package com.gxecard.customerservice.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TestGson {

	private String t1 = "vv333222";
	private String t2 = "vv333 22 .2";
	public String getT1() {
		return t1;
	}
	public void setT1(String t1) {
		this.t1 = t1;
	}
	public String getT2() {
		return t2;
	}
	public void setT2(String t2) {
		this.t2 = t2;
	}

	public static void main(String[] args) {
		String json = "{responseCode1=00000,class1=classcom.cn, responseCode=00000}";
		JsonObject j1 = new JsonParser().parse(json).getAsJsonObject();
		System.out.println(j1.get("class1").getAsString());

		String info = new Gson().toJson(new TestGson());
		System.out.println(info);
	}
}
