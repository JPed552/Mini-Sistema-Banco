package com.projeto.model;

import java.util.Objects;


public class ContaBancaria implements Comparable<ContaBancaria> {
    private String numero;
    private String titular;
    private int prioridade; // 1: VIP, 2: Comum
    private double saldo;

    public ContaBancaria(String numero, String titular, int prioridade, double saldo) {
        this.numero = numero;
        this.titular = titular;
        this.prioridade = prioridade;
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContaBancaria que = (ContaBancaria) o;
        return Objects.equals(numero, que.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public int compareTo(ContaBancaria outra) {
        return this.numero.compareTo(outra.numero);
    }

    // Getters...
    public int getPrioridade() { return prioridade; }
    public String getNumero() { return numero; }
    public String getTitular() { return titular;}
    public double getSaldo() { return saldo;}
}