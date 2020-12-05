package com.treephones.cropixelbot.voice;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TTS {

	public Voice voice;
	public String name;
	
	public TTS(String name) {
		this.name = name;
		this.voice = VoiceManager.getInstance().getVoice(name);
		this.voice.allocate();
	}
	
	
}
