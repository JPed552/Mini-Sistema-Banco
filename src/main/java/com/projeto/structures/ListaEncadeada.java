package com.projeto.structures;

import java.util.NoSuchElementException;

public class ListaEncadeada<T> implements Iterable<T>{
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

    public boolean remover(T data) {

        Node<T> atual = head;
        Node<T> anterior = null;

        while (atual != null) {

            if (atual.data.equals(data)) {

                if (anterior == null) {
                    head = atual.next;
                } else {
                    anterior.next = atual.next;
                }

                size--;

                return true;
            }

            anterior = atual;
            atual = atual.next;
        }

        return false;
    }

    public T primeiro() {
        if (estaVazia()) throw new NoSuchElementException("Lista vazia");
        return head.data;
    }

    public boolean estaVazia() { return head == null; }
    public int tamanho() { return size; }

    @Override
    public java.util.Iterator<T> iterator() {

        return new java.util.Iterator<T>() {

            Node<T> atual = head;

            public boolean hasNext() {
                return atual != null;
            }

            public T next() {
                T data = atual.data;
                atual = atual.next;
                return data;
            }
        };
    }
}
