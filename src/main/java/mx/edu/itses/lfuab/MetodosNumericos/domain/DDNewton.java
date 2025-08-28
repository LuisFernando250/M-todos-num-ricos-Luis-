package mx.edu.itses.lfuab.MetodosNumericos.domain;

import lombok.Data;

@Data
public class DDNewton {
    private int N;             
    private double[] X;         
    private double[] Y;         

    private double[][] Tabla;   
    private double[] Coef;      
    private double[] EvalX;     
    private double[] EvalY;     
    private String[] Pasos;     
}
