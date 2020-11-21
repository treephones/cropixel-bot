package com.treephones.cropixelbot.events.text;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.treephones.cropixelbot.utils.Constants;
import com.treephones.cropixelbot.utils.Utils;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ImageCommands extends ListenerAdapter {
	public HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	
	void imageSet() {
		try {
			this.images.put("frick", ImageIO.read(new File("src/main/resources/images/lex.jpg")));
			this.images.put("brown", ImageIO.read(new File("src/main/resources/images/haddis.jpg")));
			this.images.put("greek", ImageIO.read(new File("src/main/resources/images/metre.jpg")));
			this.images.put("flip", ImageIO.read(new File("src/main/resources/images/flip.jpg")));
		}
		catch(IOException e) {e.printStackTrace();}
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] cmd = Utils.getArgs(e);
		
		if(cmd[0].equalsIgnoreCase(Constants.prompt + "meme")) {
			this.imageSet();
			Graphics g = this.images.get(cmd[1]).getGraphics();
			StringBuffer str = new StringBuffer();
			for(int i = 2; i < cmd.length; ++i) {
				str.append(cmd[i] + " ");
			}
			g.setFont(g.getFont().deriveFont(40f));
			g.drawString(str.toString(), 20, 100);
			g.dispose();
			try {
				ImageIO.write(this.images.get(cmd[1]), "jpg", new File("src/main/resources/editedcache/cache.jpg"));
				e.getChannel().sendFile(new File("src/main/resources/editedcache/cache.jpg")).queue();
			}
			catch(IOException f) {}
		}
		
	}
	
}
