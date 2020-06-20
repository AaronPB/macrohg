package com.aaronpb.macrohg.Managers;

import org.bukkit.entity.Player;

import com.aaronpb.macrohg.Macrohg;
import com.aaronpb.macrohg.Utils.Utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;

public class LuckPermsManager {

  private LuckPerms lpAPI = Macrohg.luckPerms;
  private static String tributegroup, deadtributegroup;

  public void changeToDeadTribute(Player player) {
    // Load user information
    User user = lpAPI.getUserManager().getUser(player.getName());
    if (user == null) {
      Utils.sendToServerConsole("warn", "LPmanager - " + player.getName()
          + " does no exist in LuckPerms! Returning false");
      return;
    }

    // Load groups
    Group tribgroup = lpAPI.getGroupManager().getGroup(tributegroup);
    if (tribgroup == null) {
      Utils.sendToServerConsole("warn", "LPmanager - Group " + tributegroup
          + " does not exist in LuckPerms!");
      return;
    }

    Group deadtribgroup = lpAPI.getGroupManager().getGroup(deadtributegroup);
    if (deadtribgroup == null) {
      Utils.sendToServerConsole("error", "LPmanager - The group "
          + deadtributegroup + " does not exist in LuckPerms!");
      return;
    }

    // Make group change
    InheritanceNode newnode = InheritanceNode.builder(deadtribgroup).build();
    user.data().add(newnode);
    Utils.sendToServerConsole("info", "LPmanager - " + player.getName()
        + " has been added to " + deadtributegroup);

    InheritanceNode oldnode = InheritanceNode.builder(tribgroup).build();
    user.data().remove(oldnode);
    Utils.sendToServerConsole("info", "LPmanager - " + player.getName()
        + " has been removed from " + tributegroup);
  }

  // Setters
  public void setGroups(String tributeg, String deadtributeg) {
    tributegroup = tributeg;
    deadtributegroup = deadtributeg;
    Utils.sendToServerConsole("info", "LPmanager -  Groups correctly set!");
  }

}
