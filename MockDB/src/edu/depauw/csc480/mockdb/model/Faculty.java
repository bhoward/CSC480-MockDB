package edu.depauw.csc480.mockdb.model;

import java.util.ArrayList;
import java.util.Collection;

public class Faculty {
  private String name;
  private Department department;
  private int years;

  private Collection<Section> sections;

  public Faculty(String name, Department department, int years) {
    this.name = name;
    this.department = department;
    this.years = years;

    this.sections = new ArrayList<>();

    department.addFaculty(this);
  }

  public String getName() {
    return name;
  }

  public Department getDepartment() {
    return department;
  }

  public int getYears() {
    return years;
  }

  public void addSection(Section section) {
    sections.add(section);
  }
}
