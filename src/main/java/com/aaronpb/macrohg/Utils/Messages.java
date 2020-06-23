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
      player.sendMessage(MacrohgTag + Utils.chat("&4[&cMUERTO&4] &c&l" + dead
          + "&3 de la ciudad&b " + districtdead + "&3 ha fallecido!"));
    });
  }

  public void sendGlobalTributeKillMsg(World arena, String dead,
      String districtdead, String killer, String districtkiller) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 0.7f,
          0.8f);
      player.sendMessage(MacrohgTag + Utils.chat("&4[&cMUERTO&4] &c&l" + dead
          + "&3 de la ciudad&b " + districtdead + "&3 ha muerto a manos de&6&l "
          + killer + "&3 de la ciudad&b " + districtkiller));
    });
  }

  public void sendGlobalSuddenDeathWarning(World arena) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 0.7f,
          0.8f);
      player.sendMessage(MacrohgTag + Utils.chat(
          "&4[&cMUERTE SUBITA&4] &3Al quedar 5 o menos tributos, cualquier tipo de abandono de la arena, supondra &cmuerte subita&3!"));
    });
  }

  public void sendGlobalSuddenDeathMsg(World arena, String dead,
      String districtdead) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 0.3f,
          0.8f);
      player.sendMessage(MacrohgTag + Utils.chat("&4[&cMUERTE SUBITA&4] &c&l"
          + dead + "&3 de la ciudad&b " + districtdead
          + "&3 ha fallecido por &cmuerte subita&3! &7(Inactividad)"));
    });
  }

  public void sendGlobalAllDistrictKilled(World arena, String district) {
    arena.getPlayers()
        .forEach(player -> player.sendMessage(
            MacrohgTag + Utils.chat("&5[&d&mDISTRITO&5] &3La ciudad&c "
                + district + "&3 ha sido eliminada!")));
  }

  public void sendGlobalAllDistrictKilled(World arena, String district,
      String districtkiller) {
    arena.getPlayers().forEach(player -> {
      player.sendMessage(
          MacrohgTag + Utils.chat("&5[&d&mDISTRITO&5] &3La ciudad&c " + district
              + "&3 ha sido eliminada por la ciudad&b " + districtkiller));
    });
  }

  // Global revive messages

  public void sendGlobalTributeRevived(World arena, String revived,
      String districtrevived) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 0.7f,
          0.8f);
      player
          .sendMessage(MacrohgTag + Utils.chat("&2[&aREVIDIDO&2] &a&l" + revived
              + "&3 de la ciudad&b " + districtrevived + "&3 ha revivido!"));
    });
  }

  // Global worldborder messages

  public void sendGlobalBorderNotif5(World arena, int dimensions) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1, 1);
      player.sendMessage(MacrohgTag + Utils
          .chat("&4[&cBORDE&4] &3El borde se va a reducir a&b " + dimensions
              + "x" + dimensions + " bloques &3dentro de &b5 minutos&3!"));
    });
  }

  public void sendGlobalBorderNotif2(World arena, int dimensions) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1, 1);
      player.sendMessage(MacrohgTag + Utils
          .chat("&4[&cBORDE&4] &3El borde se va a reducir a&b " + dimensions
              + "x" + dimensions + " bloques &3dentro de &b2 minutos&3!"));
    });
  }

  public void sendGlobalBorderNotif1(World arena, int dimensions) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1, 1);
      player.sendMessage(MacrohgTag + Utils
          .chat("&4[&cBORDE&4] &3El borde se va a reducir a&b " + dimensions
              + "x" + dimensions + " bloques &3dentro de &c1 minuto&3!"));
    });
  }

  public void sendGlobalBorderNotifActive(World arena, int dimensions) {
    arena.getPlayers()
        .forEach(player -> player.sendMessage(MacrohgTag
            + Utils.chat("&4[&cBORDE&4] &3El borde se esta reduciendo a&b "
                + dimensions + "x" + dimensions + " bloques&3!")));
  }

  // Global inactive warn messages
  public void sendGlobalPlayerDisconnect(World arena, String afktribute) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON,
          1, 0.8f);
      player.sendMessage(MacrohgTag
          + Utils.chat("&8[&7DESCONEX&8] &3El tributo &7" + afktribute
              + " &3se ha desconectado! Tiene &b4 minutos &3para evitar el pulso de muerte subita."));
    });
  }

  public void sendGlobalPlayerDisconnect2(World arena, String afktribute) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON,
          1, 0.8f);
      player.sendMessage(MacrohgTag
          + Utils.chat("&8[&7DESCONEX&8] &3El tributo &7" + afktribute
              + " &3tiene &b2 minutos &3para evitar el pulso de muerte subita."));
    });
  }

  public void sendGlobalPlayerDisconnect1(World arena, String afktribute) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON,
          1, 0.8f);
      player.sendMessage(MacrohgTag
          + Utils.chat("&8[&7DESCONEX&8] &3El tributo &7" + afktribute
              + " &3tiene &c1 minuto &3para evitar el pulso de muerte subita."));
    });
  }

  // Global victory message
  public void sendGlobalVictory(World arena, String winner,
      String districtwinner) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE,
          1, 0.5f);
      player.sendMessage(MacrohgTag + Utils.chat("&2[&a&lGANADOR&2] &a&l"
          + winner + "&3 de la ciudad&b " + districtwinner
          + "&3 ha ganado la &6Tercera Edición de los Macrojuegos del Hambre&3!!!"));
    });
  }

  // Global economy messages
  public void sendGlobalSurvivedMoneyNotif(World arena, int amount) {
    arena.getPlayers().forEach(player -> {
      player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
          0.3f, 0.5f);
      player.sendMessage(MacrohgTag
          + Utils.chat("&6[&eLAGRIMAS&6] &3Se ha entregado a los mentores &c&l"
              + amount + " &clagrimas de sangre &3por tributo vivo!"));
    });
  }

  public void sendGlobalTributeKillMoneyNotif(World arena,
      String districtkiller, int amount) {
    arena.getPlayers().forEach(player -> {
      player.sendMessage(MacrohgTag
          + Utils.chat("&6[&eLAGRIMAS&6] &3Se ha entregado &c&l" + amount
              + " &clagrimas de sangre &3al mentor de &b" + districtkiller));
    });
  }

  public void sendGlobalAllDistrictKilledMoneyNotif(World arena,
      String districtkiller, int amount) {
    arena.getPlayers().forEach(player -> {
      player.sendMessage(MacrohgTag
          + Utils.chat("&6[&eLAGRIMAS&6] &3Se ha entregado &c&l" + amount
              + " &clagrimas de sangre &3al mentor de &b" + districtkiller));
    });
  }

  // Private mentor messages
  public void sendMentorMoneyNotif(Player mentor, int amount) {
    mentor.sendMessage(
        MacrohgTag + Utils.chat("&9[&bMENTOR&9] &3Has recibido&e " + amount
            + " lagrimas de sangre &3para gastar en la tienda!\n&3Accede via &d/tienda"));
  }

  public void sendMentorAllKilledNotif(Player mentor, String district) {
    mentor.sendMessage(MacrohgTag + Utils.chat(
        "&9[&bMENTOR&9] &3La ciudad a la que representas ha sido eliminada del evento!"
            + "\n&3Aun asi puedes apoyar con las lagrimas de sangre sobrantes a otros tributos!\n&3Accede via &d/tienda"));
  }

  // Private tribute messages
  public void sendTributeSlowFallingMsg(Player player) {
    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
        1, 0.5f);
    player.sendMessage(MacrohgTag + Utils.chat(
        "&9[&bINFO&9]&3 Tienes activado &b30 SEGUNDOS &3de &bCAIDA LENTA &3para bajar del pilar sin hacerte daño!"));
  }

  // Private spectator messages
  public void sendSpectatorHelpMsgs(Player spectator) {
    spectator.sendMessage(MacrohgTag + Utils.chat("&9[&bINFO&9]&3 Hola "
        + spectator.getName()
        + "&3! Eres un espectador de los macrojuegos.\n&7Info de como usar el modo espectador:"));
    spectator.sendMessage(Utils.chat(
        "&7- &bEspectar a un tributo: &fPulsa 1 para abrir el panel de control, y pulsa 1 de nuevo para desplegar el menu de jugadores a espectar"));
    spectator.sendMessage(Utils.chat(
        "&7- &bAumenta tu velocidad: &fUsa la rueda del raton para modificar tu velocidad de vuelo"));
    spectator.sendMessage(Utils.chat(
        "&7- &bEntrar en la vista del tributo: &fHaz click izquierdo sobre el jugador que este en survival"));
    spectator.sendMessage(
        Utils.chat("&7- &bSalir de la vista del tributo: &fUsa el shift"));
    spectator.sendMessage(
        Utils.chat("&7- &bSalir de los MacroHG: &fUsa el comando /lobby"));
  }

}
