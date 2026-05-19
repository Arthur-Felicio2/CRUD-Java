package com.template;

import java.sql.Timestamp;

/**
 * Classe DTO (Data Transfer Object) para representar a entidade Personagem.
 */
public class RpgDTO {

    private int id;
    private String nome;
    private String raca;
    private String classe;
    private int nivel;
    private int pontosVida;
    private int pontosMana;
    private int atributoForca;
    private int atributoDestreza;
    private int atributoInteligencia;
    private String alinhamento;
    private Timestamp dataCriacao;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }

    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public int getPontosVida() { return pontosVida; }
    public void setPontosVida(int pontosVida) { this.pontosVida = pontosVida; }

    public int getPontosMana() { return pontosMana; }
    public void setPontosMana(int pontosMana) { this.pontosMana = pontosMana; }

    public int getAtributoForca() { return atributoForca; }
    public void setAtributoForca(int atributoForca) { this.atributoForca = atributoForca; }

    public int getAtributoDestreza() { return atributoDestreza; }
    public void setAtributoDestreza(int atributoDestreza) { this.atributoDestreza = atributoDestreza; }

    public int getAtributoInteligencia() { return atributoInteligencia; }
    public void setAtributoInteligencia(int atributoInteligencia) { this.atributoInteligencia = atributoInteligencia; }

    public String getAlinhamento() { return alinhamento; }
    public void setAlinhamento(String alinhamento) { this.alinhamento = alinhamento; }

    public Timestamp getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Timestamp dataCriacao) { this.dataCriacao = dataCriacao; }
}