package sentaca.lib;

import sentaca.ui.ProgressCallback;

public class BackgroundListener {

  private BackgroundApplication backgroundApplication;

  public BackgroundListener(BackgroundApplication application) {
    this.backgroundApplication = application;
  }

  public void cleanup() {
    this.backgroundApplication = null;
  }

  public void startJob(ProgressCallback progressCallback) {
    this.backgroundApplication.startJob(progressCallback);
  }

}
