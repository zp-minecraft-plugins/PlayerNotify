package me.zackpollard.playernotify.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PlayerStore {

	private File playersFile;
	private ArrayList<String> players;
	
	public PlayerStore(File file){
		this.playersFile = file;
		this.players = new ArrayList<String>();
		
		if (this.playersFile.exists() == false){
			try {
				this.playersFile.createNewFile();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public void load(){
		try {
			DataInputStream input = new DataInputStream(new FileInputStream(this.playersFile));
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			String line;
			
			while ((line = reader.readLine()) != null){
				if(this.players.contains(line) == false){
					this.players.add(line);
				}
			}
			reader.close();
			input.close();
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void save(){
		try {
			FileWriter stream = new FileWriter(this.playersFile);
			BufferedWriter out = new BufferedWriter(stream);
			
			for (String value : this.players){
				out.write(value);
				out.newLine();
			}
			out.close();
			stream.close();
			
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public boolean contains(String value){
		return this.players.contains(value);
	}
	
	public void add(String value){
		if(this.contains(value) == false){
			this.players.add(value);
		}	
	}
	
	public void remove(String value){
		this.players.remove(value);
	}
	
	public ArrayList<String> getValues(){
		return this.players;
	}
	
}
