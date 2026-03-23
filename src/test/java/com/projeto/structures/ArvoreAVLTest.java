package com.projeto.structures;

import com.projeto.model.ContaBancaria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ArvoreAVLTest {

    private ArvoreAVL<ContaBancaria> arvore;

    @BeforeEach
    void setUp() {
        arvore = new ArvoreAVL<>();
    }

    @Test
    void inserir_DeveManterOrdemEBalanceamento() {
        arvore.inserir(new ContaBancaria("333", "Titular C", 2, 100.0));
        arvore.inserir(new ContaBancaria("222", "Titular B", 2, 100.0));
        arvore.inserir(new ContaBancaria("111", "Titular A", 2, 100.0));

        assertEquals(2, arvore.getAltura(), "A árvore deve estar balanceada (altura 2).");
        assertTrue(arvore.buscar(new ContaBancaria("222", "", 0, 0)));
    }

    @Test
    void emOrdem_DeveRetornarListaOrdenadaPorCPF() {
        arvore.inserir(new ContaBancaria("200", "B", 2, 0));
        arvore.inserir(new ContaBancaria("100", "A", 2, 0));
        arvore.inserir(new ContaBancaria("300", "C", 2, 0));

        List<ContaBancaria> lista = arvore.emOrdem();

        assertEquals(3, lista.size());
        assertEquals("100", lista.get(0).getNumero(), "O primeiro elemento deve ser o menor CPF.");
        assertEquals("200", lista.get(1).getNumero());
        assertEquals("300", lista.get(2).getNumero(), "O último elemento deve ser o maior CPF.");
    }

    @Test
    void remover_DeveExcluirElementoEAtivarRebalanceamento() {
        ContaBancaria conta = new ContaBancaria("500", "D", 1, 0.0);
        arvore.inserir(conta);
        assertTrue(arvore.buscar(conta), "A conta deve ser encontrada após a inserção.");

        arvore.remover(conta);
        assertFalse(arvore.buscar(conta), "A conta não deve mais existir após a remoção.");
        assertTrue(arvore.estaVazia(), "A árvore deve ficar vazia se o único elemento foi removido.");
    }

    @Test
    void buscar_DeveRetornarFalsoParaElementoInexistente() {
        assertFalse(arvore.buscar(new ContaBancaria("999", "Inexistente", 2, 0)));
    }
}