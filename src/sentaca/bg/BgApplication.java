package sentaca.bg;

import sentaca.lib.BackgroundApplication;
import sentaca.lib.BackgroundListener;
import sentaca.lib.util.GlobalEventListenerAdapter;
import sentaca.lib.util.Logger;
import sentaca.lib.util.SystemListenerAdapter;
import sentaca.ui.ProgressCallback;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.RuntimeStore;

public class BgApplication extends Application implements BackgroundApplication {

  private static final String TAG = "APP-BG";

  public BgApplication() {
    Logger.i(TAG, "Storing listener in runtime store...");
    final RuntimeStore store = RuntimeStore.getRuntimeStore();
    store.put(RUNTIME_STORE_BG_LISTENER_UID, new BackgroundListener(this));

    Logger.i(TAG, "Listener stored in runtime store.");

    addSystemListener(new SystemListenerAdapter() {
      public void powerOff() {
        super.powerOff();
        Logger.i(TAG, "Powering off... cleaning...");
        cleanup();
      }
    });

    addGlobalEventListener(new GlobalEventListenerAdapter() {
      public void eventOccurred(long guid, int data0, int data1, Object object0, Object object1) {
        if (guid == BG_GUID) {
          cleanup();
        }
      }
    });

  }

  private void cleanup() {
    Logger.i(TAG, "Removing listener...");
    final RuntimeStore store = RuntimeStore.getRuntimeStore();
    final BackgroundListener listener = (BackgroundListener) store.remove(RUNTIME_STORE_BG_LISTENER_UID);
    int code = 0;
    if (listener != null) {
      listener.cleanup();
      Logger.i(TAG, "Listener removed and cleaned up.");
    } else {
      Logger.w(TAG, "Cannot find listener... that's really strange mate.");
      code = 1;
    }
    Logger.i(TAG, "Closing application with code " + code);
    System.exit(code);
  }

  public void startJob(final ProgressCallback progressCallback) {
    invokeLater(new Runnable() {

      public void run() {
        for (int i = 0; i <= 100; i++) {
          progressCallback.update(i);
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            Logger.e(TAG, e.getMessage());
            // just to rest UI
            progressCallback.update(100);
          }
        }
      }
    });

  }

}
