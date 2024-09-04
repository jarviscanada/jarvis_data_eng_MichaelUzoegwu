package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {

  /**
   * Top level search workflow
   * @throws IOException
   */
  void process() throws IOException;

  /**
   * Traverse a given directory and return all files recursively
   * @param rootDir input directory
   * @return files under rootDir
   */
  List<File> listFiles(String rootDir);

  /**
   * Read a file and return all the lines
   *
   * TODO: Explain FileReader, BufferReader, and character encoding
   *
   * @param inputFile  file to be read
   * @return lines
   * @throws IllegalArgumentException if a given input file is not a file
   */
  List<String> readLines(File inputFile) throws IllegalArgumentException;

  /**
   * Check if a line contains the regex pattern (passed by user)
   * @param line input string
   * @return true if there is a match
   */
  boolean containsPattern(String line);

  /**
   * Write lines to a file
   *
   * TODO: Explore FileOutputStream, OutputStreamWriter and Buffered Writer
   *
   * @param lines
   * @throws IOException
   */
  void writeToFile(List<String> lines) throws IOException;

  String getRootPath();

  void setRootPath(String rootPath);

  String getRegex();

  void setRegex(String regex);

  String getOutFile();

  void setOutFile(String outFile);
}
