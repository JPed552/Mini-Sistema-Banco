package com.projeto.structures;

public interface Arvore<T> {

    void inserir(T valor);

    boolean buscar(T valor);

    void remover(T valor);

    boolean estaVazia();
}