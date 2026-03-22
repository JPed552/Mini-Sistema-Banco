package com.projeto.structures;

import com.projeto.model.ContaBancaria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HeapBinariaTest {

    private HeapBinaria heap;

    @BeforeEach
    void setUp() {
        // Inicializa com capacidade 3 para forçarmos o teste de Fila Cheia rapidamente
        heap = new HeapBinaria(3);
    }

    @Test
    void inserirERemover_CaminhoFeliz_DeveGarantirPrioridadeVip() {
        // Lógica do seu sistema: Prioridade 1 (VIP) é maior que Prioridade 2 (Comum)
        ContaBancaria comum1 = new ContaBancaria("111", "Comum A", 2, 100.0);
        ContaBancaria vip = new ContaBancaria("222", "VIP", 1, 500.0);
        ContaBancaria comum2 = new ContaBancaria("333", "Comum B", 2, 200.0);

        heap.inserir(comum1);
        heap.inserir(vip);
        heap.inserir(comum2);

        // O VIP foi o segundo a entrar, mas DEVE ser o primeiro a sair
        assertEquals("222", heap.remover().getNumero(), "Falha Crítica: O VIP não assumiu o topo da Heap.");
        assertEquals(2, heap.remover().getPrioridade(), "O próximo deve ser prioridade 2.");
        assertEquals(2, heap.remover().getPrioridade(), "O último deve ser prioridade 2.");
        assertTrue(heap.estaVazia());
    }

    @Test
    void filaCheia_TesteDeEstresse_DeveLancarIllegalStateException() {
        heap.inserir(new ContaBancaria("1", "A", 1, 0));
        heap.inserir(new ContaBancaria("2", "B", 1, 0));
        heap.inserir(new ContaBancaria("3", "C", 1, 0));

        assertThrows(IllegalStateException.class, () -> {
            heap.inserir(new ContaBancaria("4", "D", 1, 0));
        }, "A heap deve lançar IllegalStateException quando tentar ultrapassar a capacidade.");
    }

    @Test
    void operacoesEmFilaVazia_CasosExtremos_DeveRetornarNuloLimpo() {
        assertTrue(heap.estaVazia());
        assertNull(heap.remover(), "Remover de heap vazia deve retornar null, conforme seu código.");
        assertNull(heap.topo(), "Consultar o topo de heap vazia deve retornar null.");
    }
}