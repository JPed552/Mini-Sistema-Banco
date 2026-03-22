package com.projeto.structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class FilaComPilhasTest {

    private FilaComPilhas<Integer> fila;

    @BeforeEach
    void setUp() {
        fila = new FilaComPilhas<>();
    }

    @Test
    void enqueueEdequeue_CaminhoFeliz_DeveManterFIFO() {
        fila.enqueue(10);
        fila.enqueue(20);
        fila.enqueue(30);

        assertEquals(3, fila.size(), "O tamanho deveria ser 3.");

        assertEquals(10, fila.dequeue(), "O 10 entrou primeiro, deve sair primeiro.");
        assertEquals(20, fila.dequeue(), "O 20 entrou em segundo, deve sair em segundo.");
        assertEquals(30, fila.dequeue());
        assertTrue(fila.isEmpty(), "A fila deve estar vazia no final.");
    }

    @Test
    void operacoesIntercaladas_TesteDeStress_NaoDevePerderAOrdem() {
        fila.enqueue(1);
        fila.enqueue(2);

        assertEquals(1, fila.dequeue(), "Deve remover o 1.");

        fila.enqueue(3); // Inserindo enquanto a pilha de saída tem dados

        assertEquals(2, fila.dequeue(), "Deve remover o 2.");
        assertEquals(3, fila.dequeue(), "Deve remover o 3.");
    }

    @Test
    void isEmptyESize_CasosExtremos_DeveLidarComFilaVazia() {
        assertTrue(fila.isEmpty(), "A fila deve nascer vazia.");
        assertEquals(0, fila.size(), "O tamanho inicial deve ser 0.");

        assertThrows(NoSuchElementException.class, () -> {
            fila.dequeue();
        }, "Remover de fila vazia deve lançar NoSuchElementException.");

        assertThrows(NoSuchElementException.class, () -> {
            fila.peek();
        }, "Peek em fila vazia deve lançar NoSuchElementException.");
    }
}