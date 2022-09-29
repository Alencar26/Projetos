package br.com.al3ncar.calc.model;

@FunctionalInterface
public interface MemoriaObserver {

    void valorAlterado(String novoValor);
}
