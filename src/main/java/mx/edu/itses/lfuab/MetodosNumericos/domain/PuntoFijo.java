/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.lfuab.MetodosNumericos.domain;

import lombok.Data;

/**
 *
 * @author lfuab
 */
@Data
public class PuntoFijo {
    private String FX; // Función a evaluar
    private double XI, GXI, Ea;
    private int IteracionesMaximas;
}
