package es.ulpgc.miguel.smartlock.services;

public interface FirebaseContract {
  interface ProcessRequest {
    void onProcessedRequest(boolean error, boolean open);
  }
}
