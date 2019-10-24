package es.ulpgc.miguel.smartlock.services;

import android.bluetooth.BluetoothSocket;

public interface BluetoothContract {
  interface AcceptThread {
    void onSocketConnected(BluetoothSocket socket);
  }
}
