package com.projeto.structures;

import java.util.List;

public interface Arvore<T> {

    void inserir(T valor);

    boolean buscar(T valor);

    List<T> emOrdem();

    void remover(T valor);

    boolean estaVazia();

    int getAltura();
}