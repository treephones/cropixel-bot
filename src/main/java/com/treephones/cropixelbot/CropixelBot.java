package com.treephones.cropixelbot;

import javax.security.auth.login.LoginException;

import com.treephones.cropixelbot.StatQuery.Query;
import com.treephones.cropixelbot.events.HypixelQueries;
import com.treephones.cropixelbot.events.MinecraftQueries;
import com.treephones.cropixelbot.utils.Constants;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class CropixelBot {
	public static JDA jda;
	public Query api = new Query();
	
	public static void main(String[] args) throws LoginException {
		jda = new JDABuilder(AccountType.BOT)
				.setToken(Constants.key_discord)
				.build();	
		
		jda.addEventListener(new MinecraftQueries());
		jda.addEventListener(new HypixelQueries());
	}
}
