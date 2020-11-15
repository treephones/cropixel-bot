package com.treephones.cropixelbot;

import javax.security.auth.login.LoginException;

import com.treephones.cropixelbot.StatQuery.Query;
import com.treephones.cropixelbot.events.HypixelQueries;
import com.treephones.cropixelbot.events.MinecraftQueries;
import com.treephones.cropixelbot.utils.Constants;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class CropixelBot {
	public static JDA jda;
	public Query api = new Query();
	
	public static void main(String[] args) throws LoginException {
		jda = JDABuilder.createDefault(Constants.key_discord)
				.setActivity(Activity.watching("Hypixel"))
				.build();
		
		jda.addEventListener(new MinecraftQueries());
		jda.addEventListener(new HypixelQueries());
	}
}
