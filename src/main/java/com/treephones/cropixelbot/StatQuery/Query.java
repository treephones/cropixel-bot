package com.treephones.cropixelbot.StatQuery;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.treephones.cropixelbot.utils.Constants;
import com.treephones.cropixelbot.utils.Utils;

import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reply.FriendsReply.FriendShip;

public class Query {
	public HypixelAPI api = new HypixelAPI(UUID.fromString(Constants.key_hypixel));
	public JsonObject query = null;
	public List<FriendShip> friends = null;
	
	JsonParser parse = new JsonParser();
	
	public Query() {}
	
	public void patQuery() {
		try {
			this.friends = api.getFriends(Constants.patrick_uuid).get().getFriendShips();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		catch(ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public JsonObject getQuery() {
		return this.query;
	}
	
	public String usernameToUUID(String username) {
		try {
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			
			int responsecode;
			if ((responsecode = con.getResponseCode()) != 200) {
				throw new UsernameNotFoundException("Username does not exist!");
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
		catch(UsernameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String UUIDtoUsername(String UUID) {
		try {
			URL url = new URL("https://api.mojang.com/user/profiles/" + UUID + "/names");
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			
			int responsecode;
			if ((responsecode = con.getResponseCode()) != 200) {
				throw new UsernameNotFoundException("Username does not exist!");
			}
			else {
				String contents = "";
				Scanner reader = new Scanner(url.openStream());
				while(reader.hasNext()) {
					contents += reader.nextLine();
				}
				reader.close();
				contents = Utils.chop(contents);
				
				JsonObject obj = (JsonObject)parse.parse(contents);
				return Utils.chop(obj.get("name").toString());
			}
		}
		catch(MalformedURLException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(UsernameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
