package com.aaronpb.macrohg.Managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.aaronpb.macrohg.District;
import com.aaronpb.macrohg.Macrohg;
import com.aaronpb.macrohg.Utils.Utils;

import net.milkbowl.vault.economy.Economy;

public class VaultManager {

  private Economy econAPI = Macrohg.economy;
  private static int econ_tributekill, econ_timesurvived, econ_districtkilled;

  public void giveMoneyKill(District district) {
    if (district.getHasMentor() && district.getAliveTributes().size() > 0) {
      Player player = Bukkit.getPlayer(district.getMentor());
      if (player != null) {
        econAPI.depositPlayer(player, econ_tributekill);
        Utils.sendToServerConsole("info", "[PLAYERKILLED] Sended "
            + econ_tributekill + " to mentor " + district.getMentor());
      } else {
        Utils.sendToServerConsole("warn",
            "[PLAYERKILLED] Could not send " + econ_tributekill + " to mentor "
                + district.getMentor() + "! (Offline??)");
      }
    }
  }

  public void giveMoneyDistrictKilled(District district) {
    if (district.getHasMentor() && district.getAliveTributes().size() > 0) {
      Player player = Bukkit.getPlayer(district.getMentor());
      if (player != null) {
        econAPI.depositPlayer(player,
            district.getAliveTributes().size() * econ_districtkilled);
        Utils.sendToServerConsole("info",
            "[DISTRICTKILLED] Sended "
                + district.getAliveTributes().size() * econ_districtkilled
                + " to mentor " + district.getMentor());
      } else {
        Utils.sendToServerConsole("warn",
            "[DISTRICTKILLED] Could not send "
                + district.getAliveTributes().size() * econ_districtkilled
                + " to mentor " + district.getMentor() + "! (Offline??)");
      }
    }
  }

  public void giveMoneyTimeSurvived(ArrayList<District> districtlist) {
    for (District district : districtlist) {

      List<String> alivetributes = district.getAliveTributes();

      if (district.getHasMentor() && alivetributes.size() > 0) {
        Player player = Bukkit.getPlayer(district.getMentor());
        if (player != null) {
          econAPI.depositPlayer(player,
              alivetributes.size() * econ_timesurvived);
          Utils.sendToServerConsole("info",
              "[TIMESURVIVED][" + district.getDisctrictName() + "] Sended "
                  + alivetributes.size() * econ_timesurvived + " to mentor "
                  + district.getMentor());
        } else {
          Utils.sendToServerConsole("warn",
              "[TIMESURVIVED][]" + district.getDisctrictName()
                  + " Could not send "
                  + alivetributes.size() * econ_timesurvived + " to mentor "
                  + district.getMentor() + "! (Offline??)");
        }
      } else if (!district.getHasMentor()) {
        Utils.sendToServerConsole("info",
            "[TIMESURVIVED] " + district.getDisctrictName()
                + " does not receive money because it has no mentor.");
      } else {
        Utils.sendToServerConsole("info",
            "[TIMESURVIVED] " + district.getDisctrictName()
                + " does not receive money - Alive tributes: " + alivetributes);
      }
    }
  }

  // Economy setter
  public void setEconSettings(int tributekill, int timesurvived,
      int districtkilled) {
    econ_tributekill = tributekill;
    econ_timesurvived = timesurvived;
    econ_districtkilled = districtkilled;
  }

}
