package com.projeto.structures;

public class PilhaEncadeada<T> implements Pilha<T> {
    private final ListaEncadeada<T> lista = new ListaEncadeada<>();

    @Override
    public void push(T data) { lista.adicionarInicio(data); }

    @Override
    public T pop() { return lista.removerInicio(); }

    @Override
    public T peek() { return lista.primeiro(); }

    @Override
    public boolean isEmpty() { return lista.estaVazia(); }

    @Override
    public int size() { return lista.tamanho(); }
}