package mx.edu.itses.lfuab.MetodosNumericos.domain;

import lombok.Data;

@Data
public class GaussJordan {
    private double[][] A;
    private double[] B;
    private int N;
    private boolean PivoteoParcial;
    private double To;
    private int IteracionesMaximas;

    private double[] X;
    private double De;
    private double ER;

    private double[][] Ab;
    private double[][] R;
    private String[] Pasos;
}
