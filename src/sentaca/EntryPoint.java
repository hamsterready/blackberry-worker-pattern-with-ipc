package sentaca;

import sentaca.bg.BgApplication;
import sentaca.lib.util.Logger;
import sentaca.ui.FgApplication;

/**
 * This class extends the UiApplication class, providing a graphical user
 * interface.
 */
public class EntryPoint {
  private static final String TAG = "ENTRY";

  /**
   * Entry point for application
   * 
   * @param args
   *          Command line arguments (not used)
   */
  public static void main(String[] args) {
    Logger.i(TAG, "Starting EntryPoint " + args.length);
    
    if (args.length == 1 && args[0].equals("background")) {
      Logger.i(TAG, "Starting background application");
      new BgApplication().enterEventDispatcher();
      return;
    }

    Logger.i(TAG, "Starting UI application.");
    new FgApplication().enterEventDispatcher();
  }

}
