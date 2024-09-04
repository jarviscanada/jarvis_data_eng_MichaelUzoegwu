package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

  private final Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

  private String regex;
  private String rootPath;
  private String outFile;
  private Pattern pattern;

  @Override
  public void process() throws IOException {
    ArrayList<String> matchedLines = new ArrayList<>();

    for (File file : listFiles(rootPath)){
      List<String> lines = readLines(file);
      for (String line : readLines(file)) {
        if(containsPattern(line)) matchedLines.add(line);
      }
    }

    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    File rootFile = new File(rootDir);
    ArrayList<File> outFiles = new ArrayList<>();

    if (!rootFile.exists())
      return new ArrayList<>();

    if (!rootFile.isDirectory()){
      outFiles.add(rootFile);
      return outFiles;
    }

    ArrayDeque<File> directories = new ArrayDeque<>();
    directories.add(rootFile);

    while (!directories.isEmpty()) {
      File curDir = directories.pop();
      File[] files = curDir.listFiles();

      for (File file : files) {
        if (file.isDirectory())
          directories.push(file);
        else
          outFiles.add(file);
      }
    }

    return outFiles;
  }

  @Override
  public List<String> readLines(File inputFile) throws IllegalArgumentException {
    if (!inputFile.exists()) throw new IllegalArgumentException("Cannot read non-existent file.");

    ArrayList<String> lines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      String currentLine = reader.readLine();
      while (currentLine != null){
        lines.add(currentLine);
        currentLine = reader.readLine();
      }
    } catch (IOException e) {
      logger.error("Could not read file: " + inputFile.getName());
    }

    return lines;
  }

  @Override
  public boolean containsPattern(String line) {
    Matcher m = this.pattern.matcher(line);
    return m.find();
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    File outFileFile = new File(outFile);

    try (BufferedWriter writer = new BufferedWriter( new FileWriter(outFileFile))){
      for (String line : lines){
        writer.append(line);
        writer.newLine();
      }
    } catch (IOException ex) {
      logger.error("Could not write output to: " + outFileFile.getName(), ex);
    }
  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
    this.pattern = Pattern.compile(this.regex);
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }
}
