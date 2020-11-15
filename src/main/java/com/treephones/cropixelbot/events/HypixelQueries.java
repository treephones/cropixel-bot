package com.treephones.cropixelbot.events;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import com.treephones.cropixelbot.CropixelBot;
import com.treephones.cropixelbot.StatQuery.exceptions.UUIDNotFoundException;
import com.treephones.cropixelbot.StatQuery.exceptions.UsernameNotFoundException;
import com.treephones.cropixelbot.StatQuery.tasks.NewFriendsTask;
import com.treephones.cropixelbot.utils.Constants;
import com.treephones.cropixelbot.utils.Utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HypixelQueries extends ListenerAdapter {
	CropixelBot bot = new CropixelBot();
	HashMap<String, Timer> tasks = new HashMap<String, Timer>();
	String tracking_name = null;
	
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
			else if(cmd[0].equalsIgnoreCase(Constants.prompt + "start")) {
				if(cmd[1].equalsIgnoreCase("friends")) {
					e.getChannel().sendMessage(new EmbedBuilder().setTitle("Loading...").build()).queue();
					Timer timer = new Timer();
					timer.schedule(new NewFriendsTask(cmd[2], this.bot.jda.getTextChannelsByName(Constants.task_channel, true).get(0)),
							0, TimeUnit.HOURS.toMillis(1));
					this.tasks.put("friends", timer);	
					this.tracking_name = cmd[2];
					EmbedBuilder ret = new EmbedBuilder()
							.setThumbnail("https://crafatar.com/avatars/" + this.bot.api.usernameToUUID(cmd[2]))
							.setTitle("Started a Task!")
							.setDescription("Now tracking the friends list of " + cmd[2] + "!\nCheck the hypixel-updates channel for updates.");
					e.getChannel().sendMessage(ret.build()).queue();			
				}
			}
			else if(cmd[0].equalsIgnoreCase(Constants.prompt + "stop")) {
				if(cmd[1].equalsIgnoreCase("friends") && this.tracking_name != null) {
					tasks.get("friends").cancel();
					tasks.remove("friends");
					EmbedBuilder ret = new EmbedBuilder()
							.setThumbnail("https://crafatar.com/avatars/" + this.bot.api.usernameToUUID(this.tracking_name))
							.setTitle("Stopped a Task!")
							.setDescription("No longer tracking the friends list of " + tracking_name + ".");
					this.tracking_name = null;
					e.getChannel().sendMessage(ret.build()).queue();			
				}
				else {
					EmbedBuilder ret = new EmbedBuilder()
							.setTitle("Uh Oh!")
							.setDescription("Not currently tracking anyones friends list!");
					e.getChannel().sendMessage(ret.build()).queue();
				}
			}
		}
		catch(UsernameNotFoundException f) {
			//embed builder saying username not found
		}
		catch(UUIDNotFoundException h) {
			
		}
	}

}
