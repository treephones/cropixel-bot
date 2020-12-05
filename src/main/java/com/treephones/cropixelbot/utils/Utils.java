package com.treephones.cropixelbot.utils;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Utils {
	
	public static String chop(String s) {
		StringBuffer ret = new StringBuffer(s);
		ret.deleteCharAt(ret.length()-1);
		ret.deleteCharAt(0);
		return ret.toString();
	}
	
	public static String cleanUUID(String UUID) {
		return UUID.replace("-", "");
	}
	
	public static String[] getArgs(GuildMessageReceivedEvent e) {
		return e.getMessage().getContentRaw().split(" ");
	}
	
	public static String getMessage(String[] cmd, int index) {
		StringBuffer ret = new StringBuffer();
		for(int i = index; i < cmd.length; ++i) {
			ret.append(cmd[i] + " ");
		}
		return ret.toString();
	}
	
}