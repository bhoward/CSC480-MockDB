package edu.depauw.csc480.mockdb;

import edu.depauw.csc480.mockdb.sim.EventLoop;

public class Main {

  public static void main(String[] args) {
    EventLoop loop = new EventLoop();
    loop.schedule(new DemoEvent(10, "Hello"));
    loop.schedule(new DemoEvent(20, "World"));
    loop.run();
  }

}
