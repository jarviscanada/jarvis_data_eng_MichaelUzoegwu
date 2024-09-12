package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LambdaStreamTester {

  final static Logger logger = LoggerFactory.getLogger(LambdaStreamPractice.class);

  static <T> void printStream(Stream<T> stream, String label) {
    StringBuilder sb = new StringBuilder();
    LambdaStreamPractice lsp = new LambdaStreamPractice();

    // Label
    sb.append("(): ");
    sb.insert(1, label);

    // Stream
    List<T> streamList = lsp.toList(stream);
    sb.append(streamList);
    logger.info(sb.toString());
  }

  public static void main(String[] args) {
    LambdaStreamPractice lsp = new LambdaStreamPractice();
    BasicConfigurator.configure();

    String[] words = {"The", "Quick", "Brown", "Fox", "Jumps", "Over", "The", "Lazy", "Dog"};
    int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    // Create String Stream
    Stream<String> s = lsp.createStrStream("The", "Quick", "Brown", "Fox");
    Stream<String> sUpper = lsp.toUpperCase("The", "Quick", "Brown", "Fox");

    printStream(s, "Create Stream");
    printStream(sUpper, "Create Stream Uppercase");

    // Filter Stream
    Stream s1 = lsp.createStrStream(words);
    Stream s2 = lsp.createStrStream(words);
    printStream(lsp.createStrStream(words), "Unfiltered Stream");
    printStream(lsp.filter(s1, "e"), "Filter (remove 'e')");
    printStream(lsp.filter(s2, "o.$"), "Filter (remove 'o.$')");

    // Create IntStream
    IntStream s3 = lsp.createIntStream(numbers);
    printStream(s3.boxed(), "Number stream");

    // Stream<String> Conversion to List
    Stream<String> s4 = lsp.createStrStream(words);
    List<String> streamList = lsp.toList(s4);
    logger.info("(Word stream converted to list): " + streamList.toString());

    // IntStream Conversion to List
    IntStream s5 = lsp.createIntStream(numbers);
    List<Integer> intStreamList = lsp.toList(s5);
    logger.info("(Int stream 1->10 converted to list): " + intStreamList.toString());

    // Create IntStream range
    IntStream s6 = lsp.createIntStream(15, 25);
    printStream(s6.boxed(), "Create IntStream with range 15->25");

    // Create DoubleStream of Square Roots
    IntStream s7 = lsp.createIntStream(numbers);
    printStream(lsp.squareRootIntStream(s7).boxed(), "Square root of numbers 1->10");

    // Filter even numbers out of stream
    IntStream s8 = lsp.createIntStream(1, 10);
    printStream(lsp.getOdd(s8).boxed(), "Filter out even numbers in range 1->10");

    // Create Printer and print
    Consumer<String> printer = lsp.getLambdaPrinter("<Prefix>", "<Suffix>");
    printer.accept("Printing Surrounded Message");

    // Print Messages with created printer
    logger.info("Printing words  with prefix/suffix printer");
    lsp.printMessages(words, printer);

    // Print Odd numbers with printer
    logger.info("Printing odd numbers 1->10 with prefix/suffix printer");
    lsp.printOdd(lsp.createIntStream(1, 10), printer);

    // Print Nested List Squared
    List<List<Integer>> nestedLists = Arrays.asList(
        Arrays.asList(1, 2, 3, 4),
        Arrays.asList(5, 6, 7, 8),
        Arrays.asList(9, 10, 11, 12)
    );

    Stream<Integer> s9 = lsp.flatNestedInt(nestedLists.stream());
    printStream(s9, "Flattening and squaring [[1,2,3,4] [5,6,7,8] [9,10,11,12]]");
  }
}
