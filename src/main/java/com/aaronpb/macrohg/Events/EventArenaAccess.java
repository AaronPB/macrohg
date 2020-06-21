package com.aaronpb.macrohg.Events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.aaronpb.macrohg.Core;
import com.aaronpb.macrohg.Macrohg;
import com.aaronpb.macrohg.Utils.Messages;
import com.aaronpb.macrohg.Utils.Utils;

public class EventArenaAccess implements Listener {
  private Core core = new Core();
  private Messages msgs = new Messages();

  @EventHandler
  public void PlayerMovedFromWorld(PlayerChangedWorldEvent event) {
    World  previousworld = (World) event.getFrom();
    Player player        = (Player) event.getPlayer();
    World  newworld      = player.getWorld();

    if (!previousworld.equals(Core.arena) && !newworld.equals(Core.arena)) {
      return;
    }

    if (previousworld.equals(Core.arena)) {
      if (Core.arenarunning && core.getIsAliveTribute(player.getName())) {
        if (core.getAllAliveTributes() > 5) {
          core.addToAlertSystem(player.getName());
        } else {
          core.killTribute(player, core.getTributeDistrict(player.getName()),
              null);
        }

      }
      core.removeFromScoreboard(player);
      if (Core.borderrunning) {
        core.removeFromBossbar(player);
      }
      return;
    }
    if (newworld.equals(Core.arena)) {
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
        core.removeFromAlertSystem(player.getName());
      }
      if (Core.borderrunning) {
        core.addToBossbar(player);
      }
      core.addToScoreboard(player);
    }
  }
}
