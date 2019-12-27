package es.ulpgc.miguel.smartlock.models;

import java.util.ArrayList;

public class Door {
  private int id;
  private String name, address;
  private String latitude, longitude;
  private boolean open;
  private ArrayList<String> users;

  public Door() {}

  public Door(int id, String name, String address, String latitude, String longitude, boolean open, ArrayList<String> users) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.latitude = latitude;
    this.longitude = longitude;
    this.open = open;
    this.users = users;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public boolean isOpen() {
    return open;
  }

  public void setOpen(boolean open) {
    this.open = open;
  }

  public ArrayList<String> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<String> users) {
    this.users = users;
  }
}
