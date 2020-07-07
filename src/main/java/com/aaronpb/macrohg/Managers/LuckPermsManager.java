package com.aaronpb.macrohg.Managers;

import com.aaronpb.macrohg.Core;
import com.aaronpb.macrohg.Macrohg;
import com.aaronpb.macrohg.Utils.Utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.context.MutableContextSet;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;

public class LuckPermsManager {

  private LuckPerms lpAPI = Macrohg.luckPerms;
  private static String tributegroup, deadtributegroup;

  public void changeToDeadTribute(String deadplayer) {
    // Load user information
    User user = lpAPI.getUserManager().getUser(deadplayer);
    if (user == null) {
      Utils.sendToServerConsole("warn", "LPmanager - " + deadplayer
          + " does no exist in LuckPerms! Groups not changed.");
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

    MutableContextSet macrohgcontext = MutableContextSet.create();
    macrohgcontext.add("server", "juegos");
    macrohgcontext.add("world", Core.arena);

    // Make group change
    InheritanceNode  newnode = InheritanceNode.builder(deadtribgroup)
        .context(macrohgcontext).build();
    DataMutateResult result  = user.data().add(newnode);
    if (result.wasSuccessful()) {
      Utils.sendToServerConsole("info", "LPmanager - " + deadplayer
          + " has been added to " + deadtributegroup);
    } else {
      Utils.sendToServerConsole("error", "LPmanager - " + deadplayer
          + " could not be added to " + deadtributegroup);
    }

    InheritanceNode oldnode = InheritanceNode.builder(tribgroup)
        .context(macrohgcontext).build();
    result = user.data().remove(oldnode);
    if (result.wasSuccessful()) {
      Utils.sendToServerConsole("info", "LPmanager - " + deadplayer
          + " has been removed from " + tributegroup);
    } else {
      Utils.sendToServerConsole("error", "LPmanager - " + deadplayer
          + " could not be removed from " + tributegroup);
    }

    lpAPI.getUserManager().saveUser(user);
  }

  public void changeToAliveTribute(String revivingplayer) {
    // Load user information
    User user = lpAPI.getUserManager().getUser(revivingplayer);
    if (user == null) {
      Utils.sendToServerConsole("warn", "LPmanager - " + revivingplayer
          + " does no exist in LuckPerms! Groups not changed.");
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

    MutableContextSet macrohgcontext = MutableContextSet.create();
    macrohgcontext.add("server", "juegos");
    macrohgcontext.add("world", Core.arena);

    // Make group change
    InheritanceNode  newnode = InheritanceNode.builder(tribgroup)
        .context(macrohgcontext).build();
    DataMutateResult result  = user.data().add(newnode);
    if (result.wasSuccessful()) {
      Utils.sendToServerConsole("info", "LPmanager - " + revivingplayer
          + " has been added to " + tributegroup);
    } else {
      Utils.sendToServerConsole("error", "LPmanager - " + revivingplayer
          + " could not be added to " + tributegroup);
    }

    InheritanceNode oldnode = InheritanceNode.builder(deadtribgroup)
        .context(macrohgcontext).build();
    result = user.data().remove(oldnode);
    if (result.wasSuccessful()) {
      Utils.sendToServerConsole("info", "LPmanager - " + revivingplayer
          + " has been removed from " + deadtributegroup);
    } else {
      Utils.sendToServerConsole("error", "LPmanager - " + revivingplayer
          + " could not be removed from " + deadtributegroup);
    }

    lpAPI.getUserManager().saveUser(user);
  }

  // Setters
  public void setGroups(String tributeg, String deadtributeg) {
    tributegroup = tributeg;
    deadtributegroup = deadtributeg;
    Utils.sendToServerConsole("info", "LPmanager -  Groups correctly set!");
  }

}
