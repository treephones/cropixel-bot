package com.treephones.cropixelbot.voice;

import java.awt.Color;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFileFormat.Type;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class TTS {

	public Voice voice;
	public AudioPlayer player;
	public String name;
	public String path = "src/main/resources/voicecache/voicecache";
	public String play_path = "src/main/resources/voicecache/voicecache.wav";
	
	public TTS(String name) {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		this.name = name;
		this.voice = VoiceManager.getInstance().getVoice(name);
		this.voice.allocate();
		
		this.player = new SingleFileAudioPlayer(this.path, Type.WAVE);
		this.voice.setAudioPlayer(this.player);
	}
	
	public void writeTTS(String message) {
		this.voice.speak(message);
		this.voice.deallocate();
		player.close();
	}
	
	public void say(GuildMessageReceivedEvent e, VoiceChannel channel, AudioManager audioManager) {
		AudioPlayerManager manager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerLocalSource(manager);
		com.sedmelluq.discord.lavaplayer.player.AudioPlayer messagePlayer = manager.createPlayer();
		AudioPlayerSendHandler sendHandler = new AudioPlayerSendHandler(messagePlayer);
		
		AudioLoadResultHandler handler = new AudioLoadResultHandler() {
			  @Override
			  public void trackLoaded(AudioTrack track) {
			    messagePlayer.playTrack(track);
			  }

			  @Override
			  public void playlistLoaded(AudioPlaylist playlist) {
			    return;
			  }

			  @Override
			  public void noMatches() {
				  e.getChannel().sendMessage(new EmbedBuilder()
							.setTitle("Error!")
							.setDescription("Something went wrong with playing the voice.")
							.setColor(Color.BLACK)
							.build()).queue();
			  }

			  @Override
			  public void loadFailed(FriendlyException throwable) {
				  e.getChannel().sendMessage(new EmbedBuilder()
							.setTitle("Error!")
							.setDescription("Something went wrong with playing the voice.")
							.setColor(Color.BLACK)
							.build()).queue();
			  }
			};
		manager.loadItem(this.play_path, handler);
		audioManager.setSendingHandler(sendHandler);
		audioManager.openAudioConnection(channel);
		
	}
}

class AudioPlayerSendHandler implements AudioSendHandler {
	  private final com.sedmelluq.discord.lavaplayer.player.AudioPlayer audioPlayer;
	  private AudioFrame lastFrame;

	  public AudioPlayerSendHandler(com.sedmelluq.discord.lavaplayer.player.AudioPlayer audioPlayer) {
	    this.audioPlayer = audioPlayer;
	  }

	  @Override
	  public boolean canProvide() {
	    lastFrame = audioPlayer.provide();
	    return lastFrame != null;
	  }

	  @Override
	  public ByteBuffer provide20MsAudio() {
	    return ByteBuffer.wrap(lastFrame.getData());
	  }

	  @Override
	  public boolean isOpus() {
	    return true;
	  }
	}
