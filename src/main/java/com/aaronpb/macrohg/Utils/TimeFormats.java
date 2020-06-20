package com.aaronpb.macrohg.Utils;

public class TimeFormats {

  public static String getHourTimeFormatted(long duration) {
    int boosthours, boostminutes, boostseconds;
    boosthours = (int) (duration / 3600);
    boostminutes = (int) ((duration - (3600 * boosthours)) / 60);
    boostseconds = (int) (duration - (3600 * boosthours) - (60 * boostminutes));
    return String.format("%02d:%02d:%02d", boosthours, boostminutes,
        boostseconds);
  }

  public static String getMinuteTimeFormatted(long duration) {
    int boostminutes, boostseconds;
    boostminutes = (int) (duration / 60);
    boostseconds = (int) (duration - (60 * boostminutes));
    return String.format("%02d:%02d", boostminutes, boostseconds);
  }

}
