package com.aaronpb.macrohg.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.aaronpb.macrohg.Core;
import com.aaronpb.macrohg.Macrohg;
import com.aaronpb.macrohg.Utils.TimeFormats;
import com.aaronpb.macrohg.Utils.Utils;

public class AdminCommands implements Listener, CommandExecutor {

  private Core core = new Core();

  public String maincmd = "macrohg";

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label,
      String[] args) {

    if (cmd.getName().equalsIgnoreCase(maincmd)) {
      if (args == null) {
        sender.sendMessage(Utils.chat("&cFaltan argumentos! start o stop"));
        return true;
      }

      if (args.length == 0 || args.length > 3) {
        sender.sendMessage(
            Utils.chat("&cDemasiados argumentos!! Usa start o stop"));
        return true;
      }

      switch (args[0]) {
        case "help":
          sender.sendMessage(
              Utils.chat("&e&m====&6 &lMacroHG - Asistencia&e &m===="));
          sender.sendMessage(Utils.chat(
              "&e/macrohg reload &7Recargar la config (debe hacerse con la arena parada)"));
          sender.sendMessage(
              Utils.chat("&6/macrohg status &7Revisar el estado del juego"));
          sender.sendMessage(Utils
              .chat("&6/macrohg start &7Iniciar el juego con cuenta atras"));
          sender.sendMessage(Utils.chat(
              "&6/macrohg direcstart &7Iniciar el juego sin cuenta atras"));
          sender.sendMessage(Utils.chat(
              "&6/macrohg stop &7Parar el juego (se queda todo tal y como esta)"));
          sender.sendMessage(Utils.chat(
              "&6/macrohg settime <sec> &7Poner el tiempo global en x segundos"));
          sender.sendMessage(Utils.chat(
              "&6/macrohg kill <jugador> &7Matar a un tributo de la arena"));
          sender.sendMessage(Utils.chat(
              "&e/macrohg revive <jugador> &7Revivir a un tributo en la arena"));
          sender.sendMessage(Utils.chat(
              "&e/macrohg deathmatch &7Activar la propagacion de muerte sobre los tributos"));
          break;
        case "reload":
          sender.sendMessage(
              Utils.chat("&3Todavia no se ha realizado esta opcion"));
          break;
        case "status":
          sender.sendMessage(
              Utils.chat("&e&m====&6 &lMacroHG - Status&e &m===="));
          if (Core.arenarunning) {
            sender.sendMessage(Utils.chat("&7- &6Juego: &aEsta activado"));
          } else {
            sender.sendMessage(Utils.chat("&7- &6Juego: &cEsta desactivado"));
          }
          sender.sendMessage(Utils.chat("&7- &6Tiempo actual: &e"
              + TimeFormats.getHourTimeFormatted(core.getMainTime())));
          sender.sendMessage(Utils
              .chat("&7- &6Tributos vivos: &e" + core.getAllAliveTributes()));
          sender.sendMessage(
              Utils.chat("&7- &6Tributos registrados: &aOnline &cOffline\n&f"
                  + core.getAllListedTributes()));
          break;
        case "start":
          if (Core.arenarunning) {
            sender.sendMessage(Utils.chat(
                "&cLa arena esta ya inicada! Si quieres pausarla, usa stop."));
            break;
          }
          core.startGame();
          sender.sendMessage(Utils.chat("&aSe ha iniciado el juego!"));
          break;
        case "directstart":
          if (Core.arenarunning) {
            sender.sendMessage(Utils.chat(
                "&cLa arena esta ya inicada! Si quieres pausarla, usa stop."));
            break;
          }
          core.directStartGame();
          sender.sendMessage(
              Utils.chat("&aSe ha iniciado el juego directamente!"));
          break;
        case "stop":
          if (!Core.arenarunning) {
            sender.sendMessage(Utils.chat(
                "&cLa arena no ha empezado! Iniciala con start o directstart."));
            break;
          }
          core.stopGame();
          sender.sendMessage(Utils.chat(
              "&aSe ha parado el juego. Activalo de nuevo con &6/macrohg <start|directstart>"));
          break;
        case "settime":
          if (!Core.arenarunning) {
            sender.sendMessage(Utils.chat(
                "&cLa arena no ha empezado! Vuelve a iniciarla con start o directart, o retoma la pausa con resume."));
            break;
          }
          if (args.length == 2) {
            int time;
            try {
              time = Integer.parseInt(args[1]);
              core.setArenaTimer(time);
              sender.sendMessage(Utils
                  .chat("&aSe ha establecido la arena en el tiempo " + time));
            } catch (NumberFormatException e) {
              sender.sendMessage(Utils.chat("&cTiempo invalido! " + args[1]
                  + "! Pon un tiempo valido en segundos desde 0 hasta 3600"));
            }
          } else {
            sender.sendMessage(Utils.chat(
                "&cIndica el tiempo al que se quiere retomar el juego: de 0 a 3600 segundos."));
          }
          break;
        case "kill":
          if (!Core.arenarunning) {
            sender.sendMessage(Utils.chat(
                "&cLa arena no ha empezado! Usalo cuando la arena este iniciada."));
            break;
          }
          if (args.length == 2) {
            if (!core.getIsAliveTribute(args[1])) {
              sender.sendMessage(Utils.chat(
                  "&cNo existe ningun tributo vivo con el nombre " + args[1]));
              break;
            }
            Player tributo = Macrohg.plugin.getServer().getPlayer(args[1]);
            if (tributo != null) {
              core.killTribute(tributo,
                  core.getTributeDistrict(tributo.getName()), null);
            } else {
              core.killTribute(args[1], core.getTributeDistrict(args[1]), null);
            }
          } else {
            sender.sendMessage(Utils
                .chat("&cIndica el nombre del tributo al que deseas matar."));
          }
          break;
        case "revive":
          sender.sendMessage(
              Utils.chat("&3Todavia no se ha realizado esta opcion"));
          break;
        case "deathmatch":
          sender.sendMessage(
              Utils.chat("&3Todavia no se ha realizado esta opcion"));
          break;
        default:
          sender.sendMessage(Utils.chat("&cNo existe el argumento " + args[0]
              + "! Consulta los comandos en &6/macrohg help"));
          break;
      }
      return true;
    }

    return false;
  }
}
