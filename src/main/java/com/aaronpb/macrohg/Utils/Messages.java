package com.aaronpb.macrohg.Utils;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Messages {

  private static String MacrohgTag = Utils.chat("&6&l[&e&lMacroHG&6&l] ");

  // Global kill messages

  public void sendGlobalTributeKillMsg(World arena, String dead,
      String districtdead) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 0.7f,
          0.8f);
      player.sendMessage(MacrohgTag + Utils.chat("&c[&4MUERTE&c] &c&l" + dead
          + "&3 de la ciudad&b " + districtdead + "&3 ha fallecido!"));
    });
  }

  public void sendGlobalTributeKillMsg(World arena, String dead,
      String districtdead, String killer, String districtkiller) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 0.7f,
          0.8f);
      player.sendMessage(MacrohgTag + Utils.chat("&c[&4MUERTE&c] &c&l" + dead
          + "&3 de la ciudad&b " + districtdead + "&3 ha muerto a manos de&6&l "
          + killer + "&3 de la ciudad&b " + districtkiller));
    });
  }

  public void sendGlobalAllDistrictKilled(World arena, String district) {
    arena.getPlayers()
        .forEach(player -> player.sendMessage(
            MacrohgTag + Utils.chat("&c[&d&mDISTRITO&c] &3La ciudad&c "
                + district + "&3 ha sido eliminada!")));
  }

  public void sendGlobalAllDistrictKilled(World arena, String district,
      String districtkiller) {
    arena.getPlayers()
        .forEach(player -> player.sendMessage(MacrohgTag
            + Utils.chat("&c[&d&mDISTRITO&c] &3La ciudad&c " + district
                + "&3 ha sido eliminada por la ciudad&b " + districtkiller)));
  }

  // Global worldborder messages

  public void sendGlobalBorderNotif5(World arena, int dimensions) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1, 1);
      player.sendMessage(MacrohgTag + Utils
          .chat("&c[&cBORDE&c] &3El borde se va a reducir a&b " + dimensions
              + "x" + dimensions + " bloques &3dentro de &b5 minutos&3!"));
    });
  }

  public void sendGlobalBorderNotif2(World arena, int dimensions) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1, 1);
      player.sendMessage(MacrohgTag + Utils
          .chat("&c[&cBORDE&c] &3El borde se va a reducir a&b " + dimensions
              + "x" + dimensions + " bloques &3dentro de &b2 minutos&3!"));
    });
  }

  public void sendGlobalBorderNotif1(World arena, int dimensions) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1, 1);
      player.sendMessage(MacrohgTag + Utils
          .chat("&c[&cBORDE&c] &3El borde se va a reducir a&b " + dimensions
              + "x" + dimensions + " bloques &3dentro de &c1 minuto&3!"));
    });
  }

  public void sendGlobalBorderNotifActive(World arena, int dimensions) {
    arena.getPlayers()
        .forEach(player -> player.sendMessage(MacrohgTag
            + Utils.chat("&c[&cBORDE&c] &3El borde se esta reduciendo a&b "
                + dimensions + "x" + dimensions + " bloques&3!")));
  }

  // Global inactive warn messages
  public void sendGlobalPlayerDisconnect(World arena, String afktribute) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON,
          1, 0.8f);
      player.sendMessage(MacrohgTag
          + Utils.chat("&c[&5DESCONEX&c] &3El tributo &7 " + afktribute
              + " &3se ha desconectado! Tiene &b4 minutos &3para evitar el pulso de muerte subita."));
    });
  }

  public void sendGlobalPlayerDisconnect2(World arena, String afktribute) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON,
          1, 0.8f);
      player.sendMessage(MacrohgTag
          + Utils.chat("&c[&5DESCONEX&c] &3El tributo &7 " + afktribute
              + " &3tiene &b2 minutos &3para evitar el pulso de muerte subita."));
    });
  }

  public void sendGlobalPlayerDisconnect1(World arena, String afktribute) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON,
          1, 0.8f);
      player.sendMessage(MacrohgTag
          + Utils.chat("&c[&5DESCONEX&c] &3El tributo &7 " + afktribute
              + " &3tiene &c1 minuto &3para evitar el pulso de muerte subita."));
    });
  }

  // Private mentor messages
  public void sendMentorMoneyNotif(Player mentor, int amount) {
    mentor.sendMessage(
        MacrohgTag + Utils.chat("&7[&dMENTOR&7] &3Has recibido&e " + amount
            + " lagrimas de sangre &3para gastar en la tienda de tus tributos!\n&3Accede via &d/tienda"));
  }

  public void sendMentorAllKilledNotif(Player mentor, String district) {
    mentor.sendMessage(MacrohgTag + Utils.chat(
        "&7[&dMENTOR&7] &3La ciudad a la que representas ha sido eliminada del evento!"
            + "\n&3Aun asi puedes apoyar con las lagrimas de sangre sobrantes a otros tributos!\n&3Accede via &d/tienda"));
  }

  // Private tribute messages

  // Private spectator messages
  public void sendSpectatorHelpMsgs(Player spectator) {
    spectator.sendMessage(MacrohgTag + Utils.chat("&9[&bINFO&9]&3 Hola "
        + spectator.getName()
        + "&3! Eres un espectador de los macrojuegos.\n&7Info de como usar el modo espectador:"));
    spectator.sendMessage(Utils.chat("&7- &bEspectar a un tributo: &fPulsa 1 y selecciona del 1 al 9 el tributo que deseas espectar"));
    spectator.sendMessage(Utils.chat("&7- &bSalir de la vista: &fUsa el shift"));
    spectator.sendMessage(Utils.chat("&7- &bSalir de los MacroHG: &fUsa el comando /lobby"));
  }

}
