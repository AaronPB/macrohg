package com.aaronpb.macrohg.Events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.aaronpb.macrohg.Core;

public class EventArenaAccess implements Listener {
  private Core core = new Core();

  @EventHandler
  public void PlayerMovedFromWorld(PlayerChangedWorldEvent event) {
    World  previousworld = (World) event.getFrom();
    Player player        = (Player) event.getPlayer();
    World  newworld      = player.getWorld();

    if (!previousworld.equals(Core.arena) && !newworld.equals(Core.arena)) {
      return;
    }

    if (previousworld.equals(Core.arena)) {
      if (Core.arenarunning && core.getIsAliveTribute(player)) {
        core.addToAlertSystem(player);
      }
      core.removeFromScoreboard(player);
      return;
    }
    if (newworld.equals(Core.arena)) {
      if (Core.arenarunning && core.getIsAliveTribute(player)) {
        core.removeFromAlertSystem(player);
      }
      core.addToScoreboard(player);
    }
  }
}
