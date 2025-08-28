package mx.edu.itses.lfuab.MetodosNumericos.services;

import java.util.ArrayList;
import mx.edu.itses.lfuab.MetodosNumericos.domain.DDNewton;
import mx.edu.itses.lfuab.MetodosNumericos.domain.Lagrange;

public interface UnidadIVService {
    public ArrayList<DDNewton>  AlgoritmoDDNewton(DDNewton ddnewton);
    public ArrayList<Lagrange>  AlgoritmoLagrange(Lagrange lagrange);
}
