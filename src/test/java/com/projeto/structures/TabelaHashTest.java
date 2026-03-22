package com.projeto.structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TabelaHashTest {

    private TabelaHash<Integer, String> hash;

    @BeforeEach
    void setUp() {
        hash = new TabelaHash<>(5);
    }

    @Test
    void inserirEBuscar_CaminhoFeliz_DeveEncontrarElementos() {
        hash.inserir(100, "Cliente A");
        hash.inserir(200, "Cliente B");

        assertEquals("Cliente A", hash.buscar(100), "Deve encontrar o valor exato inserido.");
        assertEquals("Cliente B", hash.buscar(200));
        assertEquals(2, hash.tamanho(), "O tamanho deve ser 2 após duas inserções distintas.");
        assertFalse(hash.estaVazia());
    }

    @Test
    void sobrescreverChave_TesteDeEstresse_DeveAtualizarValorSemAumentarTamanho() {
        hash.inserir(10, "Saldo Antigo");
        hash.inserir(10, "Saldo Novo"); // Inserindo na mesma chave

        assertEquals("Saldo Novo", hash.buscar(10), "A Hash deve atualizar o valor em chaves duplicadas.");
        assertEquals(1, hash.tamanho(), "Falha Crítica: O tamanho aumentou ao sobrescrever. Sua Hash está duplicando nós em vez de atualizar.");
    }

    @Test
    void operacoesComInexistentes_CasosExtremos_DeveRetornarNuloLimpo() {
        assertNull(hash.buscar(999), "Buscar chave inexistente deve retornar null sem estourar exceção.");

        hash.inserir(1, "Alvo");
        hash.remover(1);

        assertNull(hash.buscar(1), "Após remoção, a chave deve ser inacessível.");
        assertTrue(hash.estaVazia(), "A tabela deve estar vazia após remover o único elemento.");
    }
}