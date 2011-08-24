package sentaca.lib;

import sentaca.ui.ProgressCallback;

public interface BackgroundApplication {

  long BG_GUID = 0xBABECAFE;

  long RUNTIME_STORE_BG_LISTENER_UID = 0x1231231238l;

  void startJob(ProgressCallback progressCallback);

}
