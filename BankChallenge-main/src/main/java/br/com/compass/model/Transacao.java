package br.com.compass.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity // Marca como entidade JPA
public class Transacao {
    private String tipo;
    private Double valor;
    private LocalDateTime data;
    private String descricao;
    private String contaOrigem; // CPF da conta de origem
    private String contaDestino; // CPF da conta de destino

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    public Transacao(String tipo, Double valor, String descricao, String contaOrigem, String contaDestino) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = LocalDateTime.now(); // Registra o momento atual
        this.descricao = descricao;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
    }

    public Transacao() {

    }

    public String getTipo() { return tipo; }
    public Double getValor() { return valor; }
    public LocalDateTime getData() { return data; }
    public String getDescricao() { return descricao; }
    public String getContaOrigem() { return contaOrigem; }
    public String getContaDestino() { return contaDestino; }
}