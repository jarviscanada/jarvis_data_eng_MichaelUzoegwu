package ca.jrvs.apps.practice.dataStructure.bst;

import java.util.ArrayList;
import java.util.List;

public class BST<K extends Comparable<K>, V> {

  private class Node<K extends Comparable<K>, V> {

    final K key;
    final V value;
    Node left;
    Node right;

    Node(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  Node<K, V> root;

  public BST() {
  }

  public BST(K key, V value) {
    this.root = new Node(key, value);
  }

  public void add(K key, V value) {
    if (root == null) {
      root = new Node<K, V>(key, value);
      return;
    }

    Node<K, V> parentNode = null;
    Node<K, V> curNode = root;
    while (curNode != null) {
      parentNode = curNode;

      if (curNode.key.compareTo(key) > 0) {
        curNode = curNode.left;
      } else if (curNode.key.compareTo(key) < 0) {
        curNode = curNode.right;
      }
    }

    Node<K, V> newNode = new Node<K, V>(key, value);
    if (parentNode.key.compareTo(key) > 0) {
      parentNode.left = newNode;
    } else if (parentNode.key.compareTo(key) < 0) {
      parentNode.right = newNode;
    }
  }

  public V get(K key) {
    Node<K, V> curNode = root;

    while (curNode != null) {
      if (curNode.key.compareTo(key) > 0) {
        curNode = curNode.left;
      } else if (curNode.key.compareTo(key) < 0) {
        curNode = curNode.right;
      } else {
        return curNode.value;
      }
    }

    return null;
  }

  /**
   * Traverse through the binary search Tree and return the nodes in a particular order specified by
   * {@code mode}. 0 -> pre-order | 1 -> post-order | 2 -> in-order
   *
   * @param mode Determines the order of traversal
   */
  public List<V> traverse(int mode) {

    ArrayList<V> order = new ArrayList<>();
    traverseRecursive(root, order, mode);
    return order;
  }

  private void traverseRecursive(Node<K, V> node, List<V> order, int mode) {
    if (node == null) {
      return;
    }

    if (mode == 0) {
      order.add(node.value);
    }
    traverseRecursive(node.left, order, mode);
    if (mode == 2) {
      order.add(node.value);
    }
    traverseRecursive(node.right, order, mode);
    if (mode == 1) {
      order.add(node.value);
    }
  }


}
