package es.ulpgc.miguel.smartlock.services;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectedThread extends Thread {
  private static final String TAG = ConnectedThread.class.getSimpleName();
  private static final int MESSAGE_READ = 0;
  private final BluetoothSocket mmSocket;
  private final InputStream mmInStream;
  private final OutputStream mmOutStream;
  private byte[] mmBuffer; // mmBuffer store for the stream
  private final Handler handler;

  public ConnectedThread(BluetoothSocket socket, Handler handler) {
    mmSocket = socket;
    InputStream tmpIn = null;
    OutputStream tmpOut = null;
    this.handler = handler;

    // Get the input and output streams; using temp objects because
    // member streams are final.
    try {
      tmpIn = socket.getInputStream();
    } catch (IOException e) {
      Log.e(TAG, "Error occurred when creating input stream", e);
    }
    try {
      tmpOut = socket.getOutputStream();
    } catch (IOException e) {
      Log.e(TAG, "Error occurred when creating output stream", e);
    }

    mmInStream = tmpIn;
    mmOutStream = tmpOut;
  }

  public void run() {
    mmBuffer = new byte[64]; // todo cambiar tamaño si es necesario
    int numBytes; // bytes returned from read()

    // Keep listening to the InputStream until an exception occurs.
    while (true) {
      try {
        // Read from the InputStream.

        numBytes = mmInStream.read(mmBuffer);
        // Send the obtained bytes to the UI activity.
        Message readMsg = handler.obtainMessage(
            MESSAGE_READ, numBytes, -1,
            mmBuffer);
        readMsg.sendToTarget();
        //this.cancel();
      } catch (IOException e) {
        Log.d(TAG, "Input stream was disconnected", e);
        cancel(); // todo lo puse yo
        break;
      }
    }
    // todo: no se si va o no aquí
  }

  // Call this from the main activity to send data to the remote device.
    /*public void write(byte[] bytes) {
      try {
        mmOutStream.write(bytes);

        // Share the sent message with the UI activity.
        Message writtenMsg = handler.obtainMessage(
            MESSAGE_WRITE, -1, -1, mmBuffer);
        writtenMsg.sendToTarget();
      } catch (IOException e) {
        Log.e(TAG, "Error occurred when sending data", e);

        // Send a failure message back to the activity.
        Message writeErrorMsg =
            handler.obtainMessage(MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("toast",
            "Couldn't send data to the other device");
        writeErrorMsg.setData(bundle);
        handler.sendMessage(writeErrorMsg);
      }
    }*/

  // Call this method from the main activity to shut down the connection.
  public void cancel() {
    try {
      mmSocket.close();
    } catch (IOException e) {
      Log.e(TAG, "Could not close the connect socket", e);
    }
  }
}

