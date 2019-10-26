package es.ulpgc.miguel.smartlock.home;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import es.ulpgc.miguel.smartlock.models.Door;
import es.ulpgc.miguel.smartlock.services.FirebaseContract;

public class HomeModel implements HomeContract.Model {

  public static String TAG = HomeModel.class.getSimpleName();

  private DatabaseReference databaseReference;

  // indicating the door id
  private String id;

  public HomeModel() {
    this.databaseReference = FirebaseDatabase.getInstance().getReference();
    id = "0"; // todo aqui deberiamos preguntar al dispositivo al encenderse que id es el suyo
  }

  @Override
  public void processRequest(String uid, final FirebaseContract.ProcessRequest callback) {
    final DatabaseReference statusReference = databaseReference.child("doors").child("0").child("open");
    statusReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        boolean open = (boolean) dataSnapshot.getValue();
        if (open) {
          statusReference.setValue(false); // if door is open it closes it
          callback.onProcessedRequest(false, false);
        } else {
          statusReference.setValue(true); // else it opens it
          callback.onProcessedRequest(false, true);
        }

      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {
        callback.onProcessedRequest(true, true); // todo no estoy seguro
      }
    });
  }
}
