package ca.jrvs.apps.practice.dataStructure.map;

import java.util.Map;
import java.util.Set;

public class JHashMap<K, V> implements JMap<K, V> {

  private class Node<K, V> {

    public Node next;
    final K key;
    final V value;

    public Node(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  private int size;
  private Node[] table;

  public JHashMap() {
    this(10);
  }

  public JHashMap(int initialCapacity) {
    size = initialCapacity;
    table = new Node[initialCapacity];
  }

  @Override
  public V get(Object key) {
    final int putIndex = getPutIndex(key);

    Node<K, V> curNode = table[putIndex];

    while (curNode != null) {
      if (curNode.key == key || curNode.key.equals(key)) {
        return curNode.value;
      }
      curNode = curNode.next;
    }

    return curNode.value;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean containsKey(Object key) {
    return false;
  }

  @Override
  public V put(K key, V value) {
    int putIndex = getPutIndex(key);
    Node<K, V> putNode = new Node<>(key, value);

    if (table[putIndex] == null) {
      table[putIndex] = putNode;
    } else {
      Node<K, V> curNode = table[putIndex];
      while (curNode.next != null) {
        curNode = curNode.next;
      }
      curNode.next = putNode;
    }

    return value;
  }

  @Override
  public Set<Map.Entry<K, V>> entrySet() {
    return null;
  }

  private int getPutIndex(Object key) {
    return key.hashCode() % size;
  }
}
