package es.ulpgc.miguel.smartlock.app;

import android.app.Application;

import es.ulpgc.miguel.smartlock.home.HomeState;

public class AppMediator extends Application {
  private HomeState homeState;

  @Override
  public void onCreate() {
    super.onCreate();

    // initializing the FirebaseApp
    // todo

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
