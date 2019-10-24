package es.ulpgc.miguel.smartlock.home;

import java.lang.ref.WeakReference;

import es.ulpgc.miguel.smartlock.services.FirebaseContract;

public class HomePresenter implements HomeContract.Presenter {

  public static String TAG = HomePresenter.class.getSimpleName();

  private WeakReference<HomeContract.View> view;
  private HomeViewModel viewModel;
  private HomeContract.Model model;
  private HomeContract.Router router;

  public HomePresenter(HomeState state) {
    viewModel = state;
  }

  @Override
  public void injectView(WeakReference<HomeContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(HomeContract.Model model) {
    this.model = model;
  }

  @Override
  public void injectRouter(HomeContract.Router router) {
    this.router = router;
  }

  @Override
  public void processRequest(String uid) {
    model.processRequest(uid, new FirebaseContract.ProcessRequest() {
      @Override
      public void onProcessedRequest(boolean error) {
        if (!error) {
          // no errors, successful request
        } else {
          // something wrong happened
        }
      }
    });
  }
}
