package com.treephones.cropixelbot.events;

import java.util.List;

import com.treephones.cropixelbot.CropixelBot;
import com.treephones.cropixelbot.StatQuery.exceptions.UsernameNotFoundException;
import com.treephones.cropixelbot.utils.Constants;
import com.treephones.cropixelbot.utils.Utils;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HypixelQueries extends ListenerAdapter {
	CropixelBot bot = new CropixelBot();
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] cmd = Utils.getArgs(e);
		try {
			if(cmd[0].equalsIgnoreCase(Constants.prompt + "hypixel")) {
				if(cmd[1].equalsIgnoreCase("friends")) {
					List<String> friends = bot.api.friendQuery(cmd[2]);
					e.getChannel().sendMessage("Friends of " + cmd[2] + ":").queue();
					for(String friend : friends) {
						e.getChannel().sendMessage(friend).queue();
					}
				}
				//add other hypixel stats below here
			}
		}
		catch(UsernameNotFoundException f) {
			
		}
		//add catch for uuidnotfoundexception
	}

}
