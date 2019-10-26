package es.ulpgc.miguel.smartlock.home;

public class HomeViewModel {

  private String message;
  private int index = 0;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
