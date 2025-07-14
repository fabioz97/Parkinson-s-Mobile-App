// Classe User
package com.example.aplicaoparkinson;

public class User {

    String nome;
    int idade, NumeroUtenteSaude, NivelParkinson;

    public User(String nome, int idade, int numeroUtenteSaude, int nivelParkinson) {
        this.nome = nome;
        this.idade = idade;
        this.NumeroUtenteSaude = numeroUtenteSaude;
        this.NivelParkinson = nivelParkinson;
    }
    public User() {
    }
    public User(int numeroUtenteSaude, String nome) {
        NumeroUtenteSaude = numeroUtenteSaude;
        this.nome = nome;
    }

    public int getNivelParkinson() {
        return NivelParkinson;
    }

    public void setNivelParkinson(int nivelParkinson) {
        NivelParkinson = nivelParkinson;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getNumeroUtenteSaude() {
        return NumeroUtenteSaude;
    }

    public void setNumeroUtenteSaude(int numeroUtenteSaude) {
        NumeroUtenteSaude = numeroUtenteSaude;
    }
}
