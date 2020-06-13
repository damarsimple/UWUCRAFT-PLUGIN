package com.uwucraft.website;



import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvent implements Listener{
	private Main plugin;
	public SerializeItem SItem;
	public PlayerEvent(Main plugin)
	{
		this.plugin = plugin;
		this.SItem = new SerializeItem();
	}
	@EventHandler()
	public void Join(PlayerJoinEvent event)
	{
		Player player = (Player) event.getPlayer();
		plugin.data.createPlayer(player);
		plugin.data.SyncData(player);
		plugin.data.LastSeen(player);
	}
	@EventHandler()
	public void Leave(PlayerQuitEvent event)
	{
		Player player = (Player) event.getPlayer();
		plugin.data.SyncData(player);
		plugin.data.LastSeen(player);
		Bukkit.getLogger().info("He Leaves " + player.getName());
	}
	@EventHandler()
	public void onKill(EntityDeathEvent event)
	{
		if(event.getEntity() instanceof Mob)
		{
			Player player = event.getEntity().getKiller();
			if(player == null)
			{
				return;
			}
			
			Random r = new Random();
			int amount = r.nextInt(1000);
			plugin.eco.depositPlayer(player, amount);
			player.sendMessage("You get " + amount + "$");
		}
	}
}