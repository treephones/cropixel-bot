package com.treephones.cropixelbot.events.text;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.treephones.cropixelbot.utils.Constants;
import com.treephones.cropixelbot.utils.Utils;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.AttachmentOption;

public class ImageCommands extends ListenerAdapter {
	public HashMap<String, BufferedImage> images = images();
	
	HashMap<String, BufferedImage> images() {
		try {
			this.images.put("frick", ImageIO.read(new File("resources/images/lex.jpg")));
		}
		catch(IOException e) {}
		return this.images;
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] cmd = Utils.getArgs(e);
		
		if(cmd[0].equalsIgnoreCase(Constants.prompt + "meme")) {
			Graphics g = this.images.get(cmd[1]).getGraphics();
			StringBuffer str = new StringBuffer();
			for(int i = 2; i < cmd.length; ++i) {
				str.append(cmd[i] + " ");
			}
			g.drawString(str.toString(), 20, 20);
			g.dispose();
			try {
				ImageIO.write(this.images.get(cmd[1]), "jpg", new File("resources/images/lex.jpg"));
				e.getChannel().sendFile(new File("resources/images/lex.jpg")).queue();
			}
			catch(IOException f) {}
		}
		
	}
	
}
