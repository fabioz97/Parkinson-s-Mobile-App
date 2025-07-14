package com.example.aplicaoparkinson;

class Resposta {
    private String textoPergunta;
    private String textoResposta;
    private int valorSentimento;
    private long timestamp;

    public Resposta(String textoPergunta, String textoResposta, int valorSentimento, long timestamp) {
        this.textoPergunta = textoPergunta;
        this.textoResposta = textoResposta;
        this.valorSentimento = valorSentimento;
        this.timestamp = timestamp;
    }

    public String getTextoPergunta() {
        return textoPergunta;
    }

    public String getTextoResposta() {
        return textoResposta;
    }

    public int getValorSentimento() {
        return valorSentimento;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Resposta{" +
                "textoPergunta='" + textoPergunta + '\'' +
                ", textoResposta='" + textoResposta + '\'' +
                ", valorSentimento=" + valorSentimento +
                ", timestamp=" + timestamp +
                '}';
    }
}
