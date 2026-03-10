package com.projeto.structures;

public interface Fila<T> {
    void enqueue(T data);
    T dequeue();
    T peek();
    boolean isEmpty();
    int size();
}
