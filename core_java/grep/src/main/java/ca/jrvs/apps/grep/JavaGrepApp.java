package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepApp {

  final static Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  public static void main(String[] args) {
    if (args.length < 3 || args.length > 4) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    JavaGrepImp javaGrepImp;
    if (args.length == 4 && args[3].equals("--streams")) {
      javaGrepImp = new JavaGrepLambdaImp();
    } else {
      javaGrepImp = new JavaGrepImp();
    }

    BasicConfigurator.configure();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      logger.error("Error: Unable to process", ex);
    }
  }
}