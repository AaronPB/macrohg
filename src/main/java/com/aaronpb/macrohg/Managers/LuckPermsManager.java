package com.aaronpb.macrohg.Managers;

import com.aaronpb.macrohg.Macrohg;
import com.aaronpb.macrohg.Utils.Utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;

public class LuckPermsManager {

  private LuckPerms lpAPI = Macrohg.luckPerms;
  private static String tributegroup, deadtributegroup, mentorgroup;

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

    // Make group change
    InheritanceNode  newnode = InheritanceNode.builder(deadtribgroup).build();
    DataMutateResult result  = user.data().add(newnode);
    if (result.wasSuccessful()) {
      Utils.sendToServerConsole("info", "LPmanager - " + deadplayer
          + " has been added to " + deadtributegroup);
    } else {
      Utils.sendToServerConsole("error", "LPmanager - " + deadplayer
          + " could not be added to " + deadtributegroup);
    }

    InheritanceNode oldnode = InheritanceNode.builder(tribgroup).build();
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

    // Make group change
    InheritanceNode  newnode = InheritanceNode.builder(tribgroup).build();
    DataMutateResult result  = user.data().add(newnode);
    if (result.wasSuccessful()) {
      Utils.sendToServerConsole("info", "LPmanager - " + revivingplayer
          + " has been added to " + tributegroup);
    } else {
      Utils.sendToServerConsole("error", "LPmanager - " + revivingplayer
          + " could not be added to " + tributegroup);
    }

    InheritanceNode oldnode = InheritanceNode.builder(deadtribgroup).build();
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

  public boolean assignTributeGroup(String tributeplayer) {
    // Load user information
    User user = lpAPI.getUserManager().getUser(tributeplayer);
    if (user == null) {
      Utils.sendToServerConsole("warn", "LPmanager - " + tributeplayer
          + " does no exist in LuckPerms! Groups not changed.");
      return false;
    }

    // Load groups
    Group tribgroup = lpAPI.getGroupManager().getGroup(tributegroup);
    if (tribgroup == null) {
      Utils.sendToServerConsole("warn", "LPmanager - Group " + tributegroup
          + " does not exist in LuckPerms!");
      return false;
    }

    Group deadtribgroup = lpAPI.getGroupManager().getGroup(deadtributegroup);
    if (deadtribgroup == null) {
      Utils.sendToServerConsole("error", "LPmanager - The group "
          + deadtributegroup + " does not exist in LuckPerms!");
      return false;
    }

    // Make group change
    InheritanceNode  newnode = InheritanceNode.builder(tribgroup).build();
    DataMutateResult result  = user.data().add(newnode);
    if (result.wasSuccessful()) {
      Utils.sendToServerConsole("info", "LPmanager - " + tributeplayer
          + " has been added to " + tributegroup);
    } else {
      Utils.sendToServerConsole("error", "LPmanager - " + tributeplayer
          + " could not be added to " + tributegroup);
    }

    InheritanceNode oldnode = InheritanceNode.builder(deadtribgroup).build();
    result = user.data().remove(oldnode);
    if (result.wasSuccessful()) {
      Utils.sendToServerConsole("info", "LPmanager - " + tributeplayer
          + " has been removed from " + deadtributegroup);
    } else {
      Utils.sendToServerConsole("error", "LPmanager - " + tributeplayer
          + " could not be removed from " + deadtributegroup);
    }

    lpAPI.getUserManager().saveUser(user);

    return true;
  }

  public boolean assignMentorGroup(String mentorplayer) {
    // Load user information
    User user = lpAPI.getUserManager().getUser(mentorplayer);
    if (user == null) {
      Utils.sendToServerConsole("warn", "LPmanager - " + mentorplayer
          + " does no exist in LuckPerms! Groups not changed.");
      return false;
    }

    // Load groups
    Group mentgroup = lpAPI.getGroupManager().getGroup(mentorgroup);
    if (mentgroup == null) {
      Utils.sendToServerConsole("warn",
          "LPmanager - Group " + mentorgroup + " does not exist in LuckPerms!");
      return false;
    }

    // Make group change
    InheritanceNode  newnode = InheritanceNode.builder(mentgroup).build();
    DataMutateResult result  = user.data().add(newnode);
    if (result.wasSuccessful()) {
      Utils.sendToServerConsole("info",
          "LPmanager - " + mentorplayer + " has been added to " + mentorgroup);
    } else {
      Utils.sendToServerConsole("error", "LPmanager - " + mentorplayer
          + " could not be added to " + mentorgroup);
    }

    lpAPI.getUserManager().saveUser(user);

    return true;
  }

  // Setters
  public void setGroups(String tributeg, String deadtributeg,
      String mentorteg) {
    tributegroup = tributeg;
    deadtributegroup = deadtributeg;
    mentorgroup = mentorteg;
    Utils.sendToServerConsole("info", "LPmanager -  Groups correctly set!");
  }

}
