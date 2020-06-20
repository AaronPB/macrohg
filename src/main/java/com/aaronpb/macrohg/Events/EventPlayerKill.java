package com.aaronpb.macrohg.Events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.aaronpb.macrohg.Core;
import com.aaronpb.macrohg.District;
import com.aaronpb.macrohg.Utils.Messages;

public class EventPlayerKill implements Listener {

  private Core core = new Core();
  private Messages msgs = new Messages();

  @EventHandler
  public void PlayerKilled(PlayerDeathEvent event) {
    World arena = (World) event.getEntity().getWorld();

    if (!arena.equals(Core.arena)) {
      return;
    }

    Player deadtribute = (Player) event.getEntity();
    Player killer      = (Player) event.getEntity().getKiller();

    if (deadtribute == null) {
      return;
    }

    District losedistrict = core.getTributeDistrict(deadtribute);
    
    // FIXME Throws error null pointer
    if (!losedistrict.getAliveTributes().contains(deadtribute.getName())) {
      return;
    }

    District windistrict = null;

    if (killer == null) {
      msgs.sendGlobalAllDistrictKilled(arena, losedistrict.getDisctrictName());
    } else {
      msgs.sendGlobalAllDistrictKilled(arena, losedistrict.getDisctrictName(),
          killer.getName());
      windistrict = core.getTributeDistrict(killer);
    }

    core.killTribute(deadtribute, losedistrict, windistrict);
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
