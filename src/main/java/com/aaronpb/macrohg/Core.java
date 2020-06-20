package com.aaronpb.macrohg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.aaronpb.macrohg.Managers.LuckPermsManager;
import com.aaronpb.macrohg.Managers.VaultManager;
import com.aaronpb.macrohg.Utils.Messages;
import com.aaronpb.macrohg.Utils.TimeFormats;
import com.aaronpb.macrohg.Utils.Utils;

import javafx.util.Pair;

public class Core {

  /* MacrohgCore - VARIABLES */
  // Core variables
  // - global process
  public static boolean arenarunning = false;
  public static boolean borderrunning = false;
  private static BukkitTask maintasktimer, maintaskwaiter;
  private static Scoreboard hgboard;
  private static ArrayList<Pair<District, Score>> scorelist;
  private static Objective objective;
  private static Team maintime, nextborder;
  private static int globaltimer = 0, nextbordertimer;
  private static final List<Pair<Integer, Integer>> bordertimerslist = Arrays
      .asList(new Pair<Integer, Integer>(70, 800),
          new Pair<Integer, Integer>(600, 700),
          new Pair<Integer, Integer>(1200, 650),
          new Pair<Integer, Integer>(1800, 500),
          new Pair<Integer, Integer>(2400, 300),
          new Pair<Integer, Integer>(3000, 100),
          new Pair<Integer, Integer>(3300, 50),
          new Pair<Integer, Integer>(3600, 10));
  // - coundown process
  private static BukkitTask countdowntimer, countdownwaiter;
  private static int countdown = 60;
  // - border process
  private static BukkitTask borderbossbartasktimer, borderbossbartaskwaiter;
  private static BossBar hgbar;
  private static WorldBorder hgborder;
  private static int borderbossbartimer = 60; // 5 and 2 min notification, 1 min
                                              // bossbartimer.
  // Districs info
  private static ArrayList<District> districtlist = new ArrayList<District>();
  // Tributes disconnected
  private static HashMap<Player, Integer> cooldownlist = new HashMap<Player, Integer>();
  // Arena properties
  public static World arena;
  private static int world_cx, world_cz;

  /* MacrohgCore - MAIN CORE */

  private void macrohgCountDown() {
    arenarunning = true;
    countdowntimer = Bukkit.getScheduler().runTaskTimer(Macrohg.plugin, () -> {
      if (countdown > 0) {
        arena.getPlayers().forEach(player -> {
          player.playSound(player.getLocation(),
              Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 1, 0.1f);
          player.sendTitle(Utils.chat("&3&l" + countdown),
              Utils.chat("&6&lMacroHG &eminexilon"), 0, 30, 0);
        });
      }
      countdown--;
    }, 0, 20);

    countdownwaiter = Bukkit.getScheduler().runTaskLater(Macrohg.plugin, () -> {
      countdowntimer.cancel();
      Utils.sendToServerConsole("info", "Countdown timer correctly canceled!");
      macrohgMain();
    }, 60 * 20);
  }

  private void macrohgMain() {
    arenarunning = true;
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.2f,
          0.5f);
      player.sendTitle(Utils.chat("&6&lMacroHG &eminexilon"),
          Utils.chat("&3Que la suerte este de vuestro lado!"), 0, 30, 40);
    });
    arena.strikeLightningEffect(new Location(arena, world_cx, 50, world_cz));

    arena.setTime(0);

