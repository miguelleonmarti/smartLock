package es.ulpgc.miguel.smartlock.app;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import es.ulpgc.miguel.smartlock.home.HomeState;

public class AppMediator extends Application {
  private HomeState homeState;

  @Override
  public void onCreate() {
    super.onCreate();

    // initializing the FirebaseApp
    FirebaseApp.initializeApp(getApplicationContext());

    // initializing states
    this.homeState = new HomeState();
  }

  public HomeState getHomeState() {
    return homeState;
  }

  public void setHomeState(HomeState homeState) {
    this.homeState = homeState;
  }
}
