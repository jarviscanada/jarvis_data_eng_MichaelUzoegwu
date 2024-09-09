package ca.jrvs.apps.practice.dataStructure.map;

public class Employee {

  private int id;
  private String name;
  private int age;
  private long salary;

  public Employee() {}

  public Employee(int id, String name, int age, long salary){
    this.id = id;
    this.name = name;
    this.age = age;
    this.salary = salary;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}
