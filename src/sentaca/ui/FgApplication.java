package sentaca.ui;

import sentaca.lib.BackgroundApplication;
import sentaca.lib.BackgroundListener;
import sentaca.lib.util.Logger;
import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.system.ApplicationManagerException;
import net.rim.device.api.system.RuntimeStore;
import net.rim.device.api.ui.UiApplication;

public class FgApplication extends UiApplication {
  public static final String TAG = "FG-APP";

  public interface Callback {
    void call(Object o);
  }

  public FgApplication() {
    startBackgroundApplicationIfNeeded(new Callback() {

      public void call(Object o) {
        pushScreen(new ApplicationScreen((BackgroundListener) o));
      }
    });
  }

  private void startBackgroundApplicationIfNeeded(final Callback callback) {
    BackgroundListener listener = (BackgroundListener) RuntimeStore.getRuntimeStore().get(
        BackgroundApplication.RUNTIME_STORE_BG_LISTENER_UID);

    if (listener != null) {
      callback.call(listener);
      return;
    }

    Logger.w(FgApplication.TAG, "Cannot find listener... that's bad... let's start background application.");

    final InitializationScreen initializingScreen = new InitializationScreen();
    pushScreen(initializingScreen);
    invokeLater(new Runnable() {

      public void run() {
        try {
          Logger.i(TAG, "Invoking background application...");
          ApplicationDescriptor[] visibleApplications = ApplicationManager.getApplicationManager()
              .getVisibleApplications();

          ApplicationManager.getApplicationManager().runApplication(
              new ApplicationDescriptor(ApplicationDescriptor.currentApplicationDescriptor(),
                  new String[] { "background" }), false);

          Logger.i(TAG, "Application started.");

          BackgroundListener newListner = null;
          do {
            newListner = (BackgroundListener) RuntimeStore.getRuntimeStore().get(
                BackgroundApplication.RUNTIME_STORE_BG_LISTENER_UID);
            if (newListner == null) {
              try {
                Thread.sleep(500);
              } catch (InterruptedException e) {
                Logger.e(TAG, e.getMessage());
              }
            }
          } while (newListner == null);
          popScreen(initializingScreen);
          callback.call(newListner);
        } catch (ApplicationManagerException e) {
          Logger.e(FgApplication.TAG, "Failed to start background application..." + e.getMessage());
          initializingScreen.updateText("Failed to start background application: " + e.getMessage());
          initializingScreen.showExit();
        }
      }
    });

  }
}
