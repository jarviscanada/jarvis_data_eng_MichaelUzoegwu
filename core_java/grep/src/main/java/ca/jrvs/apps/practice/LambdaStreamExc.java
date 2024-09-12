package ca.jrvs.apps.practice;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface LambdaStreamExc {

  /**
   * Creates a {@link String} stream from an array.
   *
   * @param strings An array of {@link String} to be included in the {@link Stream}.
   * @return A {@link Stream} of strings.
   */
  Stream<String> createStrStream(String... strings);

  /**
   * Converts a {@link String} array into a {@link Stream} with each string in uppercase.
   *
   * @param strings An array of {@link String} to be transformed to uppercase.
   * @return A {@link Stream} containing the input strings in uppercase.
   */
  Stream<String> toUpperCase(String... strings);

  /**
   * Filters strings that contain a RegEx pattern.
   *
   * @param stringStream Input {@link Stream} of strings.
   * @param pattern      RegEx pattern used to filter out strings.
   * @return A {@link Stream} of strings that do not contain the RegEx pattern.
   */
  Stream<String> filter(Stream<String> stringStream, String pattern);

  /**
   * Creates an {@link IntStream} from an Array of {@code int} values.
   *
   * @param arr
   * @return {@link IntStream}
   */
  IntStream createIntStream(int[] arr);

  /**
   * Converts a {@link Stream} to a list.
   *
   * @param stream
   * @param <E>
   * @return {@link List}
   */
  <E> List<E> toList(Stream<E> stream);


  /**
   * Converts an {@link IntStream} to a list.
   *
   * @param intStream
   * @return {@link List}
   */
  List<Integer> toList(IntStream intStream);

  /**
   * Creates an IntStream with values from {@code start} to {@code end} inclusive.
   *
   * @param start
   * @param end
   * @return {@link IntStream}
   */
  IntStream createIntStream(int start, int end);

  /**
   * Converts an {@link IntStream} to a {@link DoubleStream} and computes the square root of each
   * element.
   *
   * @param intStream
   * @return {@link DoubleStream}
   */
  DoubleStream squareRootIntStream(IntStream intStream);

  /**
   * Filters out all even number from an {@link IntStream}.
   *
   * @param intStream
   * @return {@link IntStream}
   */
  IntStream getOdd(IntStream intStream);

  /**
   * Returns a lambda function that print a message with a prefix and suffix. This lambda can be
   * useful to format logs.
   *
   * <pre><code>
   *   Consumer&lt;String&gt; printer = getLambdaPrinter("(Lambda Printer)", "[INFO]");
   *   printer.accept("This is a message");
   * </code></pre>
   *
   * @param prefix Text to be added before the printed message.
   * @param suffix Text to be added after the printed message.
   * @return Printer that prints a given {@link String}.
   */
  Consumer<String> getLambdaPrinter(String prefix, String suffix);

  /**
   * Prints multiple messages with a given printer.
   *
   * <pre><code>
   *  String[] messages = {"a", "b", "c"};
   *  printMessages(messages, getLambdaPrinter("msg:", "!"));
   * </code></pre>
   *
   * @param messages
   * @param printer
   */
  void printMessages(String[] messages, Consumer<String> printer);

  /**
   * Prints all odd number from an {@link IntStream} with a given printer.
   *
   * @param intStream
   * @param printer
   */
  void printOdd(IntStream intStream, Consumer<String> printer);

  /**
   * Squares each number from the input {@link Stream} of {@link List}&lt;{@link Integer}&gt;.
   * <br>Returns a stream with input lists transformed and flattened.
   *
   * @param ints A {@link Stream} of {@link List}&lt;{@link Integer}&gt; containing integers to be
   *             squared.
   * @return A flattened {@link Stream} of {@link Integer} where each integer from the input lists
   * is squared.
   */
  Stream<Integer> flatNestedInt(Stream<List<Integer>> ints);
}
