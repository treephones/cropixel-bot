package com.treephones.cropixelbot.events.text;

import java.awt.Color;

import com.treephones.cropixelbot.utils.Utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class TTSCommands extends ListenerAdapter {
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] cmd = Utils.getArgs(e);
		VoiceChannel channel = null;
		AudioManager audio = e.getGuild().getAudioManager();
		try {
			channel = e.getMember().getVoiceState().getChannel();
		}
		catch(Exception f) {
			e.getChannel().sendMessage(new EmbedBuilder()
					.setTitle("Uh Oh!")
					.setDescription("You must be connected to a voice channel to use this.")
					.setColor(Color.BLACK)
					.build()).queue();
		}
		
		if(cmd[0].equalsIgnoreCase("TTS")) {
			if(cmd[1].equalsIgnoreCase("start")) {
				if(!audio.isConnected()) {
					audio.openAudioConnection(channel);
				}
				else {
					e.getChannel().sendMessage(new EmbedBuilder()
							.setTitle("Already Connected!")
							.setDescription("Already connected to " + channel.getName() + ".")
							.setColor(Color.BLACK)
							.build()).queue();
				}
			}
			else if(cmd[1].equalsIgnoreCase("stop")) {
				if(audio.isConnected()) {
					audio.closeAudioConnection();
				}
				else {
					e.getChannel().sendMessage(new EmbedBuilder()
							.setTitle("Not Connected!")
							.setDescription("Not connected to a voice channel!")
							.setColor(Color.BLACK)
							.build()).queue();
				}
			}
		}
	}

}
