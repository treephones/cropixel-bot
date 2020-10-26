package com.treephones.cropixelbot.events;

import com.treephones.cropixelbot.CropixelBot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.hypixel.api.reply.FriendsReply.FriendShip;

public class MinecraftQueries extends ListenerAdapter {
	CropixelBot bot = new CropixelBot();
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] cmd = e.getMessage().getContentRaw().split(" ");
		if(cmd[0].equalsIgnoreCase("/simpcheck")) {
			//bot.api.patQuery();
			e.getChannel().sendMessage(bot.api.UUIDtoUsername(cmd[1])).queue();
		}
	}
}