//    VaultManager vmng = new VaultManager();
    Messages msgs = new Messages();

    maintasktimer = Bukkit.getScheduler().runTaskTimer(Macrohg.plugin, () -> {

      int prenextbordertimer;
      for (Pair<Integer, Integer> bordernextpair : bordertimerslist) {
        prenextbordertimer = bordernextpair.getKey() - globaltimer;
        if (prenextbordertimer >= 0) {
          switch (prenextbordertimer) {
            case 300:
              msgs.sendGlobalBorderNotif5(arena, bordernextpair.getValue());
              break;
            case 120:
              msgs.sendGlobalBorderNotif2(arena, bordernextpair.getValue());
              break;
            case 60:
              msgs.sendGlobalBorderNotif1(arena, bordernextpair.getValue());
              nextborder.setPrefix(Utils.chat("&4&l ! "));
              break;
            case 0:
              borderrunning = true;
              nextborder.setSuffix(Utils.chat("&cACTIVO"));
              nextborder.setPrefix(Utils.chat("&5&l ! "));
//              vmng.giveMoneyTimeSurvived(districtlist);
              hgborder.setSize(bordernextpair.getValue(), 60);
              bossbarBorderProcess();
              break;
          }
          nextbordertimer = prenextbordertimer;
          break;
        }
      }

      // Update scoreboard timers
      maintime.setSuffix(
          Utils.chat("&f" + TimeFormats.getHourTimeFormatted(globaltimer)));
      if (!borderrunning)
        nextborder.setSuffix(Utils
            .chat("&c" + TimeFormats.getMinuteTimeFormatted(nextbordertimer)));

      globaltimer++;

      // Disconnect system check
      if (!cooldownlist.isEmpty()) {
        for (Player tribute : cooldownlist.keySet()) {
          int cdtime = cooldownlist.get(tribute).intValue();
          if (cdtime == 0) {
            killTribute(tribute, getTributeDistrict(tribute), null);
            cooldownlist.remove(tribute);
          } else {
            cdtime--;
            cooldownlist.replace(tribute, cdtime);
            if (cdtime == 120) {
              msgs.sendGlobalPlayerDisconnect2(arena, tribute.getName());
            } else if (cdtime == 60) {
              msgs.sendGlobalPlayerDisconnect1(arena, tribute.getName());
            }
          }
        }
      }
    }, 0, 20);

    maintaskwaiter = Bukkit.getScheduler().runTaskLater(Macrohg.plugin, () -> {
      // TODO Dejar todo a 0
      globaltimer = 0;
      maintasktimer.cancel();
      Utils.sendToServerConsole("info", "Arena timer correctly canceled!");
    }, 3600 * 20);
  }

  /* MacrohgCore - BORDER PROCESS */

  private void bossbarBorderProcess() {
    hgbar = Bukkit.createBossBar(Utils.chat("&cAvance del Pulso de los Caidos"),
        BarColor.RED, BarStyle.SEGMENTED_6);
    hgbar.setProgress(0);

    arena.getPlayers().forEach(player -> {
      hgbar.addPlayer(player);
      player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.2f,
          0.5f);
      player.sendTitle(Utils.chat("&c&lPulso de los Caidos"),
          Utils.chat("&7¡El borde se esta reduciendo!"), 10, 60, 10);
    });

    arena.setTime(18000);

    borderbossbartasktimer = Bukkit.getScheduler().runTaskTimer(Macrohg.plugin,
        () -> {
          if ((borderbossbartimer % 3) == 0)
            arena.strikeLightningEffect(
                new Location(arena, world_cx, 50, world_cz));

          float incr     = (float) 1 / 60;
          double newProgress = hgbar.getProgress() + incr;
          if (newProgress >= 1) {
            hgbar.setTitle(Utils.chat("&cAvance del Pulso de los Caidos"));
            hgbar.setProgress(1);
          } else {
            hgbar.setTitle(Utils.chat("&cAvance del Pulso de los Caidos &7- &d"
                + borderbossbartimer));
            hgbar.setProgress(newProgress);
          }
          borderbossbartimer--;
        }, 0, 20);

    borderbossbartaskwaiter = Bukkit.getScheduler().runTaskLater(Macrohg.plugin,
        () -> {
          borderbossbartimer = 60;
          borderbossbartasktimer.cancel();
          hgbar.removeAll();
          arena.setTime(0);
          borderrunning = false;
          nextborder.setPrefix("");
          Utils.sendToServerConsole("info",
              "Border bossbar timer correctly canceled!");
        }, 60 * 20);
  }

  /* MacrohgCore - SCOREBOARD */
  public void setupScoreboard() {
    ScoreboardManager m = Macrohg.plugin.getServer().getScoreboardManager();
    hgboard = m.getNewScoreboard();

    objective = hgboard.registerNewObjective("MacroHgInfo", "dummy",
        Utils.chat("&6&lMacroHG"));
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    objective.getScore(Utils.chat("&7play.minexilon.com")).setScore(5);
    ;

    maintime = hgboard.registerNewTeam("maintime");
    maintime.addEntry(Utils.chat("&6Tiempo: "));
    maintime.setSuffix(Utils.chat("&aEn espera"));
    maintime.setPrefix("");
    objective.getScore(Utils.chat("&6Tiempo: ")).setScore(4);
    nextborder = hgboard.registerNewTeam("nextborder");
    nextborder.addEntry(Utils.chat("&6Nuevo pulso: "));
    nextborder.setSuffix(Utils.chat("&7--:--"));
    nextborder.setPrefix("");
    objective.getScore(Utils.chat("&6Nuevo pulso: ")).setScore(3);

    int counter = 0;
    scorelist = new ArrayList<Pair<District, Score>>();

    for (District district : districtlist) {
      Pair<District, Score> newpair = new Pair<District, Score>(district,
          objective.getScore(Utils.chat("&d" + district.getDisctrictName()
              + " &7(&a" + district.getAliveTributes().size() + "&7)")));
      scorelist.add(counter, newpair);
      scorelist.get(counter).getValue()
          .setScore(district.getAliveTributes().size());
      counter++;
    }
  }

  public void updateScoreboard() {
    for (String entry : hgboard.getEntries()) {
      if (objective.getScore(entry).getScore() == 2
          || objective.getScore(entry).getScore() == 1) {
        hgboard.resetScores(entry);
      }
    }

    scorelist = new ArrayList<Pair<District, Score>>();
    int counter = 0;

    for (District district : districtlist) {
      Pair<District, Score> newpair = new Pair<District, Score>(district,
          objective.getScore(Utils.chat("&d" + district.getDisctrictName()
              + " &7(&a" + district.getAliveTributes().size() + "&7)")));
      scorelist.add(counter, newpair);
      scorelist.get(counter).getValue()
          .setScore(district.getAliveTributes().size());
      counter++;
    }
  }

  /* MacrohgCore - Public methods */
  public void addToScoreboard(Player player) {
    player.setScoreboard(hgboard);
  }

  public void removeFromScoreboard(Player player) {
    player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
  }

  public void addToAlertSystem(Player player) {
    Messages msgs = new Messages();
    cooldownlist.put(player, 240);
    msgs.sendGlobalPlayerDisconnect(arena, player.getName());
  }

  public void removeFromAlertSystem(Player player) {
    if (!cooldownlist.isEmpty()) {
      Utils.sendToServerConsole("info",
          "Removing " + player.getName() + " from the cooldownlist.");
      // FIXME It does not remove!
      if (cooldownlist.remove(player) != null) {
        Utils.sendToServerConsole("info", "Successfully removed "
            + player.getName() + " from the cooldownlist.");
      }
    }
  }

  public void killTribute(Player deadtribute, District losedistrict,
      District windistrict) {
    Messages         msgs  = new Messages();
    LuckPermsManager lpmng = new LuckPermsManager();
    VaultManager     vmng  = new VaultManager();

    lpmng.changeToDeadTribute(deadtribute);

    // Arena effects
    arena.strikeLightningEffect(deadtribute.getLocation());
    arena.strikeLightningEffect(deadtribute.getLocation());

    if (windistrict != null) {
      vmng.giveMoneyKill(windistrict);
    }

    if (losedistrict.getAliveTributes().size() == 1) {

      if (windistrict != null) {
        vmng.giveMoneyDistrictKilled(windistrict);
        msgs.sendGlobalAllDistrictKilled(arena, losedistrict.getDisctrictName(),
            windistrict.getDisctrictName());
      } else
        msgs.sendGlobalAllDistrictKilled(arena,
            losedistrict.getDisctrictName());

      if (losedistrict.getTribute1().equals(deadtribute.getName()))
        losedistrict.setTribute1Dead();
      else
        losedistrict.setTribute2Dead();
      // TODO district has been eliminated (scoreboard)
    } else {
      if (losedistrict.getTribute1().equals(deadtribute.getName()))
        losedistrict.setTribute1Dead();
      else
        losedistrict.setTribute2Dead();
    }
  }

  // Administrator live setters
  public void startGame() {
    macrohgCountDown();
  }

  public void directStartGame() {
    macrohgMain();
  }

  public void setArenaTimer(int newtime) {
    globaltimer = newtime;
  }

  public void stopGame() {
    try {
      countdowntimer.cancel();
      countdownwaiter.cancel();
    } catch (Exception e) {
      Utils.sendToServerConsole("warn",
          "Could not cancel countdown tasks (maybe not initialized)");
    }
    try {
      maintasktimer.cancel();
      maintaskwaiter.cancel();
    } catch (Exception e) {
      Utils.sendToServerConsole("warn",
          "Could not cancel main tasks (maybe not initialized)");
    }
    try {
      borderbossbartasktimer.cancel();
      borderbossbartaskwaiter.cancel();
      hgbar.removeAll();
    } catch (Exception e) {
      Utils.sendToServerConsole("warn",
          "Could not cancel borderbossbar tasks or bossbar removal (maybe not initialized)");
    }
    arenarunning = false;
    borderrunning = false;
    borderbossbartimer = 60;
    maintime.setSuffix(Utils.chat("&eEn pausa"));
    nextborder.setSuffix(Utils.chat("&7--:--"));
  }

  // Setters
  public void setDistrictList(ArrayList<District> list) {
    districtlist = list;
  }

  public void setWorldSettings(String worldname, int x, int z) {
    arena = Bukkit.getWorld(worldname);
    Utils.sendToServerConsole("debug", "asdasdasd");
    if (arena == null) {
      Utils.sendToServerConsole("error",
          "World " + worldname + " does not exist in this server!");
      return;
    }
    world_cx = x;
    world_cz = z;
    hgborder = arena.getWorldBorder();
    hgborder.setCenter(x, z);
    Utils.sendToServerConsole("debug", "World settings loaded!");
  }

  // Getters
  public District getTributeDistrict(Player player) {
    for (District district : districtlist) {
      if (district.getAliveTributes().contains(player.getName())) {
        return district;
      }
    }
    return null;
  }

  public boolean getIsAliveTribute(Player player) {
    String playername = player.getName();
    for (District district : districtlist) {
      if (district.getAliveTributes().contains(playername)) {
        return true;
      }
    }
    Utils.sendToServerConsole("debug", playername + " is not a tribute!");
    return false;
  }
}
