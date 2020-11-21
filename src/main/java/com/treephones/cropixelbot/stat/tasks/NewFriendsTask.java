package com.treephones.cropixelbot.stat.tasks;

import java.awt.Color;
import java.util.List;
import java.util.TimerTask;

import com.treephones.cropixelbot.CropixelBot;
import com.treephones.cropixelbot.stat.StatRetr;
import com.treephones.cropixelbot.stat.exceptions.UUIDNotFoundException;
import com.treephones.cropixelbot.stat.exceptions.UsernameNotFoundException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class NewFriendsTask extends TimerTask {
	String username;
	String uuid;
	StatRetr q;
	List<String> prevflist;
	TextChannel channel;
	
	public NewFriendsTask(String username, TextChannel channel) throws UsernameNotFoundException, UUIDNotFoundException {
		this.q = new CropixelBot().api;
		this.uuid = this.q.usernameToUUID(username);
		this.username = this.q.UUIDtoUsername(this.uuid);	
		this.prevflist = this.q.friendQuery(username);
		this.channel = channel;
	}

	@Override
	public void run() {
		try {
			List<String> curflist = this.q.friendQuery(this.username);
			EmbedBuilder ret = new EmbedBuilder()
					.setColor(Color.blue)
					.setThumbnail("https://crafatar.com/avatars/" + this.uuid);
			if(curflist.equals(this.prevflist)) {
				ret.setTitle(this.username + " has made no new friends in the past hour!");
				ret.setDescription("They are lonely.");
			}
			else {
				int nAmount = curflist.size() - this.prevflist.size();
				ret.setTitle(this.username + " has made " + nAmount + " new friends!\nHere they are:");
				StringBuffer nFriends = new StringBuffer();
				for(int i = 0; i < nAmount; ++i) {
					nFriends.append(curflist.get(this.prevflist.size()+i) + "\n");
				}
				ret.setDescription(nFriends.toString());
			}
			this.channel.sendMessage(ret.build()).queue();
			this.prevflist = curflist;
		}
		catch(UsernameNotFoundException e) {}	
	}
	
	public String getUsername() {
		return this.username;
	}
}
