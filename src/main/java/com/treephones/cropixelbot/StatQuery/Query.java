package com.treephones.cropixelbot.StatQuery;

import java.awt.Image;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.treephones.cropixelbot.StatQuery.exceptions.UUIDNotFoundException;
import com.treephones.cropixelbot.StatQuery.exceptions.UsernameNotFoundException;
import com.treephones.cropixelbot.utils.Constants;
import com.treephones.cropixelbot.utils.Utils;

import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reply.FriendsReply.FriendShip;

public class Query {
	public HypixelAPI api = new HypixelAPI(UUID.fromString(Constants.key_hypixel));
	public JsonObject query = null;
	public List<FriendShip> friendships = null;
	
	JsonParser parse = new JsonParser();
	
	public Query() {}
	
	public List<String> friendQuery(String username) throws UsernameNotFoundException {
		String uuid = usernameToUUID(username);
		List<String> friendNames = new ArrayList<String>();
		try {
			this.friendships = api.getFriends(uuid).get().getFriendShips();
			for(FriendShip friendship : this.friendships) {
				if(Utils.cleanUUID(friendship.getUuidReceiver().toString()).equals(uuid)) {
					friendNames.add(UUIDtoUsername(friendship.getUuidSender().toString()));
				}
				else {
					friendNames.add(UUIDtoUsername(friendship.getUuidReceiver().toString()));
				}
			}
			return friendNames;
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		catch(ExecutionException e) {
			e.printStackTrace();
		}
		catch(UUIDNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonObject getQuery() {
		return this.query;
	}
	
	public String usernameToUUID(String username) throws UsernameNotFoundException {
		try {
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			
			int responsecode;
			if ((responsecode = con.getResponseCode()) != 200) {
				throw new UsernameNotFoundException("Username: " + username + " does not exist!");
			}
			else {
				String contents = "";
				Scanner reader = new Scanner(url.openStream());
				while(reader.hasNext()) {
					contents += reader.nextLine();
				}
				reader.close();
				JsonObject obj = (JsonObject)parse.parse(contents);
				return Utils.chop(obj.get("id").toString());
			}
		}
		catch(MalformedURLException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String UUIDtoUsername(String UUID) throws UUIDNotFoundException {
		try {
			UUID = Utils.cleanUUID(UUID);
			URL url = new URL("https://api.mojang.com/user/profiles/" + UUID + "/names");
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			
			if(con.getResponseCode() != 200) {
				throw new UUIDNotFoundException("UUID: " + UUID + " does not exist!");
			}
			else {
				String contents = "";
				Scanner reader = new Scanner(url.openStream());
				while(reader.hasNext()) {
					contents += reader.nextLine();
				}
				reader.close();
				
				JsonArray objs = (JsonArray)parse.parse(contents);
				JsonObject obj = (JsonObject)objs.get(objs.size()-1);
				
				return Utils.chop(obj.get("name").toString());
			}
		}
		catch(MalformedURLException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
