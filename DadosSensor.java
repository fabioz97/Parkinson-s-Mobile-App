package com.example.aplicaoparkinson;

class DadosSensor {
    public String tipo;
    public float x, y, z;

    public DadosSensor(String tipo, float x, float y, float z) {
        this.tipo = tipo;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}