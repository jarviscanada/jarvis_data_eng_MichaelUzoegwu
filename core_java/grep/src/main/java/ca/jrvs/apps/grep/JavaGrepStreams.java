package ca.jrvs.apps.grep;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

public interface JavaGrepStreams {

  /**
   * Recursively traverses a given directory and returns all files. If {@code rootDir} is a file,
   * returns a {@link Stream} only containing the {@link File} at the path specified by
   * {@code rootDir}.
   *
   * @param rootDir File path of input directory or file.
   * @return A {@link Stream} of files with all files under {@code rootDir}.
   */
  Stream<File> listFilesStream(String rootDir);

  /**
   * Reads a file and return all of its lines.
   *
   * @param inputFile {@link File} to be read
   * @return {@link Stream} of lines in the file.
   * @throws IllegalArgumentException If a given input file does not exist.
   */
  Stream<String> readLinesStream(File inputFile) throws IllegalArgumentException;
}