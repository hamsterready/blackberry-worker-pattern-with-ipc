package sentaca.ui;

import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.GaugeField;
import net.rim.device.api.ui.container.MainScreen;
import sentaca.lib.BackgroundApplication;
import sentaca.lib.BackgroundListener;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class ApplicationScreen extends MainScreen {
  private final BackgroundListener listener;

  /**
   * Creates a new Registration object
   * 
   * @param listener
   */
  public ApplicationScreen(final BackgroundListener listener) {
    this.listener = listener;

    final int max = 100;

    final GaugeField progress = new GaugeField("Progress", 0, max, 0, GaugeField.PERCENT);
    final ButtonField start = new ButtonField("Go");
    final ButtonField exit = new ButtonField("Exit");

    start.setChangeListener(new FieldChangeListener() {

      public void fieldChanged(Field field, int context) {
        start.setEditable(false);
        exit.setEditable(false);

        listener.startJob(new ProgressCallback() {

          public void update(int v) {
            progress.setValue(v);
            if (v == max) {
              start.setEditable(true);
              exit.setEditable(true);
            }
          }
        });
      }
    });

    exit.setChangeListener(new FieldChangeListener() {
      public void fieldChanged(Field field, int context) {
        ApplicationManager.getApplicationManager().postGlobalEvent(BackgroundApplication.BG_GUID);
        System.exit(0);
      }
    });

    add(progress);
    add(start);
    add(exit);
  }
}
