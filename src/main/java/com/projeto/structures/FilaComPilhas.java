package com.projeto.structures;

import java.util.NoSuchElementException;

public class FilaComPilhas<T> implements Fila<T> {
    private final Pilha<T> pilhaEntrada = new PilhaEncadeada<>();
    private final Pilha<T> pilhaSaida = new PilhaEncadeada<>();

    @Override
    public void enqueue(T data) {
        pilhaEntrada.push(data);
    }

    @Override
    public T dequeue() {
        prepararSaida();
        return pilhaSaida.pop();
    }

    @Override
    public T peek() {
        prepararSaida();
        return pilhaSaida.peek();
    }

    private void prepararSaida() {
        if (pilhaSaida.isEmpty()) {
            while (!pilhaEntrada.isEmpty()) {
                pilhaSaida.push(pilhaEntrada.pop());
            }
        }
        if (pilhaSaida.isEmpty()) throw new NoSuchElementException("Fila vazia");
    }

    @Override
    public boolean isEmpty() {
        return pilhaEntrada.isEmpty() && pilhaSaida.isEmpty();
    }

    @Override
    public int size() {
        return pilhaEntrada.size() + pilhaSaida.size();
    }
}