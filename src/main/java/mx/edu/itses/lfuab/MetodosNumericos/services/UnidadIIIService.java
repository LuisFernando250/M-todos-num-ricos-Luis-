package mx.edu.itses.lfuab.MetodosNumericos.services;

import mx.edu.itses.lfuab.MetodosNumericos.domain.ReglaCramer;
import mx.edu.itses.lfuab.MetodosNumericos.domain.GaussJordan;
import mx.edu.itses.lfuab.MetodosNumericos.domain.Jacobi;
import mx.edu.itses.lfuab.MetodosNumericos.domain.GaussSeidel;

public interface UnidadIIIService {
    ReglaCramer  AlgoritmoReglaCramer (ReglaCramer modelCramer);
    GaussJordan  AlgoritmoGaussJordan(GaussJordan gaussJordan);
    Jacobi       AlgoritmoJacobi     (Jacobi jacobi);
    GaussSeidel  AlgoritmoGaussSeidel(GaussSeidel gaussSeidel);
}
