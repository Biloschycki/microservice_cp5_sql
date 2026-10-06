package com.github.biloschycki.jogador.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Restaurante {
    @Id
    private long id;

    @Column(name = "nome_prato", length = 70, nullable = false)
    private String nome;

    @Column(name = "numero_mesa", nullable = false)
    private int numero;

    
    @Column(name = "quantidade_prato", nullable = false)
    private int quantidade;

    
    @Column(name = "valor_pedido",  nullable = false)
    private double valor;


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public int getNumero() {
        return numero;
    }


    public void setNumero(int numero) {
        this.numero = numero;
    }


    public int getQuantidade() {
        return quantidade;
    }


    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


    public double getValor() {
        return valor;
    }


    public void setValor(double valor) {
        this.valor = valor;
    }
}
