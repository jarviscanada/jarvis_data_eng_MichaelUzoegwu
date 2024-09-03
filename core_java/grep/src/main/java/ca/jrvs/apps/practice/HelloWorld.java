package ca.jrvs.apps.practice;

class HelloWorld {
  public static void main(String[] args) {
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