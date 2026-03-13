package com.projeto.structures;

import com.projeto.model.ContaBancaria;

public class HeapBinaria implements Heap<ContaBancaria>{
    private ContaBancaria[] heap;
    private int tamanho;
    private int capacidade;

    public HeapBinaria(int capacidade) {
        this.capacidade = capacidade;
        this.heap = new ContaBancaria[capacidade];
        this.tamanho = 0;
    }

    @Override
    public void inserir(ContaBancaria conta) {
        if (tamanho == capacidade) throw new IllegalStateException("Fila cheia");
        heap[tamanho] = conta;
        subir(tamanho);
        tamanho++;
    }

    @Override
    public ContaBancaria remover() {
        if (estaVazia()) return null;
        ContaBancaria raiz = heap[0];
        heap[0] = heap[tamanho - 1];
        tamanho--;
        descer(0);
        return raiz;
    }

    private void subir(int i) {
        while (i > 0 && heap[i].getPrioridade() < heap[(i - 1) / 2].getPrioridade()) {
            trocar(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private void descer(int i) {
        int menor = i;
        int esq = 2 * i + 1;
        int dir = 2 * i + 2;

        if (esq < tamanho && heap[esq].getPrioridade() < heap[menor].getPrioridade()) menor = esq;
        if (dir < tamanho && heap[dir].getPrioridade() < heap[menor].getPrioridade()) menor = dir;

        if (menor != i) {
            trocar(i, menor);
            descer(menor);
        }
    }

    private void trocar(int i, int j) {
        ContaBancaria temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @Override
    public ContaBancaria topo() {
        if (estaVazia()) return null;
        return heap[0];
    }

    @Override
    public int tamanho() {
        return tamanho;
    }

    @Override
    public boolean estaVazia() { return tamanho == 0; }
}