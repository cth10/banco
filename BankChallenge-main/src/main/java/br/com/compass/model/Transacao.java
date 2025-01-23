package br.com.compass.model;

import java.time.LocalDateTime;

public class Transacao {
    private String tipo;
    private Double valor;
    private LocalDateTime data;
    private String descricao;
    private String contaOrigem;
    private String contaDestino;

    public Transacao(String tipo, Double valor, String descricao, String contaOrigem, String contaDestino) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = LocalDateTime.now();
        this.descricao = descricao;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
    }

    public String getTipo() { return tipo; }
    public Double getValor() { return valor; }
    public LocalDateTime getData() { return data; }
    public String getDescricao() { return descricao; }
    public String getContaOrigem() { return contaOrigem; }
    public String getContaDestino() { return contaDestino; }
}