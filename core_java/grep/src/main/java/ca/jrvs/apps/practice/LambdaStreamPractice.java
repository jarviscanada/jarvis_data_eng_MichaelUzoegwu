package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class LambdaStreamPractice implements LambdaStreamExc{

  @Override
  public Stream<String> createStrStream(String... strings) {
    return Arrays.stream(strings);
  }

  @Override
  public Stream<String> toUpperCase(String... strings) {
    return createStrStream(strings)
        .map((s) -> s.toUpperCase());
  }

  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    Pattern compiledPattern = Pattern.compile(pattern);

    return stringStream.filter((s) -> {
      Matcher matcher = compiledPattern.matcher(s);
      return !matcher.find();
    });
  }

  @Override
  public IntStream createIntStream(int[] arr) {
    return IntStream.of(arr);
  }

  @Override
  public <E> List<E> toList(Stream<E> stream) {
    List<E> list = new ArrayList<>();
    stream.forEach((e) -> list.add(e));
    stream.close();
    return list;
  }

  @Override
  public List<Integer> toList(IntStream intStream) {
    return toList(intStream.boxed());
  }

  @Override
  public IntStream createIntStream(int start, int end) {
    int seqLength = end - start + 1;
    int[] sequence = new int[seqLength];

    for (int i = 0 ; i < seqLength;  i++){
      sequence[i] = start + i;
    }

    return IntStream.of(sequence);
  }

  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {
    return intStream.asDoubleStream()
        .map(Math::sqrt);
  }

  @Override
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter((i) -> i % 2 != 0);
  }

  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    return new Consumer<String>() {
      @Override
      public void accept(String s) {
        LambdaStreamTester.logger.info(prefix + " " + s + " " + suffix);
      }
    };
  }

  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {
    createStrStream(messages).forEach(printer::accept);
  }

  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    getOdd(intStream)
        .forEach((i) -> printer.accept(Integer.toString(i)));
  }

  @Override
  public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
    ArrayList<Integer> flattened = new ArrayList<>();
    ints.forEach((list) -> {
      list.forEach((i) -> flattened.add(i));
    });

    return flattened.stream()
        .map((i) -> (int) Math.pow(i, 2));
  }
}
