package com.projeto.structures;

public class ArvoreAVL<T extends Comparable<T>> implements Arvore<T>{

    private class No {
        T valor;
        No esquerda;
        No direita;
        int altura;

        No(T valor) {
            this.valor = valor;
            this.altura = 1;
        }
    }

    private No raiz;

    private int altura(No no) {
        return (no == null) ? 0 : no.altura;
    }

    private int fatorBalanceamento(No no) {
        return (no == null) ? 0 : altura(no.esquerda) - altura(no.direita);
    }

    private No rotacaoDireita(No y) {
        No x = y.esquerda;
        No t2 = x.direita;

        x.direita = y;
        y.esquerda = t2;

        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    private No rotacaoEsquerda(No x) {
        No y = x.direita;
        No t2 = y.esquerda;

        y.esquerda = x;
        x.direita = t2;

        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    public void inserir(T valor) {
        raiz = inserir(raiz, valor);
    }

    private No inserir(No no, T valor) {

        if (no == null)
            return new No(valor);

        if (valor.compareTo(no.valor) < 0)
            no.esquerda = inserir(no.esquerda, valor);
        else if (valor.compareTo(no.valor) > 0)
            no.direita = inserir(no.direita, valor);
        else
            return no;

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));

        int balanceamento = fatorBalanceamento(no);

        if (balanceamento > 1 && valor.compareTo(no.esquerda.valor) < 0)
            return rotacaoDireita(no);

        if (balanceamento < -1 && valor.compareTo(no.direita.valor) > 0)
            return rotacaoEsquerda(no);

        if (balanceamento > 1 && valor.compareTo(no.esquerda.valor) > 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && valor.compareTo(no.direita.valor) < 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public boolean buscar(T valor) {
        return buscar(raiz, valor);
    }

    private boolean buscar(No no, T valor) {

        if (no == null)
            return false;

        int comparacao = valor.compareTo(no.valor);

        if (comparacao == 0)
            return true;

        if (comparacao < 0)
            return buscar(no.esquerda, valor);

        return buscar(no.direita, valor);
    }

    public void emOrdem() {
        emOrdem(raiz);
    }

    private void emOrdem(No no) {

        if (no != null) {
            emOrdem(no.esquerda);
            System.out.println(no.valor);
            emOrdem(no.direita);
        }
    }
    @Override
    public boolean estaVazia() {
        return raiz == null;
    }
    @Override
    public void remover(T valor) {
        raiz = remover(raiz, valor);
    }

    private No menor(No no) {

        No atual = no;

        while (atual.esquerda != null)
            atual = atual.esquerda;

        return atual;
    }

    private No remover(No no, T valor) {

        if (no == null)
            return null;

        if (valor.compareTo(no.valor) < 0)
            no.esquerda = remover(no.esquerda, valor);

        else if (valor.compareTo(no.valor) > 0)
            no.direita = remover(no.direita, valor);

        else {

            if (no.esquerda == null || no.direita == null) {

                No temp = (no.esquerda != null) ? no.esquerda : no.direita;

                if (temp == null)
                    no = null;
                else
                    no = temp;

            } else {

                No sucessor = menor(no.direita);
                no.valor = sucessor.valor;
                no.direita = remover(no.direita, sucessor.valor);
            }
        }

        if (no == null)
            return null;

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));

        int balanceamento = fatorBalanceamento(no);

        if (balanceamento > 1 && fatorBalanceamento(no.esquerda) >= 0)
            return rotacaoDireita(no);

        if (balanceamento > 1 && fatorBalanceamento(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && fatorBalanceamento(no.direita) <= 0)
            return rotacaoEsquerda(no);

        if (balanceamento < -1 && fatorBalanceamento(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }
}