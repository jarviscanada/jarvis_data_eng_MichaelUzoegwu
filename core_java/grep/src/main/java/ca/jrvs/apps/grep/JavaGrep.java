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
   * Creates a {@code BufferedReader} to read lines 1 at a time from {@code inputFile}.
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
   * Creates a {@code BufferedWriter} to write lines one at a time
   * Output file is overwritten if it already exists
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
