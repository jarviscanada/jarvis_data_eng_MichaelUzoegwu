package ca.jrvs.apps.practice;

import ca.jrvs.apps.practice.dataStructure.map.Employee;
import ca.jrvs.apps.practice.dataStructure.map.JHashMap;
import ca.jrvs.apps.practice.dataStructure.map.JMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HelloWorld {
  public static void main(String[] args) {

    //testingRegex(args);
    hashMapPractice(args);
  }

  private static void hashMapPractice(String[] args) {
    JMap<Employee, List<String>> empStrMap = new JHashMap<>();

    //Amy
    Employee amy = new Employee(1, "Amy", 25, 45000);
    List<String> amyPreviousCompanies = Arrays.asList("TDL", "RBC", "CIBC");
    empStrMap.put(amy, amyPreviousCompanies);

    //Bob
    Employee bob = new Employee(1, "Bob", 18, 40000);
    List<String> bobPreviousCompanies = Arrays.asList("A&W", "Superstore");
    empStrMap.put(bob, bobPreviousCompanies);

    System.out.println(empStrMap.get(bob));

//    for (Employee emp : empStrMap.keySet()){
//      System.out.println("Hashcode: " + emp.hashCode());
//      System.out.println("Value: " + empStrMap.get(emp).toString());
//      System.out.println("-----------");
//    }
  }
  private static void testingRegex(String[] args) {
    RegexPractice rp = new RegexPractice();

    // Testing Regex
    String emptyLine = "";
    System.out.println("Matching empty line: " + rp.isEmptyLine(emptyLine));

    String jpgFile = "Star.jpeg";
    System.out.println("Matching jpg file: " + rp.matchJpeg(jpgFile));

    String ip = "255.0.0.0";
    System.out.println("Matching ip address: " + rp.matchIp(ip));
  }
}