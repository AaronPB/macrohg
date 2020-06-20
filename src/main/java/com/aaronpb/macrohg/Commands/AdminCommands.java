package com.aaronpb.macrohg.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import com.aaronpb.macrohg.Core;
import com.aaronpb.macrohg.Utils.Utils;

public class AdminCommands implements Listener, CommandExecutor {

  private Core core = new Core();

  public String maincmd = "macrohg";

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label,
      String[] args) {

    // TODO Admin changes time of the match, kill users, pause and stop
    // properly.
    // TODO Add arenarunning statements for some commands.

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
        case "start":
          if (Core.arenarunning) {
            sender.sendMessage(Utils.chat(
                "&cLa arena esta ya inicada! Si quieres pausarla, usa stop."));
            break;
          }
          core.startGame();
          break;
        case "directstart":
          if (Core.arenarunning) {
            sender.sendMessage(Utils.chat(
                "&cLa arena esta ya inicada! Si quieres pausarla, usa stop."));
            break;
          }
          core.directStartGame();
          break;
        case "stop":
          if (!Core.arenarunning) {
            sender.sendMessage(Utils.chat(
                "&cLa arena no ha empezado! Iniciala con start o directstart."));
            break;
          }
          core.stopGame();
          break;
        case "resume":
          if (Core.arenarunning) {
            sender.sendMessage(Utils.chat(
                "&cLa arena esta ya inicada! Si quieres pausarla, usa stop."));
            break;
          }
          core.directStartGame();
          break;
        case "settime":
          if (!Core.arenarunning) {
            sender.sendMessage(Utils.chat(
                "&cLa arena no ha empezado! Veulve a iniciarla con start o directart, o retoma la pausa con resume."));
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
        case "deathmatch":
          sender.sendMessage(
              Utils.chat("&3Todavia no se ha realizado esta opcion"));
          break;
        default:
          sender.sendMessage(Utils.chat("&cNo existe el argumento " + args[0]
              + "! Validos: start y stop"));
          break;
      }
      return true;
    }

    return false;
  }
}
