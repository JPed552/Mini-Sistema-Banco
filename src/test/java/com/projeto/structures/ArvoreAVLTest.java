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
    void emOrdem_DeveRetornarListaOrdenadaPorNome() {
        arvore.inserir(new ContaBancaria("200", "Carlos", 2, 0));
        arvore.inserir(new ContaBancaria("100", "Ana", 2, 0));
        arvore.inserir(new ContaBancaria("300", "Bruno", 2, 0));

        List<ContaBancaria> lista = arvore.emOrdem();

        assertEquals(3, lista.size());
        assertEquals("Ana", lista.get(0).getTitular(), "O primeiro deve ser Ana (Ordem Alfabética).");
        assertEquals("Bruno", lista.get(1).getTitular());
        assertEquals("Carlos", lista.get(2).getTitular());
    }

    @Test
    void nomesIguais_DeveUsarCPFComoDesempateENaoExcluirDados() {
        ContaBancaria c1 = new ContaBancaria("111", "João Silva", 2, 100.0);
        ContaBancaria c2 = new ContaBancaria("222", "João Silva", 2, 200.0);

        arvore.inserir(c1);
        arvore.inserir(c2);

        List<ContaBancaria> lista = arvore.emOrdem();

        assertEquals(2, lista.size(), "A árvore deve conter ambos os Joões, pois os CPFs são diferentes.");
        assertEquals("111", lista.get(0).getNumero(), "O João com CPF 111 deve vir primeiro no desempate.");
        assertEquals("222", lista.get(1).getNumero());
    }

    @Test
    void buscar_DeveExigirNomeECPFParaSucesso() {
        ContaBancaria alvo = new ContaBancaria("123", "Cliente X", 2, 0);
        arvore.inserir(alvo);

        assertTrue(arvore.buscar(new ContaBancaria("123", "Cliente X", 0, 0)));

        assertFalse(arvore.buscar(new ContaBancaria("123", "Nome Errado", 0, 0)),
                "A busca deve falhar se o nome não bater, mesmo com o CPF correto.");
    }

    @Test
    void remover_DeveManterIntegridade() {
        ContaBancaria c1 = new ContaBancaria("123", "Remover Me", 1, 0.0);
        arvore.inserir(c1);
        arvore.remover(c1);
        assertFalse(arvore.buscar(c1), "A conta deve ser removida com sucesso.");
        assertTrue(arvore.estaVazia());
    }
}