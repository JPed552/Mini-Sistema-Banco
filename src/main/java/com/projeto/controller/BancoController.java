package com.projeto.controller;

import com.projeto.model.ContaBancaria;
import com.projeto.structures.*;

import java.util.List;

public class BancoController {
    private TabelaHash<String, ContaBancaria> mapaContas;
    private HeapBinaria filaAtendimento;
    private Pilha<String> historicoOperacoes;
    private Arvore<ContaBancaria> arvoreContas;

    // A ESTRUTURA OBRIGATÓRIA QUE VOCÊ ESQUECEU
    private Fila<String> filaTransacoesPendentes;

    public BancoController() {
        this.mapaContas = new TabelaHash<>(100); // Lembre-se de passar a capacidade se a sua Hash exigir
        this.filaAtendimento = new HeapBinaria(100);
        this.historicoOperacoes = new PilhaEncadeada<>();
        this.arvoreContas = new ArvoreAVL<>();
        this.filaTransacoesPendentes = new FilaComPilhas<>();
    }

    public void cadastrarConta(String cpf, String nome, int prioridade, double saldoInicial) {
        if (mapaContas.buscar(cpf) != null) {
            System.out.println("Erro: Conta (CPF) já cadastrada.");
            return;
        }
        ContaBancaria novaConta = new ContaBancaria(cpf, nome, prioridade, saldoInicial);

        // Insere na Hash para busca O(1)
        mapaContas.inserir(cpf, novaConta);
        // Insere na AVL para listagem O(log n)
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
        ContaBancaria proximo = filaAtendimento.remover(); // VIP sai primeiro
        historicoOperacoes.push("Atendimento físico realizado para: " + proximo.getNumero());
        return proximo;
    }

    // ====================================================================
    // A JUSTIFICATIVA DA FILA COM 2 PILHAS (PROCESSAMENTO EM LOTE / FIFO)
    // ====================================================================
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
        System.out.println("Processando transação (Lote): " + transacao);
        historicoOperacoes.push("Transação processada: " + transacao);
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
        // O CONSERTO: Agora recebemos a lista e iteramos, em vez de ignorar o retorno.
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
}