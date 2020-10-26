package com.treephones.cropixelbot.events;

import com.treephones.cropixelbot.CropixelBot;
import com.treephones.cropixelbot.StatQuery.exceptions.UUIDNotFoundException;
import com.treephones.cropixelbot.StatQuery.exceptions.UsernameNotFoundException;
import com.treephones.cropixelbot.utils.Constants;
import com.treephones.cropixelbot.utils.Utils;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MinecraftQueries extends ListenerAdapter {
	CropixelBot bot = new CropixelBot();
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] cmd = Utils.getArgs(e);
		try {
			if(cmd[0].equalsIgnoreCase(Constants.prompt + "get")) {
				if(cmd[1].equalsIgnoreCase("username")) {
					e.getChannel().sendMessage(bot.api.UUIDtoUsername(cmd[2])).queue();
				}
				else if(cmd[1].equalsIgnoreCase("uuid")) {
					e.getChannel().sendMessage(bot.api.usernameToUUID(cmd[2])).queue();
				}
				else {
					e.getChannel().sendMessage("invalid argument, format /get [type] [value]").queue();
				}
			}
		}
		catch(UsernameNotFoundException f) {
			e.getChannel().sendMessage(cmd[2] + " is a nonexistant username!").queue();
		}
		catch(UUIDNotFoundException f) {
			e.getChannel().sendMessage(cmd[2] + " is a nonexistant UUID!").queue();
		}
	}
}
