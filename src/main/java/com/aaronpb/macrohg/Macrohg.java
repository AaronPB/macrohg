package com.aaronpb.macrohg;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.aaronpb.macrohg.Commands.AdminCommands;
import com.aaronpb.macrohg.Events.EventArenaAccess;
import com.aaronpb.macrohg.Events.EventPlayerConnections;
import com.aaronpb.macrohg.Events.EventPlayerKill;
import com.aaronpb.macrohg.Utils.Utils;

import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;

public class Macrohg extends JavaPlugin {

  public static Macrohg plugin;
  public static LuckPerms luckPerms;
  public static Economy economy;

  private ConfigManager cfgm = new ConfigManager();
  private AdminCommands admincmds = new AdminCommands();

  // Initiate plugin
  @Override
  public void onEnable() {
    plugin = this;
    Utils.sendToServerConsole("info", "Checking Veteranias's dependencies...");
    if (!setupLuckPerms()) {
      Utils.sendToServerConsole("error",
          "Disabled due to no LuckPerms dependency found!");
      getServer().getPluginManager().disablePlugin(plugin);
      return;
    }
    Utils.sendToServerConsole("info", "Hoocked successfully into LuckPerms");
    if (!setupEconomy()) {
      Utils.sendToServerConsole("error",
          "Disabled due to no Vault dependency found!");
      getServer().getPluginManager().disablePlugin(plugin);
      return;
    }
    Utils.sendToServerConsole("info", "Hoocked successfully into Vault");

    cfgm.setup();

    getCommand(admincmds.maincmd).setExecutor(admincmds);

    getServer().getPluginManager().registerEvents(new EventArenaAccess(),
        plugin);
    getServer().getPluginManager().registerEvents(new EventPlayerConnections(),
        plugin);
    getServer().getPluginManager().registerEvents(new EventPlayerKill(),
        plugin);

    Utils.sendToServerConsole("info", "Loaded successfully!");
  }

  // Disable plugin
  @Override
  public void onDisable() {
//   ConfigManager.configloaded = false;
//   closePlayerInvs();
    Utils.sendToServerConsole("info",
        "The plugin macrohg has been correctly disabled!");
    plugin = null;
  }

  // Setup dependencies
  private boolean setupLuckPerms() {
    if (getServer().getPluginManager().getPlugin("LuckPerms") == null) {
      return false;
    }
    RegisteredServiceProvider<LuckPerms> luckpermsProvider = Bukkit
        .getServicesManager().getRegistration(LuckPerms.class);
    if (luckpermsProvider == null) {
      return false;
    }

    luckPerms = luckpermsProvider.getProvider();
    return luckPerms != null;
  }

  private boolean setupEconomy() {
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      return false;
    }
    RegisteredServiceProvider<Economy> vaultProvider = getServer()
        .getServicesManager().getRegistration(Economy.class);
    if (vaultProvider == null) {
      return false;
    }
    economy = vaultProvider.getProvider();
    return economy != null;
  }

}
