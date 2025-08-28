package mx.edu.itses.lfuab.MetodosNumericos.domain;

import lombok.Data;

@Data
public class Lagrange {
    private double[] X;
    private double[] Y;
    private int N;
    private int Orden;
    private double[] EvalX;
    private double[] EvalY;
    private double[] Coef;
    private String[] Pasos;
    private double ER;

    public void setEr(double ER) {
        this.ER = ER;
    }
}