package es.ulpgc.miguel.smartlock.home;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import es.ulpgc.miguel.smartlock.R;
import es.ulpgc.miguel.smartlock.services.AcceptThread;
import es.ulpgc.miguel.smartlock.services.BluetoothContract;
import es.ulpgc.miguel.smartlock.services.ConnectedThread;

public class HomeActivity
    extends AppCompatActivity implements HomeContract.View {

  public static String TAG = HomeActivity.class.getSimpleName();

  private HomeContract.Presenter presenter;

  // bluetooth
  private static final String NAME = "BluetoothCommunication";
  private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
  private static final int MESSAGE_READ = 0;
  private BluetoothAdapter bluetoothAdapter;

  Handler handler = new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(@NonNull Message message) {
      switch (message.what) {
        case MESSAGE_READ:
          byte[] readBuffer = (byte[]) message.obj;
          String uid = new String(readBuffer, 0, message.arg1);
          presenter.processRequest(uid);
          //textView.setText(tempMsg); // todo cambiar

          break;
      }
      return false;
    }
  });

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    // bluetooth
    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    new AcceptThread(bluetoothAdapter, MY_UUID, NAME, new BluetoothContract.AcceptThread() {
      @Override
      public void onSocketConnected(BluetoothSocket socket) {
        new ConnectedThread(socket, handler).start(); //todo hay que cerrarlo de algun modo??
      }
    }).start();

    // do the setup
    HomeScreen.configure(this);
  }

  @Override
  public void injectPresenter(HomeContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void displayData(HomeViewModel viewModel) {
    //Log.e(TAG, "displayData()");

    // deal with the data
    ((TextView) findViewById(R.id.data)).setText(viewModel.data);
  }
}
