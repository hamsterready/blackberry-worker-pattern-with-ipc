package sentaca.ui;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.PopupScreen;

public class InitializationScreen extends PopupScreen {

  private LabelField label;

  /**
   * 
   */
  public InitializationScreen() {
    super(new HorizontalFieldManager());
    label = new LabelField("Initializing...");
    add(label);

  }

  public void updateText(String text) {
    label.setText(text);
  }

  public void showExit() {
    ButtonField exit = new ButtonField("quit");
    exit.setChangeListener(new FieldChangeListener() {

      public void fieldChanged(Field field, int context) {
        System.exit(1);
      }
    });
    add(exit);
  }

}
