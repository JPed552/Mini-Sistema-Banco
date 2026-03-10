package com.projeto.structures;

import java.util.NoSuchElementException;

public class ListaEncadeada<T> {
    private Node<T> head;
    private int size = 0;

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    public void adicionarInicio(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public T removerInicio() {
        if (estaVazia()) throw new NoSuchElementException("Lista vazia");
        T data = head.data;
        head = head.next;
        size--;
        return data;
    }

    public T primeiro() {
        if (estaVazia()) throw new NoSuchElementException("Lista vazia");
        return head.data;
    }

    public boolean estaVazia() { return head == null; }
    public int tamanho() { return size; }
}
