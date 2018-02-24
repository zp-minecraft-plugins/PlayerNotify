package me.zackpollard.playernotify;

import java.util.logging.Logger;

import net.minecraft.server.Packet62NamedSoundEffect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MyPlayerListener implements Listener{
	public static PlayerNotify plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	public MyPlayerListener(PlayerNotify instance){
		plugin = instance;
		Bukkit.getServer().getPluginManager().registerEvents(this,instance);
	}
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		
		Player player = event.getPlayer();
		String playername = player.getName().toString();
		
		if(!player.hasPermission("playernotify.alert")) return;
		
		if(!plugin.players.contains(playername)) return;
		
		if(plugin.alertedPlayers.contains(playername)) return;
		
		String message = plugin.getConfig().getString("PlayerNotify.Message");
		Integer radius = plugin.getConfig().getInt("PlayerNotify.Radius");
		Integer timeout = plugin.getConfig().getInt("PlayerNotify.Timeout");
		
		CraftPlayer craftplayer = (CraftPlayer)player;
		
		for(Entity entity : player.getNearbyEntities(radius, radius, radius)){
				
			if(entity instanceof Player){
				
				Player stalker = (Player) entity;
				
				if(stalker.hasPermission("playernotify.exempt")) continue;
				
				Packet62NamedSoundEffect packet = new Packet62NamedSoundEffect("random.orb", player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), 1.0F, 0.5F);
				craftplayer.getHandle().netServerHandler.sendPacket(packet);

				player.sendMessage(ChatColor.RED + message);
				
				plugin.alertedPlayers.add(playername);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new MyTask(plugin, playername), 20 * timeout);
				
			}
		}
	}
}