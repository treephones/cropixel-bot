package com.treephones.cropixelbot.events;

import java.util.List;

import com.treephones.cropixelbot.CropixelBot;
import com.treephones.cropixelbot.StatQuery.exceptions.UsernameNotFoundException;
import com.treephones.cropixelbot.utils.Constants;
import com.treephones.cropixelbot.utils.Utils;

import net.dv8tion.jda.api.EmbedBuilder;
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
					e.getChannel().sendMessage(new EmbedBuilder().setDescription(
							"Loading friends list of " + cmd[2] + ".\n This may take a while if they have a large friends list.").build()
							).queue();
					List<String> friends = bot.api.friendQuery(cmd[2]);
					EmbedBuilder flist = new EmbedBuilder().setTitle("Friends List of " + cmd[2] + ":");
					StringBuffer flist_str = new StringBuffer();
					for(String friend : friends) {
						flist_str.append(friend + "\n");
					}
					flist.setDescription(flist_str.toString());
					flist.setThumbnail("https://crafatar.com/avatars/" + this.bot.api.usernameToUUID(cmd[2]));
					e.getChannel().sendMessage(flist.build()).queue();
				}
				//add other hypixel stats below here
			}
		}
		catch(UsernameNotFoundException f) {
			
		}
		//add catch for uuidnotfoundexception
	}

}
