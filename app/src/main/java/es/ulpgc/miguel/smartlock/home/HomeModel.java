package es.ulpgc.miguel.smartlock.home;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.ulpgc.miguel.smartlock.services.FirebaseContract;

public class HomeModel implements HomeContract.Model {

  public static String TAG = HomeModel.class.getSimpleName();

  private DatabaseReference databaseReference;

  public HomeModel() {
    this.databaseReference = FirebaseDatabase.getInstance().getReference();
  }

  @Override
  public void processRequest(String uid, FirebaseContract.ProcessRequest callback) {
    databaseReference.child("doors"); // todo
  }
}
