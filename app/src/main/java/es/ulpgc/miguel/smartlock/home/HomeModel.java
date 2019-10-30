package es.ulpgc.miguel.smartlock.home;

import android.bluetooth.BluetoothAdapter;
import android.location.Location;
import android.os.SystemClock;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import es.ulpgc.miguel.smartlock.models.Door;
import es.ulpgc.miguel.smartlock.services.FirebaseContract;

public class HomeModel implements HomeContract.Model {

  public static String TAG = HomeModel.class.getSimpleName();

  private Door door;

  private DatabaseReference databaseReference;

  public HomeModel() {
    this.databaseReference = FirebaseDatabase.getInstance().getReference();
    this.door = new Door();
  }

  public Door getDoor() {
    return door;
  }

  public void setDoor(Door door) {
    this.door = door;
  }

  @Override
  public void processRequest(String uid, final FirebaseContract.ProcessRequest callback) {
    if (getDoor().getUsers().contains(uid)) {
      final DatabaseReference statusReference = databaseReference.child("doors").child(String.valueOf(getDoor().getId())).child("open");
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

  @Override
  public void syncDoor(final String address) {
    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Iterable<DataSnapshot> doors = dataSnapshot.child("doors").getChildren();

        for (DataSnapshot door: doors) {
          Door item = door.getValue(Door.class);
          if (item.getAddress().equals(address)) {
            setDoor(item);
            break;
          }
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.d(TAG, String.valueOf(databaseError));
      }
    });
  }
}
