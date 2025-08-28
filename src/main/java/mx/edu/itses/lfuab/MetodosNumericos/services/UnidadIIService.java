package mx.edu.itses.lfuab.MetodosNumericos.services;

import java.util.ArrayList;
import mx.edu.itses.lfuab.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.lfuab.MetodosNumericos.domain.NewtonRaphson;
import mx.edu.itses.lfuab.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.lfuab.MetodosNumericos.domain.ReglaFalsa;
import mx.edu.itses.lfuab.MetodosNumericos.domain.Secante;
import mx.edu.itses.lfuab.MetodosNumericos.domain.SecanteModificado;
import mx.edu.itses.lfuab.MetodosNumericos.domain.EliminacionGaussiana;

public interface UnidadIIService {
   
      public ArrayList<Biseccion> AlgoritmoBiseccion(Biseccion biseccion);
      public ArrayList<ReglaFalsa> AlgoritmoReglaFalsa(ReglaFalsa regulafalsi);
      public ArrayList<PuntoFijo> AlgoritmoPuntoFijo (PuntoFijo fixedpoint);
      public ArrayList<NewtonRaphson> AlgoritmoNewtonRaphon (NewtonRaphson newtonraphson);
      public ArrayList<Secante> AlgoritmoSecante (Secante secant);
      public ArrayList<SecanteModificado> AlgoritmoSecanteModificado  (SecanteModificado modsecant);
      public ArrayList<EliminacionGaussiana> AlgoritmoEliminacionGaussiana(EliminacionGaussiana eliminaciongaussiana);
      
}
