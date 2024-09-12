package ca.jrvs.apps.grep;

import java.io.File;
import java.util.List;
import org.apache.log4j.BasicConfigurator;

public class JavaGrepLambdaApp extends JavaGrepApp {

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    BasicConfigurator.configure();

    JavaGrepLambdaImp  javaGrepLambdaImp = new JavaGrepLambdaImp();
    javaGrepLambdaImp.setRegex(args[0]);
    javaGrepLambdaImp.setRootPath(args[1]);
    javaGrepLambdaImp.setOutFile(args[2]);

    try {
      javaGrepLambdaImp.process();
    } catch (Exception ex) {
      logger.error("Error: Unable to process", ex);
    }
  }
}
