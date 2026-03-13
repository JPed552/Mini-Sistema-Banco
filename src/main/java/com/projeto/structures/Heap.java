package com.projeto.structures;


public interface Heap<T> {

    void inserir(T elemento);

    T remover();

    T topo();

    boolean estaVazia();

    int tamanho();
}