package ca.jrvs.apps.practice;

import ca.jrvs.apps.practice.dataStructure.bst.BST;
import ca.jrvs.apps.practice.dataStructure.map.Employee;
import ca.jrvs.apps.practice.dataStructure.map.JHashMap;
import ca.jrvs.apps.practice.dataStructure.map.JMap;
import java.util.Arrays;
import java.util.List;

class HelloWorld {

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

  private static void BSTPractice(String[] args) {
    BST<Integer, String> bst = new BST<>();
    bst.add(8, "eight");
    bst.add(2, "two");
    bst.add(1, "one");
    bst.add(5, "five");
    bst.add(3, "three");
    bst.add(21, "twenty-one");
    bst.add(13, "thirteen");

    List<String> numbers;

    System.out.println("In order");
    numbers = bst.traverse(2);
    System.out.println(numbers);

    System.out.println("Post order");
    numbers = bst.traverse(1);
    System.out.println(numbers);

    System.out.println("Pre order");
    numbers = bst.traverse(0);
    System.out.println(numbers);

    System.out.println("Getting 5");
    System.out.println(bst.get(5));

    System.out.println("Getting 8");
    System.out.println(bst.get(8));

    System.out.println("Getting 13");
    System.out.println(bst.get(13));

    System.out.println("Getting 21");
    System.out.println(bst.get(21));
  }

  public static void main(String[] args) {

    //testingRegex(args);
    //hashMapPractice(args);
    BSTPractice(args);
  }
}