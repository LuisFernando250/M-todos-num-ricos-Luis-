/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.lfuab.MetodosNumericos.domain;

import lombok.Data;

/**
 *
 * lfuab
 */
@Data
public class SecanteModificado {
    private String FX; // Función a evaluar
    private double Sigma, XI, XII, FXIS, FXI, Ea; // XII = Xi+1
    private int IteracionesMaximas;
}
