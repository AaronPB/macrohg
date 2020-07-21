package com.aaronpb.macrohg;

import java.util.ArrayList;
import java.util.List;

public class District {

  /* Private class methods */

  /* Class variables */
  private String districtname, mentor, tribute1, tribute2;
  private boolean alive1 = true, alive2 = true;

  /* Class contructor */
  public District(String district, String mentor, String tribute1,
      String tribute2) {
    this.districtname = district;
    this.mentor = mentor;
    this.tribute1 = tribute1;
    this.tribute2 = tribute2;
    if(tribute2 == null)
      this.alive2 = false;
  }

  /* Public class contructors */

  // Setters
  public void setTribute1Dead() {
    this.alive1 = false;
  }

  public void setTribute2Dead() {
    this.alive2 = false;
  }

  public void setTributeAlive(String tributename) {
    if (tributename.equals(this.tribute1)) {
      this.alive1 = true;
    } else if (tributename.equals(this.tribute2)) {
      this.alive2 = true;
    }
  }

  // Getters
  public List<String> getTributes() {
    List<String> list = new ArrayList<String>();
    list.add(tribute1);
    if (this.tribute2 != null)
      list.add(tribute2);
    return list;
  }

  public List<String> getAliveTributes() {
    List<String> list = new ArrayList<String>();
    if (alive1)
      list.add(tribute1);
    if (alive2)
      list.add(tribute2);

    return list;
  }

  public boolean getHasMentor() {
    if (mentor != null)
      return true;
    return false;
  }

  public String getDisctrictName() {
    return districtname;
  }

  public String getMentor() {
    return mentor;
  }

  public String getTribute1() {
    return tribute1;
  }

  public String getTribute2() {
    return tribute2;
  }

}
