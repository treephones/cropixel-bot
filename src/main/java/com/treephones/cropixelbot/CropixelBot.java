package com.treephones.cropixelbot;

import javax.security.auth.login.LoginException;

import com.treephones.cropixelbot.events.HypixelCommands;
import com.treephones.cropixelbot.events.MinecraftCommands;
import com.treephones.cropixelbot.stat.StatRetr;
import com.treephones.cropixelbot.utils.Constants;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class CropixelBot {
	public static JDA jda;
	public StatRetr api = new StatRetr();
	
	public static void main(String[] args) throws LoginException {
		jda = JDABuilder.createDefault(Constants.key_discord)
				.setActivity(Activity.watching("Hypixel"))
				.build();
		
		jda.addEventListener(new MinecraftCommands());
		jda.addEventListener(new HypixelCommands());
	}
}
