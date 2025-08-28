/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.lfuab.MetodosNumericos.domain;

import lombok.Data;

/**
 *
 * @author alvar
 */
@Data
public class EliminacionGaussiana {
    private double[][] A;
    private double[] B;
    private int N;
    private boolean PivoteoParcial;
    private double To;
    private int IteracionesMaximas;
    private double[] X;
    private double De;
    private double ER;
    private double[][] U;
    private double[][] L;
    private String[] Pasos;

    public void setEr(double ER) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}   
