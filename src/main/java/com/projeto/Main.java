package com.projeto;

import com.projeto.controller.BancoController;
import com.projeto.model.ContaBancaria;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BancoController banco = new BancoController();
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        System.out.println("=== SISTEMA BANCÁRIO ===");

        while (opcao != 0) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Cadastrar Nova Conta (Tabela Hash)");
            System.out.println("2. Buscar Conta por CPF (Tabela Hash)");
            System.out.println("3. Listar Todas as Contas (Árvore AVL)");
            System.out.println("4. Entrar na Fila de Atendimento (Heap)");
            System.out.println("5. Atender Próximo Cliente (Heap)");
            System.out.println("6. Excluir conta");
            System.out.println("7. Processar Próxima Transação (Fila/Pilhas)");
            System.out.println("8. Exibir Última Operação (Pilha)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Digite o CPF (Apenas números): ");
                    String cpf = scanner.nextLine();

                    System.out.print("Nome do Titular: ");
                    String nome = scanner.nextLine();

                    System.out.println("Prioridade (1: VIP/Prioritário | 2: Comum): ");
                    int prioridade = Integer.parseInt(scanner.nextLine());

                    System.out.print("Saldo Inicial: R$ ");
                    double saldo = Double.parseDouble(scanner.nextLine());

                    banco.cadastrarConta(cpf, nome, prioridade, saldo);
                    break;

                case 2:
                    System.out.print("Digite o CPF para busca: ");
                    String buscaCpf = scanner.nextLine();
                    ContaBancaria c = banco.buscarConta(buscaCpf);
                    if (c != null) {
                        System.out.println("Conta encontrada! Titular: " + c.getTitular() + " | Saldo: " + c.getSaldo());
                    } else {
                        System.out.println("Conta não localizada.");
                    }
                    break;

                case 3:
                    banco.listarContasOrdenadas();
                    break;

                case 4:
                    System.out.print("CPF do cliente que vai entrar na fila: ");
                    banco.entrarNaFila(scanner.nextLine());
                    break;

                case 5:
                    ContaBancaria proximo = banco.atenderProximo();

                    if (proximo != null) {
                        System.out.println("\n>>> Chamando: " + proximo.getTitular() + " (CPF: " + proximo.getNumero() + ")");
                        System.out.println("Selecione a operação desejada:");
                        System.out.println("1. Depósito");
                        System.out.println("2. Saque");
                        System.out.print("Opção: ");
                        int tipoAtend = Integer.parseInt(scanner.nextLine());

                        System.out.print("Valor da operação: R$ ");
                        double valorAtend = Double.parseDouble(scanner.nextLine());

                        if (tipoAtend == 2) valorAtend *= -1;

                        banco.registrarTransacaoDeAtendimento(proximo, valorAtend);
                    }
                    break;

                case 6:
                    System.out.print("Digite o CPF da conta a ser excluída: ");
                    banco.excluirConta(scanner.nextLine());
                    break;

                case 7:
                    System.out.println("Retirando transação da Fila e aplicando o saldo...");
                    banco.processarProximaTransacao();
                    break;

                case 8:
                    banco.exibirUltimoLog();
                    break;

                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }
}