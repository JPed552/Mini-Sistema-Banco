package com.projeto.structures;

public interface Pilha<T> extends Iterable<T>{
    void push(T data);
    T pop();
    T peek();
    boolean isEmpty();
    int size();
}
