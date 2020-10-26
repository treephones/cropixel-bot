package com.treephones.cropixelbot.utils;

public class Utils {
	public static String chop(String s) {
		StringBuffer ret = new StringBuffer(s);
		ret.deleteCharAt(ret.length()-1);
		ret.deleteCharAt(0);
		return ret.toString();
	}
}