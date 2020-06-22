package com.aaronpb.macrohg.Events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.aaronpb.macrohg.Core;
import com.aaronpb.macrohg.Macrohg;
import com.aaronpb.macrohg.Utils.Messages;
import com.aaronpb.macrohg.Utils.Utils;

public class EventPlayerConnections implements Listener {
  private Core core = new Core();
  private Messages msgs = new Messages();

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
      if (!core.getIsAliveTribute(player.getName())) {
        Utils.sendToServerConsole("debug",
            "Setting spectator mode to " + player.getName());
        Bukkit.getScheduler().runTaskLater(Macrohg.plugin, new Runnable() {
          @Override
          public void run() {
            player.setGameMode(GameMode.SPECTATOR);
            msgs.sendSpectatorHelpMsgs(player);
          }
        }, 3L);
      }
      if (Core.arenarunning && core.getIsAliveTribute(player.getName())) {
        Utils.sendToServerConsole("debug", player.getName()
            + " is a disconnected tribute! Removing from alertsystem...");
        core.removeFromAlertSystem(player.getName());
      }
    } else if (core.getIsAliveTribute(player.getName())) {
      // TODO Send message to join arena world!!
    }
  }

  @EventHandler
  public void PlayerLeaveServer(PlayerQuitEvent event) {
    Player player = (Player) event.getPlayer();

    if (player == null) {
      return;
    }

    if (Core.arenarunning && core.getIsAliveTribute(player.getName())) {
      if (core.getAllAliveTributes() > 5) {
        core.addToAlertSystem(player.getName());
      } else {
        msgs.sendGlobalSuddenDeathMsg(Core.arena, player.getName(),
            core.getTributeDistrict(player.getName()).getDisctrictName());
        core.killTribute(player, core.getTributeDistrict(player.getName()),
            null);
      }
    }
  }
}
