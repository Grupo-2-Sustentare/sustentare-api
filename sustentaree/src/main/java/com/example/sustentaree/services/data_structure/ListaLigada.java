package com.example.sustentaree.services.data_structure;

import java.util.ArrayList;
import java.util.List;

public class ListaLigada<T>{
 private Node head;

  public ListaLigada(){
    this.head = new Node<T>(null);
  }

  public void insert(T info){
    Node node = new Node<T>(info);
    node.setNext(head.getNext());
    head.setNext(node);
  }

  public void print(){
    Node actual = head.getNext();
    for(int i = 0; actual != null; i++){
      System.out.println(actual.getInfo());
      actual = actual.getNext();
    }
  }

  public List<T> getAllNodeValues(){
    List<T> nodes = new ArrayList<>();
    Node<T> actual = head.getNext();
    for(int i = 0; actual != null; i++){
      nodes.add((T) actual.getInfo());
      actual = actual.getNext();
    }
    return nodes;
  }

  public Node findNode(T info){
    Node actual = head.getNext();
    for (int i = 0; actual != null; i++){
      if (actual.getInfo().equals(info)){
        return actual;
      }
      actual = actual.getNext();
    }
    return null;
  }

  public boolean removeNode(int info){
    Node past = head;
    Node actual = head.getNext();
    for(int i = 0; actual != null; i++){
      if (actual.getInfo().equals(info)){
        past.setNext(actual.getNext());
        return true;
      }
      past = actual;
      actual = actual.getNext();
    }
    return false;
  }

  public int getSize(){
    Node actual = head.getNext();
    int size = 0;
    for(int i = 0; actual != null; i++){
      size++;
      actual = actual.getNext();
    }
    return size;
  }

 public Node getHead() {
  return head;
 }
}
