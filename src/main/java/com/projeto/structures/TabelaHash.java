package com.projeto.structures;

public class TabelaHash<K, V> {

    private class Entrada {
        K chave;
        V valor;

        Entrada(K chave, V valor) {
            this.chave = chave;
            this.valor = valor;
        }
    }

    private ListaEncadeada<Entrada>[] tabela;
    private int capacidade = 16;
    private int tamanho = 0;

    public TabelaHash() {

        tabela = new ListaEncadeada[capacidade];

        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new ListaEncadeada<>();
        }
    }

    private int hash(K chave) {
        return Math.abs(chave.hashCode()) % capacidade;
    }

    public void inserir(K chave, V valor) {

        int indice = hash(chave);

        ListaEncadeada<Entrada> lista = tabela[indice];

        for (Entrada entrada : lista) {

            if (entrada.chave.equals(chave)) {
                entrada.valor = valor;
                return;
            }
        }

        lista.adicionarInicio(new Entrada(chave, valor));

        tamanho++;
    }

    public V buscar(K chave) {

        int indice = hash(chave);

        ListaEncadeada<Entrada> lista = tabela[indice];

        for (Entrada entrada : lista) {

            if (entrada.chave.equals(chave)) {
                return entrada.valor;
            }
        }

        return null;
    }

    public V remover(K chave) {

        int indice = hash(chave);

        ListaEncadeada<Entrada> lista = tabela[indice];

        for (Entrada entrada : lista) {

            if (entrada.chave.equals(chave)) {

                lista.remover(entrada);

                tamanho--;

                return entrada.valor;
            }
        }

        return null;
    }

    public int tamanho() {
        return tamanho;
    }
}