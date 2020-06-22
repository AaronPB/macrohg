package com.aaronpb.macrohg;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.aaronpb.macrohg.Managers.LuckPermsManager;
import com.aaronpb.macrohg.Managers.VaultManager;
import com.aaronpb.macrohg.Utils.Utils;

public class ConfigManager {

  private Core core = new Core();
  private LuckPermsManager lpmng = new LuckPermsManager();
  private VaultManager vmng = new VaultManager();

  // File configs
  public FileConfiguration config;
  public File configfile;

  // Public config params
  public static boolean debugmode;

  public void setup() {
    if (!Macrohg.plugin.getDataFolder().exists()) {
      Macrohg.plugin.getDataFolder().mkdir();
    }

    configfile = new File(Macrohg.plugin.getDataFolder(), "config.yml");

    if (!configfile.exists()) {
      Macrohg.plugin.getConfig().options().copyDefaults(true);
      Macrohg.plugin.saveDefaultConfig();
      Utils.sendToServerConsole("info",
          "The config file has been correctly generated!");
    } else {
      Utils.sendToServerConsole("info",
          "The config file has been detected. No need to generate it.");
    }
    try {
      loadConfigParams();
    } catch (NullPointerException e) {
      Utils.sendToServerConsole("error",
          "Could not read all the config params correctly."
              + " Correct mistakes in file and reload again!!");
    }

    // Setup scoreboard in world
    core.setupScoreboard();
  }

  public void reload() {
    config = Macrohg.plugin.getConfig();
    configfile = new File(Macrohg.plugin.getDataFolder(), "config.yml");
    if (!configfile.exists()) {
      config.options().copyDefaults(true);
      Macrohg.plugin.saveDefaultConfig();
      Utils.sendToServerConsole("warn",
          "The config file does not exist into de plugin folder."
              + " It has been generated again with default params!");
    }
    try {
      loadConfigParams();
      Utils.sendToServerConsole("info",
          "The config has been reloaded correctly!");
    } catch (NullPointerException e) {
      Utils.sendToServerConsole("error",
          "Could not read all the config params correctly."
              + " Correct mistakes in file and reload again!!");
    }

    // Set predefined values
    core.setArenaTimer(0);
    // Setup Scoreboard
    core.setupScoreboard();

    // Update scoreboard to all users in world
    Core.arena.getPlayers().forEach(player -> {
      core.removeFromScoreboard(player);
      core.addToScoreboard(player);
    });

  }

  public void loadConfigParams() throws NullPointerException {

    config = YamlConfiguration.loadConfiguration(configfile);

    Utils.sendToServerConsole("info", "Loading config params...");

    // Debugmode
    if (config.isSet("debugmode")) {
      debugmode = config.getBoolean("debugmode");
      Utils.sendToServerConsole("info", "[debugmode] set to " + debugmode);
    } else {
      debugmode = false;
      Utils.sendToServerConsole("warn",
          "[debugmode] Not found in config. Setting to false");
    }

    // Luckperms group ranks
    String tributegroup = "tributo", deadtributegroup = "deadtributegroup";
    if (config.isSet("tributegroup")) {
      tributegroup = config.getString("tributegroup");
      Utils.sendToServerConsole("info",
          "[tributegroup] set to " + tributegroup);
    } else {
      Utils.sendToServerConsole("warn",
          "[tributegroup] Not found in config. Setting to tributo");
    }
    if (config.isSet("deadtributegroup")) {
      deadtributegroup = config.getString("deadtributegroup");
      Utils.sendToServerConsole("info",
          "[deadtributegroup] set to " + deadtributegroup);
    } else {
      Utils.sendToServerConsole("warn",
          "[spectatorgroup] Not found in config. Setting to deadtributegroup");
    }

    Utils.sendToServerConsole("info", "Luckperms settings loaded.");
    lpmng.setGroups(tributegroup, deadtributegroup);

    // Districts
    ArrayList<District> districtlist = new ArrayList<District>();
    District            setdistrict;

    for (String district : config.getConfigurationSection("districts")
        .getKeys(false)) {
      String  path         = "districts." + district;
      boolean mentor_set   = config.isSet(path + ".mentor");
      boolean tribute1_set = config.isSet(path + ".tribute1");
      boolean tribute2_set = config.isSet(path + ".tribute2");

      if (tribute1_set) {
        if (mentor_set && tribute2_set) {
          setdistrict = new District(district,
              config.getString(path + ".mentor"),
              config.getString(path + ".tribute1"),
              config.getString(path + ".tribute2"));
        } else if (mentor_set) {
          setdistrict = new District(district,
              config.getString(path + ".mentor"),
              config.getString(path + ".tribute1"));
        } else {
          setdistrict = new District(district,
              config.getString(path + ".tribute1"));
        }

        districtlist.add(setdistrict);

        Utils.sendToServerConsole("info",
            "[District " + district + "] correctly loaded: " + setdistrict);
      } else {
        Utils.sendToServerConsole("warn", "[District " + district
            + "] could not been loaded becuase it has no tribute1!");
      }
    }
    Utils.sendToServerConsole("info", "District list loaded: " + districtlist);
    core.setDistrictList(districtlist);

    // Area main settings
    String arenaname = "world";
    int    world_cx  = 0, world_cz = 0;

    if (config.isSet("world_name")) {
      arenaname = config.getString("world_name");
      Utils.sendToServerConsole("info", "[arenaname] set to " + arenaname);
    } else {
      Utils.sendToServerConsole("warn",
          "[arenaname] Not found in config. Setting to world");
    }

    if (config.isSet("world_center_x")) {
      world_cx = config.getInt("world_center_x");
      Utils.sendToServerConsole("info", "[world_cx] set to " + world_cx);
    } else {
      Utils.sendToServerConsole("warn",
          "[world_cx] Not found in config. Setting to 0");
    }
    if (config.isSet("world_center_z")) {
      world_cz = config.getInt("world_center_z");
      Utils.sendToServerConsole("info", "[world_cz] set to " + world_cz);
    } else {
      Utils.sendToServerConsole("warn",
          "[world_cz] Not found in config. Setting to 0");
    }
    Utils.sendToServerConsole("info", "Arena details loaded.");
    core.setWorldSettings(arenaname, world_cx, world_cz);

    // Economy settings
    int tributekill = 20, timesurvived = 5, allteamkilled = 5;
    if (config.isSet("money_tributekill")) {
      tributekill = config.getInt("money_tributekill");
      Utils.sendToServerConsole("info", "[tributekill] set to " + tributekill);
    } else {
      Utils.sendToServerConsole("warn",
          "[tributekill] Not found in config. Setting to 20");
    }
    if (config.isSet("money_timesurvived")) {
      timesurvived = config.getInt("money_timesurvived");
      Utils.sendToServerConsole("info",
          "[timesurvived] set to " + timesurvived);
    } else {
      Utils.sendToServerConsole("warn",
          "[timesurvived] Not found in config. Setting to 5");
    }
    if (config.isSet("money_allteamkilled")) {
      allteamkilled = config.getInt("money_allteamkilled");
      Utils.sendToServerConsole("info",
          "[allteamkilled] set to " + allteamkilled);
    } else {
      Utils.sendToServerConsole("warn",
          "[allteamkilled] Not found in config. Setting to 5");
    }

    Utils.sendToServerConsole("info", "Economy details loaded.");
    vmng.setEconSettings(tributekill, timesurvived, allteamkilled);

  }

}
