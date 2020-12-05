package com.treephones.cropixelbot;

import javax.security.auth.login.LoginException;

import com.treephones.cropixelbot.events.text.HypixelCommands;
import com.treephones.cropixelbot.events.text.ImageCommands;
import com.treephones.cropixelbot.events.text.MinecraftCommands;
import com.treephones.cropixelbot.events.text.TTSCommands;
import com.treephones.cropixelbot.stat.StatRetr;
import com.treephones.cropixelbot.utils.Constants;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class CropixelBot {
	public static JDA jda;
	public StatRetr api = new StatRetr();
	
	public static void main(String[] args) throws LoginException {
		jda = JDABuilder.createDefault(Constants.key_discord)
				.setActivity(Activity.watching("Hypixel"))
				.build();
		
		jda.addEventListener(new MinecraftCommands());
		jda.addEventListener(new HypixelCommands());
		jda.addEventListener(new ImageCommands());
		jda.addEventListener(new TTSCommands());
	}
}
