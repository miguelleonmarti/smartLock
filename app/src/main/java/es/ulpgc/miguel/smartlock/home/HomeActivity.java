package es.ulpgc.miguel.smartlock.home;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.LinearLayout;
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

  // text view
  private TextView message;
  private LinearLayout layout;

  // bluetooth
  private static final String NAME = "BluetoothCommunication";
  private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
  private String address;
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
          break;
      }
      return false;
    }
  });

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    // hiding the action bar
    getSupportActionBar().hide();

    // initializing text view and layout
    message = findViewById(R.id.messageText);
    layout = findViewById(R.id.layout);

    // bluetooth
    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    address = bluetoothAdapter.getAddress();
    if (!bluetoothAdapter.isEnabled()) {
      Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      startActivity(enableBluetoothIntent);
    } else {
      new AcceptThread(bluetoothAdapter, MY_UUID, NAME, new BluetoothContract.AcceptThread() {
        @Override
        public void onSocketConnected(BluetoothSocket socket) {
          ConnectedThread connectedThread = new ConnectedThread(socket, handler); //todo hay que cerrarlo de algun modo??
          connectedThread.start();
          SystemClock.sleep(1500);
        }
      }).start();
    }

    // do the setup
    HomeScreen.configure(this);

    // sync door
    presenter.syncDoor(address);
  }

  @Override
  public void injectPresenter(HomeContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void displayData(final HomeViewModel viewModel) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        message.setText(viewModel.getMessage());
        if (viewModel.getMessage().equals("Open"))
          layout.setBackgroundColor(getResources().getColor(R.color.green_background));
        else
          layout.setBackgroundColor(getResources().getColor(R.color.red_background));
      }
    });
  }
}
