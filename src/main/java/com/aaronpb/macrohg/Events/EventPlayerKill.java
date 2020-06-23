package com.aaronpb.macrohg.Events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import com.aaronpb.macrohg.Core;
import com.aaronpb.macrohg.District;
import com.aaronpb.macrohg.Macrohg;
import com.aaronpb.macrohg.Utils.Messages;
import com.aaronpb.macrohg.Utils.Utils;

public class EventPlayerKill implements Listener {

  private Core core = new Core();
  private Messages msgs = new Messages();

  @EventHandler
  public void PlayerGetDamage(EntityDamageEvent event) {
    World arena = (World) event.getEntity().getWorld();

    if (!Core.arenarunning) {
      return;
    }

    if (!arena.equals(Core.arena)) {
      return;
    }
    if (!(event.getEntity() instanceof Player)) {
      return;
    }

    if (event.getCause().equals(DamageCause.ENTITY_ATTACK)
        || event.getCause().equals(DamageCause.ENTITY_SWEEP_ATTACK)
        || event.getCause().equals(DamageCause.PROJECTILE)) {
      Utils.sendToServerConsole("debug",
          "Ignoring proceed because it is an entity single, sweep or projectile attack.");
      return;
    }

    Player deadtribute = (Player) event.getEntity();
    if (deadtribute == null) {
      return;
    }
    Utils.sendToServerConsole("debug", "PlayerGetDamage - Running!");
    if (!core.getIsAliveTribute(deadtribute.getName())) {
      Utils.sendToServerConsole("info", deadtribute.getName()
          + " is not an alive tribute. Making it a spectator anyway ...");
      deadtribute.setGameMode(GameMode.SPECTATOR);
      return;
    }

    if (deadtribute.getHealth() - event.getDamage() < 1) {
      event.setCancelled(true);
      for (ItemStack itemStack : deadtribute.getInventory().getContents()) {
        if (itemStack != null) {
          arena.dropItemNaturally(deadtribute.getLocation(), itemStack);
        }
      }
      for (ItemStack itemStack : deadtribute.getInventory()
          .getArmorContents()) {
        if (itemStack != null) {
          arena.dropItemNaturally(deadtribute.getLocation(), itemStack);
        }
      }
      deadtribute.getInventory().clear();
      deadtribute.getInventory().setArmorContents(new ItemStack[4]);
      deadtribute.setGameMode(GameMode.SPECTATOR);

      District losedistrict = core.getTributeDistrict(deadtribute.getName());
      msgs.sendGlobalTributeKillMsg(arena, deadtribute.getName(),
          losedistrict.getDisctrictName());
      Bukkit.getScheduler().runTaskLater(Macrohg.plugin, () -> {
        msgs.sendSpectatorHelpMsgs(deadtribute);
      }, 40);
      core.killTribute(deadtribute, losedistrict, null);
    }
  }

  @EventHandler
  public void PlayerGetDamageByEntity(EntityDamageByEntityEvent event) {
    World arena = (World) event.getEntity().getWorld();

    if (!Core.arenarunning) {
      return;
    }

    if (!arena.equals(Core.arena)) {
      return;
    }
    if (!(event.getEntity() instanceof Player)) {
      return;
    }

    Player deadtribute = (Player) event.getEntity();
    if (deadtribute == null) {
      return;
    }
    Utils.sendToServerConsole("debug", "PlayerGetDamageByEntity - Running!");
    if (!core.getIsAliveTribute(deadtribute.getName())) {
      Utils.sendToServerConsole("info", deadtribute.getName()
          + " is not an alive tribute. Making it a spectator anyway ...");
      deadtribute.setGameMode(GameMode.SPECTATOR);
      return;
    }

    if (deadtribute.getHealth() - event.getDamage() < 1) {
      event.setCancelled(true);
      for (ItemStack itemStack : deadtribute.getInventory().getContents()) {
        if (itemStack != null) {
          arena.dropItemNaturally(deadtribute.getLocation(), itemStack);
        }
      }
      for (ItemStack itemStack : deadtribute.getInventory()
          .getArmorContents()) {
        if (itemStack != null) {
          arena.dropItemNaturally(deadtribute.getLocation(), itemStack);
        }
      }
      deadtribute.getInventory().clear();
      deadtribute.getInventory().setArmorContents(new ItemStack[4]);
      deadtribute.setGameMode(GameMode.SPECTATOR);

      District losedistrict = core.getTributeDistrict(deadtribute.getName());
      District windistrict  = null;
      if (!(event.getDamager() instanceof Player)) {
        Utils.sendToServerConsole("debug",
            "Killer is not a player! Preforming normal kill.");
        msgs.sendGlobalTributeKillMsg(arena, deadtribute.getName(),
            losedistrict.getDisctrictName());
        Bukkit.getScheduler().runTaskLater(Macrohg.plugin, () -> {
          msgs.sendSpectatorHelpMsgs(deadtribute);
        }, 40);
        core.killTribute(deadtribute, losedistrict, null);
        return;
      }
      Player killer = (Player) event.getDamager();
      if (killer == null) {
        msgs.sendGlobalTributeKillMsg(arena, deadtribute.getName(),
            losedistrict.getDisctrictName());
      } else {
        windistrict = core.getTributeDistrict(killer.getName());
        msgs.sendGlobalTributeKillMsg(arena, deadtribute.getName(),
            losedistrict.getDisctrictName(), killer.getName(),
            windistrict.getDisctrictName());
      }

      Bukkit.getScheduler().runTaskLater(Macrohg.plugin, () -> {
        msgs.sendSpectatorHelpMsgs(deadtribute);
      }, 40);
      core.killTribute(deadtribute, losedistrict, windistrict);
    }
  }
}
