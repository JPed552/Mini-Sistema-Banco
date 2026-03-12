package com.projeto.structures;

public class TabelaHash<K, V> implements Hash<K, V> {

    private static class Entrada<K, V> {
        K chave;
        V valor;

        Entrada(K chave, V valor) {
            this.chave = chave;
            this.valor = valor;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Entrada)) return false;
            Entrada<?, ?> outra = (Entrada<?, ?>) o;
            return chave.equals(outra.chave);
        }
    }

    private ListaEncadeada<Entrada<K, V>>[] tabela;
    private int capacidade;
    private int tamanho = 0;
    private static final double FATOR_CARGA_LIMITE = 0.75;

    @SuppressWarnings("unchecked")
    public TabelaHash(int capacidadeInicial) {
        this.capacidade = capacidadeInicial;
        this.tabela = new ListaEncadeada[capacidade];
        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new ListaEncadeada<>();
        }
    }

    public TabelaHash() {
        this(16);
    }

    private int hash(K chave) {
        // Bitwise AND para prevenir índices negativos e garantir distribuição
        return (chave.hashCode() & 0x7fffffff) % capacidade;
    }

    @Override
    public void inserir(K chave, V valor) {
        if ((double) tamanho / capacidade >= FATOR_CARGA_LIMITE) {
            rehash();
        }

        int indice = hash(chave);
        ListaEncadeada<Entrada<K, V>> lista = tabela[indice];

        for (Entrada<K, V> entrada : lista) {
            if (entrada.chave.equals(chave)) {
                entrada.valor = valor;
                return;
            }
        }

        lista.adicionarInicio(new Entrada<>(chave, valor));
        tamanho++;
    }

    @Override
    public V buscar(K chave) {
        int indice = hash(chave);
        ListaEncadeada<Entrada<K, V>> lista = tabela[indice];

        for (Entrada<K, V> entrada : lista) {
            if (entrada.chave.equals(chave)) {
                return entrada.valor;
            }
        }
        return null;
    }

    @Override
    public V remover(K chave) {
        int indice = hash(chave);
        ListaEncadeada<Entrada<K, V>> lista = tabela[indice];

        Entrada<K, V> alvo = null;
        for (Entrada<K, V> entrada : lista) {
            if (entrada.chave.equals(chave)) {
                alvo = entrada;
                break;
            }
        }

        if (alvo != null) {
            lista.remover(alvo); // Requer que Entrada tenha equals()
            tamanho--;
            return alvo.valor;
        }
        return null;
    }

    @Override
    public int tamanho() { return tamanho; }

    @Override
    public boolean estaVazia() { return tamanho == 0; }

    @SuppressWarnings("unchecked")
    private void rehash() {
        ListaEncadeada<Entrada<K, V>>[] antigaTabela = tabela;
        capacidade *= 2;
        tabela = new ListaEncadeada[capacidade];
        tamanho = 0;

        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new ListaEncadeada<>();
        }

        for (ListaEncadeada<Entrada<K, V>> lista : antigaTabela) {
            for (Entrada<K, V> entrada : lista) {
                inserir(entrada.chave, entrada.valor);
            }
        }
    }
}