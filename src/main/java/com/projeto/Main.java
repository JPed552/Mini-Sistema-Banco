package com.projeto;

import com.projeto.controller.BancoController;
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