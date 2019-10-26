package es.ulpgc.miguel.smartlock.services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class AcceptThread extends Thread {
  private static final String TAG = AcceptThread.class.getSimpleName();
  private final BluetoothServerSocket mmServerSocket;
  private final BluetoothAdapter bluetoothAdapter;
  private final BluetoothContract.AcceptThread callback;

  public AcceptThread(BluetoothAdapter bluetoothAdapter, UUID UUID, String NAME, BluetoothContract.AcceptThread callback) {
    // Use a temporary object that is later assigned to mmServerSocket
    // because mmServerSocket is final.
    BluetoothServerSocket tmp = null;
    this.bluetoothAdapter = bluetoothAdapter;
    this.callback = callback;
    try {
      // MY_UUID is the app's UUID string, also used by the client code.
      tmp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME, UUID);
    } catch (IOException e) {
      Log.e(TAG, "Socket's listen() method failed", e);
    }
    mmServerSocket = tmp;
  }

  public void run() {
    BluetoothSocket socket = null;
    // Keep listening until exception occurs or a socket is returned.
    while (true) {
      try {
        socket = mmServerSocket.accept();
      } catch (IOException e) {
        Log.e(TAG, "Socket's accept() method failed", e);
        break;
      }

      if (socket != null) {
        // A connection was accepted. Perform work associated with
        // the connection in a separate thread.
        callback.onSocketConnected(socket);

        //try {
        //mmServerSocket.close();
        //} catch (IOException e) {
        //e.printStackTrace();
        //}
        //break; //todo: it is not only a connection, many connections
      }
    }
  }

  // Closes the connect socket and causes the thread to finish.
  public void cancel() {
    try {
      mmServerSocket.close();

    } catch (IOException e) {
      Log.e(TAG, "Could not close the connect socket", e);
    }
  }
}
