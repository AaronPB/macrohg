package com.aaronpb.macrohg.Events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.aaronpb.macrohg.Core;
import com.aaronpb.macrohg.Utils.Utils;

public class EventPlayerConnections implements Listener {
  private Core core = new Core();

  @EventHandler
  public void PlayerJoinServer(PlayerJoinEvent event) {
    Player player = (Player) event.getPlayer();

    if (player == null) {
      return;
    }

    World world = player.getWorld();

    if (world.equals(Core.arena)) {
      Utils.sendToServerConsole("debug",
          "Adding " + player.getName() + " to the macrohg scoreboard.");
      core.addToScoreboard(player);
      if (Core.arenarunning && core.getIsAliveTribute(player)) {
        Utils.sendToServerConsole("debug", player.getName()
            + " is a disconnected tribute! Removing from alertsystem...");
        core.removeFromAlertSystem(player);
      }
    } else if (core.getIsAliveTribute(player)) {
      // TODO Send message to join arena world!!
    }
  }

  @EventHandler
  public void PlayerLeaveServer(PlayerQuitEvent event) {
    Player player = (Player) event.getPlayer();

    if (player == null) {
      return;
    }

    if (Core.arenarunning && core.getIsAliveTribute(player)) {
      core.addToAlertSystem(player);
    }
  }
}
