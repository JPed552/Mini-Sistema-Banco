package com.projeto.controller;

import com.projeto.model.ContaBancaria;
import com.projeto.structures.*;

import java.util.List;

public class BancoController {
    private TabelaHash<String, ContaBancaria> mapaContas;
    private HeapBinaria filaAtendimento;
    private Pilha<String> historicoOperacoes;
    private Arvore<ContaBancaria> arvoreContas;
    private Fila<String> filaTransacoesPendentes;

    public BancoController() {
        this.mapaContas = new TabelaHash<>(100);
        this.filaAtendimento = new HeapBinaria(100);
        this.historicoOperacoes = new PilhaEncadeada<>();
        this.arvoreContas = new ArvoreAVL<>();
        this.filaTransacoesPendentes = new FilaComPilhas<>();
    }

    public void cadastrarConta(String cpf, String nome, int prioridade, double saldoInicial) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
            System.out.println("Erro: CPF inválido. Deve conter exatamente 11 dígitos numéricos.");
            return;
        }

        if (mapaContas.buscar(cpf) != null) {
            System.out.println("Erro: Conta (CPF) já cadastrada.");
            return;
        }

        ContaBancaria novaConta = new ContaBancaria(cpf, nome, prioridade, saldoInicial);
        mapaContas.inserir(cpf, novaConta);
        arvoreContas.inserir(novaConta);
        historicoOperacoes.push("Conta criada: " + cpf);
        System.out.println("Conta cadastrada com sucesso!");
    }

    public void entrarNaFila(String cpf) {
        ContaBancaria conta = mapaContas.buscar(cpf);
        if (conta == null) {
            System.out.println("Erro: Cliente não possui conta neste banco.");
            return;
        }
        filaAtendimento.inserir(conta);
        System.out.println("Cliente " + conta.getTitular() + " entrou na fila de atendimento físico.");
    }

    public ContaBancaria atenderProximo() {
        if (filaAtendimento.estaVazia()) {
            System.out.println("Nenhum cliente na fila de atendimento.");
            return null;
        }
        ContaBancaria proximo = filaAtendimento.remover();
        historicoOperacoes.push("Atendimento físico realizado para: " + proximo.getNumero());
        return proximo;
    }

    public void agendarTransacao(String descricao) {
        filaTransacoesPendentes.enqueue(descricao);
        System.out.println("Transação agendada: " + descricao);
        historicoOperacoes.push("Transação adicionada à fila: " + descricao);
    }

    public void processarProximaTransacao() {
        if (filaTransacoesPendentes.isEmpty()) {
            System.out.println("Nenhuma transação pendente no momento.");
            return;
        }

        String transacao = filaTransacoesPendentes.dequeue();

        try {
            String[] partes = transacao.split(";");
            String cpf = partes[0];
            double valor = Double.parseDouble(partes[1]);

            ContaBancaria conta = mapaContas.buscar(cpf);

            if (conta != null) {
                if (valor > 0) {
                    conta.depositar(valor);
                    System.out.println("Depósito de R$ " + valor + " processado para: " + conta.getTitular());
                    historicoOperacoes.push("Sucesso: Depósito CPF " + cpf + " valor " + valor);
                } else {
                    double valorPositivo = Math.abs(valor);
                    boolean conseguiuSacar = conta.sacar(valorPositivo);

                    if (conseguiuSacar) {
                        System.out.println("Saque de R$ " + valorPositivo + " processado para: " + conta.getTitular());
                        historicoOperacoes.push("Sucesso: Saque CPF " + cpf + " valor " + valorPositivo);
                    } else {
                        System.out.println("ALERTA: O saque foi cancelado por falta de saldo.");
                        historicoOperacoes.push("FALHA: Saque negado (Saldo insuficiente) - CPF " + cpf);
                    }
                }
            } else {
                System.out.println("Erro: Conta com CPF " + cpf + " não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar transação. Formato esperado: 'CPF;VALOR'.");
        }
    }

    public ContaBancaria buscarConta(String cpf) {
        return mapaContas.buscar(cpf);
    }

    public void listarContasOrdenadas() {
        if (arvoreContas.estaVazia()) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }

        System.out.println("\n--- RELATÓRIO DE CONTAS (ÁRVORE AVL) ---");
        List<ContaBancaria> contas = arvoreContas.emOrdem();
        for (ContaBancaria c : contas) {
            System.out.println("CPF: " + c.getNumero() + " | Titular: " + c.getTitular() + " | Saldo: " + c.getSaldo());
        }
        System.out.println("----------------------------------------\n");
    }

    public void desfazerUltimaAcao() {
        if (historicoOperacoes.isEmpty()) {
            System.out.println("Nenhuma ação no histórico.");
            return;
        }
        System.out.println("Histórico: " + historicoOperacoes.pop());
        System.out.println("[Aviso: O estorno real de dados não está implementado]");
    }

    public void registrarTransacaoDeAtendimento(ContaBancaria conta, double valor) {
        if (conta == null) return;

        String comando = conta.getNumero() + ";" + valor;

        this.agendarTransacao(comando);

        System.out.println("Atendimento registado para: " + conta.getTitular());
        System.out.println("Transação de R$ " + valor + " enviada para a fila de processamento.");
    }
    public void excluirConta(String cpf) {
        ContaBancaria conta = mapaContas.buscar(cpf);
        if (conta != null) {
            mapaContas.remover(cpf);
            arvoreContas.remover(conta);
            historicoOperacoes.push("Conta removida: " + cpf);
            System.out.println("Conta excluída com sucesso.");
        } else {
            System.out.println("Erro: Conta não encontrada.");
        }
    }
}