package me.zackpollard.playernotify;
     
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import me.zackpollard.playernotify.utils.PlayerStore;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerNotify extends JavaPlugin {
	
    private static final Logger log = Logger.getLogger("Minecraft");
    
    public ArrayList<String> alertedPlayers = new ArrayList<String>();
    public PlayerStore players;
    
    public void onEnable() {
    	new MyPlayerListener(this);
    	
    	String pluginFolder = this.getDataFolder().getAbsolutePath();
		(new File(pluginFolder)).mkdirs();
		this.players = new PlayerStore(new File(pluginFolder + File.separator + "players.txt"));
		this.players.load();
		
		final FileConfiguration config = this.getConfig();
        config.options().header("PlayerNotify Config");
        config.addDefault("PlayerNotify.Message", "A Player is near, beware!");
        config.addDefault("PlayerNotify.Radius", Integer.valueOf(10));
        config.addDefault("PlayerNotify.Timeout", Integer.valueOf(30));
        config.options().copyDefaults(true);
        saveConfig();
        log.info("PlayerNotify Version 1.0 Enabled");
    }
    public void onDisable() {
        log.info("PlayerNotify Version 1.0 Disabled");
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	boolean op = false;
    	String playername = sender.getName().toString();
    	
    	if(sender instanceof Player){
    		Player player = (Player) sender;
    		op = player.isOp();
    	}
    	if(label.equalsIgnoreCase("pn")){
    		if(op || sender instanceof ConsoleCommandSender){
    			if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
    				this.reloadConfig();
    				log.info("Reloaded Config File");
    				if(op){
    					sender.sendMessage(ChatColor.GOLD + "PlayerNotify Config Reloaded");
    				}
    				return true;         
    			}
    		}
    	}
    	if(label.equalsIgnoreCase("punchalert")){
			if(sender.hasPermission("playernotify.toggle")){
				if(!players.contains(playername)){
					
					players.add(playername);
					players.save();
					
					sender.sendMessage(ChatColor.GREEN + "You will now be notified of nearby players!");
					
					return true;
					
				} else {
					if(players.contains(playername)){
						
						players.remove(playername);
						players.save();
						
						sender.sendMessage(ChatColor.GREEN + "You will no longer be notified of nearby players!");
						
						return true;
    				}
    			}
    		}
    	}
    	return false;
    }
}