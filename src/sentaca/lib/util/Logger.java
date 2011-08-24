package sentaca.lib.util;

import java.util.Date;

import net.rim.device.api.i18n.SimpleDateFormat;

public class Logger {
  private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
  private static final String INFO = " [INFO]";
  private static final String WARN = " [WARN]";
  private static final String ERROR = "[ERROR]";

  public static void i(String tag, String msg) {
    q(tag, msg, INFO);
  }

  private static void q(String tag, String msg, String lvl) {
    int max = 12;
    String t = tag;
    if (t.length() > max) {
      t = t.substring(0, max);
    } else {
      for (int i = t.length(); i <= max; i++) {
        t += " ";
      }
    }
    String s = new SimpleDateFormat(FORMAT).format(new Date()) + " " + t + " " + lvl + " " + msg;
    System.out.println(s);
  }

  public static void w(String tag, String msg) {
    q(tag, msg, WARN);
  }

  public static void e(String tag, String msg) {
    q(tag, msg, ERROR);

  }
}
