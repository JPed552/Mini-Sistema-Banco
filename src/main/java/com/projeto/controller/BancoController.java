package com.projeto.controller;

import com.projeto.model.ContaBancaria;
import com.projeto.structures.*;

public class BancoController {
    private TabelaHash<String, ContaBancaria> mapaContas;
    private HeapBinaria filaAtendimento;
    private Pilha<String> historicoOperacoes;
    private Arvore<ContaBancaria> arvoreContas;

    public BancoController() {
        this.mapaContas = new TabelaHash<>();
        this.filaAtendimento = new HeapBinaria(100); // Capacidade inicial
        this.historicoOperacoes = new PilhaEncadeada<>();
        this.arvoreContas = new ArvoreAVL<>();
    }

    // 1. Cadastro de Conta
    public void cadastrarConta(String cpf, String nome, int prioridade, double saldoInicial) {
        if (mapaContas.buscar(cpf) != null) {
            System.out.println("Erro: CPF já cadastrado.");
            return;
        }
        ContaBancaria novaConta = new ContaBancaria(cpf, nome, prioridade, saldoInicial);
        mapaContas.inserir(cpf, novaConta);
        arvoreContas.inserir(novaConta);
        historicoOperacoes.push("Conta criada: " + cpf);
        System.out.println("Conta cadastrada com sucesso!");
    }

    // 2. Entrada na Fila de Atendimento (Triagem)
    public void entrarNaFila(String cpf) {
        ContaBancaria conta = mapaContas.buscar(cpf);
        if (conta == null) {
            System.out.println("Erro: Cliente não possui conta neste banco.");
            return;
        }
        filaAtendimento.inserir(conta);
        System.out.println("Cliente " + conta.getNumero() + " entrou na fila de espera.");
    }

    // 3. Atendimento do Próximo Cliente (O coração da Heap)
    public ContaBancaria atenderProximo() {
        if (filaAtendimento.estaVazia()) {
            System.out.println("Fila vazia.");
            return null;
        }
        ContaBancaria proximo = filaAtendimento.remover();
        historicoOperacoes.push("Atendimento realizado: " + proximo.getNumero());
        return proximo;
    }

    // 4. Busca de Dados
    public ContaBancaria buscarConta(String cpf) {
        return mapaContas.buscar(cpf);
    }

    public String desfazerUltimaAcao() {
        if (historicoOperacoes.isEmpty()) return "Nenhuma ação para desfazer.";
        return "Ação desfeita: " + historicoOperacoes.pop();
    }

    public void listarContasOrdenadas() {
        if (arvoreContas.estaVazia()) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }

        System.out.println("Contas cadastradas em ordem:");
        ((ArvoreAVL<ContaBancaria>) arvoreContas).emOrdem();
    }
}