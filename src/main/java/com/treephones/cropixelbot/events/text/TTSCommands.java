package com.treephones.cropixelbot.events.text;

import java.awt.Color;

import com.treephones.cropixelbot.utils.Constants;
import com.treephones.cropixelbot.utils.Utils;
import com.treephones.cropixelbot.voice.TTS;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class TTSCommands extends ListenerAdapter {
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] cmd = Utils.getArgs(e);
		
		if(cmd[0].equalsIgnoreCase(Constants.prompt + "voice") || cmd[0].equalsIgnoreCase(Constants.prompt + "v")) {
			VoiceChannel channel = e.getMember().getVoiceState().getChannel();;
			AudioManager audio = e.getGuild().getAudioManager();
			if(channel == null) {
				e.getChannel().sendMessage(new EmbedBuilder()
						.setTitle("Uh Oh!")
						.setDescription("You must be connected to a voice channel to use this.")
						.setColor(Color.BLACK)
						.build()).queue();
				return;
			}
			
			if(Utils.isInArr(cmd[1], Constants.join_words)) {
				if(!audio.isConnected()) {
					audio.openAudioConnection(channel);
					e.getChannel().sendMessage(new EmbedBuilder()
							.setTitle("Connected!")
							.setDescription("Connected to " + channel.getName() + ".")
							.setColor(Color.BLACK)
							.build()).queue();
				}
				else {
					e.getChannel().sendMessage(new EmbedBuilder()
							.setTitle("Already Connected!")
							.setDescription("Already connected to " + channel.getName() + ".")
							.setColor(Color.BLACK)
							.build()).queue();
				}
			}
			else if(Utils.isInArr(cmd[1], Constants.leave_words)) {
				if(audio.isConnected()) {
					audio.closeAudioConnection();
					e.getChannel().sendMessage(new EmbedBuilder()
							.setTitle("Disconnected!")
							.setDescription("Disconnected from " + channel.getName() + ".")
							.setColor(Color.BLACK)
							.build()).queue();
				}
				else {
					e.getChannel().sendMessage(new EmbedBuilder()
							.setTitle("Not Connected!")
							.setDescription("Not connected to a voice channel!")
							.setColor(Color.BLACK)
							.build()).queue();
				}
			}
			else if(cmd[1].equalsIgnoreCase("say") && audio.isConnected()) {
				String message = Utils.getMessage(cmd, 2);
				TTS tts = new TTS("kevin16");
				tts.writeTTS(message);
				tts.say(e, channel, audio);
				e.getChannel().sendMessage(new EmbedBuilder()
						.setTitle("Message Said!")
						.setDescription("Message has been said in " + channel.getName() + ".")
						.setColor(Color.BLACK)
						.build()).queue();
			}
		}
	}

}
