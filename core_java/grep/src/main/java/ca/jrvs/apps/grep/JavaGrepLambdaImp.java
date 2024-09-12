package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp{

  @Override
  public List<String> readLines(File inputFile) throws IllegalArgumentException {
    if (!inputFile.exists()) throw new IllegalArgumentException("Cannot read non-existent file.");

    try (Stream<String> file = Files.lines(inputFile.toPath(), StandardCharsets.UTF_8)) {
      return file.collect(Collectors.toList());
    }
    catch (UncheckedIOException|IOException ex) {
      logger.warn("Skipping file (could not read) " + inputFile.getPath());
      return new ArrayList<>();
    }
  }

  @Override
  public List<File> listFiles(String rootDir) {
    File rootFile = new File(rootDir);

    if (!rootFile.exists())
      return new ArrayList<>();

    if (!rootFile.isDirectory())
      return Arrays.asList(rootFile);

    ArrayList<File> outFiles = new ArrayList<>();
    ArrayDeque<File> directories = new ArrayDeque<>();
    directories.add(rootFile);

    while (!directories.isEmpty()) {
      File curDir = directories.pop();

      try (Stream<Path> filePaths = Files.list(curDir.toPath())) {
        filePaths
            .map((filePath) -> new File(filePath.toString()))
            .forEach((file) -> {
              if (file.isDirectory()) directories.push((file));
              else outFiles.add(file);
            });
      } catch (IOException ex) {
        logger.error("Could not list files in nested directory " + curDir.getPath(), ex);
      }
    }

    return outFiles;
  }
}
