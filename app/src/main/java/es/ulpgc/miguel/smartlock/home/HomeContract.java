package es.ulpgc.miguel.smartlock.home;

import java.lang.ref.WeakReference;

import es.ulpgc.miguel.smartlock.services.FirebaseContract;

interface HomeContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void displayData(HomeViewModel viewModel);
  }

  interface Presenter {
    void injectView(WeakReference<View> view);

    void injectModel(Model model);

    void injectRouter(Router router);

    void processRequest(String uid);
  }

  interface Model {
    void processRequest(String uid, FirebaseContract.ProcessRequest callback);
  }

  interface Router {
    void navigateToNextScreen();

    void passDataToNextScreen(HomeState state);

    HomeState getDataFromPreviousScreen();
  }
}
