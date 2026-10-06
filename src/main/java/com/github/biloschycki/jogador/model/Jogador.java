package com.github.biloschycki.jogador.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jogadores")
public class Jogador {
    @Id
    private long id;

    @Column(name = "nome_jogador", length = 70, nullable = false)
    private String nome;

    @Column(name = "time_jogador", length = 70, nullable = false)
    private String time;

    @Column(name = "numero_jogador", nullable = false)
    private int numero;

    @Column(name = "posicao_jogador", nullable = false)
    private String posicao;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }




}
