package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.model.Department;

public class CreateDepartmentEvent extends AbstractEvent {
  private String name;
  
  public CreateDepartmentEvent(int time, String name) {
    super(time);
    this.name = name;
  }

  @Override
  public void perform(EventLoop loop) {
    Department department = new Department(name);
    // now schedule hiring faculty, creating courses, and assigning sections each year
    
    
  }

}
