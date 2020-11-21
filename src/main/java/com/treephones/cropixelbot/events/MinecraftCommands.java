package com.treephones.cropixelbot.events;

import java.awt.Color;

import com.treephones.cropixelbot.CropixelBot;
import com.treephones.cropixelbot.stat.exceptions.UUIDNotFoundException;
import com.treephones.cropixelbot.stat.exceptions.UsernameNotFoundException;
import com.treephones.cropixelbot.utils.Constants;
import com.treephones.cropixelbot.utils.Utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MinecraftCommands extends ListenerAdapter {
	CropixelBot bot = new CropixelBot();
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] cmd = Utils.getArgs(e);
		try {
			if(cmd[0].equalsIgnoreCase(Constants.prompt + "get")) {
				if(cmd[1].equalsIgnoreCase("username")) {
					EmbedBuilder ret = new EmbedBuilder().setTitle("UUID:\t" + cmd[2] + "\nUsername:\t" + bot.api.UUIDtoUsername(cmd[2]))
							.setColor(Color.green)
							.setThumbnail("https://crafatar.com/avatars/" + cmd[2]);
					e.getChannel().sendMessage(ret.build()).queue();
				}
				else if(cmd[1].equalsIgnoreCase("uuid")) {
					String uuid = bot.api.usernameToUUID(cmd[2]);
					EmbedBuilder ret = new EmbedBuilder().setTitle("Username:\t" + cmd[2] + "\nUUID:\t" + uuid)
							.setColor(Color.green)
							.setThumbnail("https://crafatar.com/avatars/" + uuid);
					e.getChannel().sendMessage(ret.build()).queue();
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
