package mx.edu.itses.lfuab.MetodosNumericos.domain;

import lombok.Data;

@Data
public class GaussSeidel {
    private double[][] A;
    private double[] B;
    private int N;
    private double To;
    private int IteracionesMaximas;
    private double[] X0;
    private double[] X;
    private double ER;
    private int IteracionesRealizadas;
    private String[] Pasos;

    public void setEr(double er) { this.ER = er; }
}
