package com.aaronpb.macrohg.Events;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.aaronpb.macrohg.Core;
import com.aaronpb.macrohg.District;
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

    Player deadtribute = (Player) event.getEntity();
    if (deadtribute == null) {
      return;
    }
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
      deadtribute.setGameMode(GameMode.SPECTATOR);
      District losedistrict = core.getTributeDistrict(deadtribute.getName());
      msgs.sendGlobalTributeKillMsg(arena, deadtribute.getName(),
          losedistrict.getDisctrictName());
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
      deadtribute.setGameMode(GameMode.SPECTATOR);

      District losedistrict = core.getTributeDistrict(deadtribute.getName());
      District windistrict  = null;
      Player   killer       = (Player) event.getDamager();
      if (killer == null) {
        msgs.sendGlobalTributeKillMsg(arena, deadtribute.getName(),
            losedistrict.getDisctrictName());
      } else {
        windistrict = core.getTributeDistrict(killer.getName());
        msgs.sendGlobalTributeKillMsg(arena, deadtribute.getName(),
            losedistrict.getDisctrictName(), killer.getName(),
            windistrict.getDisctrictName());
      }

      core.killTribute(deadtribute, losedistrict, windistrict);
    }
  }

//  private void killEffects(Player dead, District district, World arena) {
//    Bukkit.getOnlinePlayers().forEach(player -> {
//      if (player.getWorld().equals(Core.arena)) {
//        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_DEATH, 1,
//            0.5f);
//        player.sendTitle(Utils.chat("&c\1f480 " + dead.getName() + " \1f480"),
//            Utils.chat(
//                "&7Tributo fallecido de &3" + core.getTributeDistrict(dead)),
//            5, 25, 20);
//      }
//    });
//
//    // Arena effects
//    arena.strikeLightningEffect(dead.getLocation());
//    arena.strikeLightningEffect(dead.getLocation());
//
//    long arenatime = arena.getTime();
//    timer = Bukkit.getScheduler().runTaskTimer(Macrohg.plugin, () -> {
//      if(arenatime < 9000 || arenatime > 23900) {
//        arena.setTime(18000);
//      }else {
//        arena.setTime(6000);
//      }
//    }, 7, 30);
//    waiter = Bukkit.getScheduler().runTaskLater(Macrohg.plugin, () -> {
//      timer.cancel();
//      arena.setTime(arenatime);
//    }, 30);
//  }
}
