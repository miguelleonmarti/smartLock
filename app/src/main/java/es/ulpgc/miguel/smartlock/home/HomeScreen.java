package es.ulpgc.miguel.smartlock.home;

import java.lang.ref.WeakReference;

import androidx.fragment.app.FragmentActivity;
import es.ulpgc.miguel.smartlock.app.AppMediator;

public class HomeScreen {

  public static void configure(HomeContract.View view) {

    WeakReference<FragmentActivity> context =
        new WeakReference<>((FragmentActivity) view);

    AppMediator mediator = (AppMediator) context.get().getApplication();
    HomeState state = mediator.getHomeState();

    HomeContract.Router router = new HomeRouter(mediator);
    HomeContract.Presenter presenter = new HomePresenter(state);
    HomeContract.Model model = new HomeModel();
    presenter.injectModel(model);
    presenter.injectRouter(router);
    presenter.injectView(new WeakReference<>(view));

    view.injectPresenter(presenter);

  }
}
