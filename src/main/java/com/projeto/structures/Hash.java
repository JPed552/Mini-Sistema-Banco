package com.projeto.structures;

public interface Hash<K, V> {
    void inserir(K chave, V valor);
    V buscar(K chave);
    V remover(K chave);
    int tamanho();
    boolean estaVazia(); // Essencial para o Controller
}