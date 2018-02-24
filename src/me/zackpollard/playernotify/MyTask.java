package me.zackpollard.playernotify;

public class MyTask implements Runnable{
	private PlayerNotify plugin;
	private String playername;
	
	public MyTask(PlayerNotify plugin, String playername){
		this.plugin = plugin;
		this.playername = playername;
	}

	public void run(){
		
		plugin.alertedPlayers.remove(playername);
		
	}
}