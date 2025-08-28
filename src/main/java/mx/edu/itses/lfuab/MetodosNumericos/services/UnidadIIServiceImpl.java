package mx.edu.itses.lfuab.MetodosNumericos.services;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.lfuab.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.lfuab.MetodosNumericos.domain.EliminacionGaussiana;
import mx.edu.itses.lfuab.MetodosNumericos.domain.NewtonRaphson;
import mx.edu.itses.lfuab.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.lfuab.MetodosNumericos.domain.ReglaFalsa;
import mx.edu.itses.lfuab.MetodosNumericos.domain.Secante;
import mx.edu.itses.lfuab.MetodosNumericos.domain.SecanteModificado;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnidadIIServiceImpl implements UnidadIIService {

    @Override
    public ArrayList<Biseccion> AlgoritmoBiseccion(Biseccion biseccion) {
        ArrayList<Biseccion> respuesta = new ArrayList<>();
        double XL, XU, XRa, XRn, FXL, FXU, FXR, Ea;

        XL = biseccion.getXL();
        XU = biseccion.getXU();
        XRa = 0;
        Ea = 100;
        FXL = Funciones.Ecuacion(biseccion.getFX(), XL);
        FXU = Funciones.Ecuacion(biseccion.getFX(), XU);
        if (FXL * FXU < 0) {
            for (int i = 1; i <= biseccion.getIteracionesMaximas(); i++) {
                XRn = (XL + XU) / 2;
                FXL = Funciones.Ecuacion(biseccion.getFX(), XL);
                FXU = Funciones.Ecuacion(biseccion.getFX(), XU);
                FXR = Funciones.Ecuacion(biseccion.getFX(), XRn);
                if (i != 1) {
                    Ea = Funciones.ErrorRelativo(XRn, XRa);
                }
                Biseccion renglon = new Biseccion();
                renglon.setXL(XL);
                renglon.setXU(XU);
                renglon.setXR(XRn);
                renglon.setFXL(FXL);
                renglon.setFXU(FXU);
                renglon.setFXR(FXR);
                renglon.setEa(Ea);
                if (FXL * FXR < 0) {
                    XU = XRn;
                } else if (FXL * FXR > 0) {
                    XL = XRn;
                } else if (FXL * FXR == 0) {
                    break;
                }
                XRa = XRn;
                respuesta.add(renglon);
                if (Ea <= biseccion.getEa()) {
                    break;
                }
            }
        } else {
            Biseccion renglon = new Biseccion();
            respuesta.add(renglon);
        }

        return respuesta;
    }

    @Override
    public ArrayList<ReglaFalsa> AlgoritmoReglaFalsa(ReglaFalsa regulafalsi) {
        ArrayList<ReglaFalsa> respuesta = new ArrayList<>();
        double XL, XU, XRa, XRn, FXL, FXU, FXR, Ea;

        XL = regulafalsi.getXL();
        XU = regulafalsi.getXU();
        XRa = 0;
        Ea = 100;
        FXL = Funciones.Ecuacion(regulafalsi.getFX(), XL);
        FXU = Funciones.Ecuacion(regulafalsi.getFX(), XU);
        System.out.println(Funciones.Derivada(regulafalsi.getFX(), 2));

        if (FXL * FXU < 0) {
            for (int i = 1; i <= regulafalsi.getIteracionesMaximas(); i++) {
                FXL = Funciones.Ecuacion(regulafalsi.getFX(), XL);
                FXU = Funciones.Ecuacion(regulafalsi.getFX(), XU);
                XRn = XU - ((FXU * (XL - XU)) / (FXL - FXU));
                FXR = Funciones.Ecuacion(regulafalsi.getFX(), XRn);
                if (i != 1) {
                    Ea = Funciones.ErrorRelativo(XRn, XRa);
                }
                ReglaFalsa renglon = new ReglaFalsa();
                renglon.setXL(XL);
                renglon.setXU(XU);
                renglon.setXR(XRn);
                renglon.setFXL(FXL);
                renglon.setFXU(FXU);
                renglon.setFXR(FXR);
                renglon.setEa(Ea);

                if (FXL * FXR < 0) {
                    XU = XRn;
                } else if (FXL * FXR > 0) {
                    XL = XRn;
                } else if (FXL * FXR == 0) {
                    break;
                }
                XRa = XRn;
                System.out.println(XRa);
                respuesta.add(renglon);
                if (Ea <= regulafalsi.getEa()) {
                    break;
                }
            }
        } else {
            ReglaFalsa renglon = new ReglaFalsa();
            respuesta.add(regulafalsi);
        }

        return respuesta;
    }

    @Override
    public ArrayList<PuntoFijo> AlgoritmoPuntoFijo(PuntoFijo fixedpoint) {
        ArrayList<PuntoFijo> respuesta = new ArrayList<>();
        double XI, GXI, Ea;
        XI = fixedpoint.getXI();
        Ea = 100;
        for (int i = 1; i < fixedpoint.getIteracionesMaximas(); i++) {
            GXI = Funciones.Ecuacion(fixedpoint.getFX(), XI);
            if (i != 1) {
                Ea = Funciones.ErrorRelativo(GXI, XI);
            }
            PuntoFijo renglon = new PuntoFijo();
            renglon.setXI(XI);
            renglon.setGXI(GXI);
            renglon.setEa(Ea);
            XI = GXI;
            respuesta.add(renglon);
            if (Ea <= fixedpoint.getEa()) {
                break;
            }
        }
        return respuesta;
    }

    @Override
    public ArrayList<NewtonRaphson> AlgoritmoNewtonRaphon(NewtonRaphson newtonraphson) {
        ArrayList<NewtonRaphson> respuesta = new ArrayList<>();
        double XI, FXI, FDXI, XII, Ea;

        XI = newtonraphson.getXI();
        FXI = Funciones.Ecuacion(newtonraphson.getFX(), XI);
        FDXI = Funciones.Derivada(newtonraphson.getFX(), XI);

        Ea = 100;
        for (int i = 1; i < newtonraphson.getIteracionesMaximas(); i++) {
            FXI = Funciones.Ecuacion(newtonraphson.getFX(), XI);
            FDXI = Funciones.Derivada(newtonraphson.getFX(), XI);
            XII = XI - (FXI / FDXI);
            if (i != 1) {
                Ea = Funciones.ErrorRelativo(XII, XI);
            }
            NewtonRaphson renglon = new NewtonRaphson();
            renglon.setXI(XI);
            renglon.setFXI(FXI);
            renglon.setFDXI(FDXI);
            renglon.setXII(XII);
            renglon.setEa(Ea);
            XI = XII;

            respuesta.add(renglon);
            if (Ea <= newtonraphson.getEa()) {
                break;
            }

        }
        return respuesta;

    }

    @Override
    public ArrayList<Secante> AlgoritmoSecante(Secante secant) {
        ArrayList<Secante> respuesta = new ArrayList<>();
        double XMI, XI, XII, FXMI, FXI, Ea;
        XMI = secant.getXMI();
        XI = secant.getXI();
        FXI = Funciones.Ecuacion(secant.getFX(), XI);
        FXMI = Funciones.Ecuacion(secant.getFX(), XMI);
        Ea = 100;
        for (int i = 1; i < secant.getIteracionesMaximas(); i++) {
            FXI = Funciones.Ecuacion(secant.getFX(), XI);
            FXMI = Funciones.Ecuacion(secant.getFX(), XMI);
            XII = XI - ((FXI * (XMI - XI)) / (FXMI - FXI));
            Ea = Funciones.ErrorRelativo(XII, XI);

            Secante renglon = new Secante();
            renglon.setXI(XI);
            renglon.setXMI(XMI);
            renglon.setXII(XII);
            renglon.setFXI(FXI);
            renglon.setFXMI(FXMI);
            renglon.setEa(Ea);
            XMI = XI;
            XI = XII;
            respuesta.add(renglon);
            if (Ea <= secant.getEa()) {
                break;
            }
        }
        return respuesta;

    }

    @Override
    public ArrayList<SecanteModificado> AlgoritmoSecanteModificado(SecanteModificado modsecant) {
        ArrayList<SecanteModificado> respuesta = new ArrayList<>();
        double Sigma, XI, XII, FXIS, FXI, Ea;
        Sigma = modsecant.getSigma();
        XI = modsecant.getXI();
        FXI = Funciones.Ecuacion(modsecant.getFX(), XI);
        FXIS = Funciones.Ecuacion(modsecant.getFX(), (XI + XI * Sigma));
        XII = XI - ((Sigma * XI * FXI) / (FXIS - FXI));
        System.out.println(XI + (XI * Sigma));
        Ea = 100;
        for (int i = 1; i < modsecant.getIteracionesMaximas(); i++) {
            FXI = Funciones.Ecuacion(modsecant.getFX(), XI);
            FXIS = Funciones.Ecuacion(modsecant.getFX(), (XI + XI * Sigma));
            XII = XI - ((Sigma * XI * FXI) / (FXIS - FXI));
            Ea = Funciones.ErrorRelativo(XII, XI);

            SecanteModificado renglon = new SecanteModificado();
            renglon.setXI(XI);
            renglon.setXII(XII);
            renglon.setSigma(Sigma);
            renglon.setFXI(FXI);
            renglon.setFXIS(FXIS);
            renglon.setEa(Ea);
            XI = XII;
            respuesta.add(renglon);
            if (Ea <= modsecant.getEa()) {
                break;
            }
        }
        return respuesta;
    }

    @Override
    public ArrayList<EliminacionGaussiana> AlgoritmoEliminacionGaussiana(EliminacionGaussiana eliminaciongaussiana) {
        ArrayList<EliminacionGaussiana> respuesta = new ArrayList<>();
        double A, B, To, De, ER, U, L; // (solo conservados para tu estilo; no se usan como escalares)

        int N = eliminaciongaussiana.getN();

        // Copias locales profundas de A y B (sin helpers externos)
        double[][] AA = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                AA[i][j] = eliminaciongaussiana.getA()[i][j];
            }
        }
        double[] BB = new double[N];
        for (int i = 0; i < N; i++) {
            BB[i] = eliminaciongaussiana.getB()[i];
        }

        To = (eliminaciongaussiana.getTo() > 0) ? eliminaciongaussiana.getTo() : 1e-12;
        De = 1.0;

        // Eliminación hacia adelante (sin pivoteo para mantenerlo compacto)
        for (int K = 0; K < N - 1; K++) {
            double PIV = AA[K][K];
            if (Math.abs(PIV) < To) { De = 0.0; break; }

            for (int I = K + 1; I < N; I++) {
                double M = AA[I][K] / PIV;
                AA[I][K] = 0.0;
                for (int J = K + 1; J < N; J++) {
                    AA[I][J] -= M * AA[K][J];
                }
                BB[I] -= M * BB[K];
            }

            De *= PIV;

            // renglón (snapshot del paso K)
            EliminacionGaussiana renglon = new EliminacionGaussiana();
            double[][] Astep = new double[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) Astep[i][j] = AA[i][j];
            }
            double[] Bstep = new double[N];
            for (int i = 0; i < N; i++) Bstep[i] = BB[i];

            renglon.setA(Astep);
            renglon.setB(Bstep);
            renglon.setN(N);
            renglon.setTo(To);
            renglon.setIteracionesMaximas(eliminaciongaussiana.getIteracionesMaximas());
            renglon.setPivoteoParcial(eliminaciongaussiana.isPivoteoParcial());
            renglon.setDe(De);
            // NO usamos setEr(...) porque en tu POJO arroja UnsupportedOperationException
            renglon.setX(null);
            renglon.setU(null);
            renglon.setL(null);
            renglon.setPasos(new String[] { "Paso " + K });
            respuesta.add(renglon);
        }

        // Sustitución regresiva si el determinante no se anuló
        double[] XX = null;
        if (De != 0.0) {
            double ULT = AA[N - 1][N - 1];
            if (Math.abs(ULT) < To) {
                De = 0.0;
            } else {
                De *= ULT;
                XX = new double[N];
                for (int I = N - 1; I >= 0; I--) {
                    double S = 0.0;
                    for (int J = I + 1; J < N; J++) S += AA[I][J] * XX[J];
                    XX[I] = (BB[I] - S) / AA[I][I];
                }
            }
        }

        // renglón final
        EliminacionGaussiana resultado = new EliminacionGaussiana();
        double[][] Afinal = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) Afinal[i][j] = AA[i][j];
        }
        double[] Bfinal = new double[N];
        for (int i = 0; i < N; i++) Bfinal[i] = BB[i];

        resultado.setA(Afinal);
        resultado.setB(Bfinal);
        resultado.setN(N);
        resultado.setTo(To);
        resultado.setIteracionesMaximas(eliminaciongaussiana.getIteracionesMaximas());
        resultado.setPivoteoParcial(eliminaciongaussiana.isPivoteoParcial());
        resultado.setX(XX);
        resultado.setDe(De);
        // NO llamamos setEr(...)
        resultado.setU(null);
        resultado.setL(null);
        resultado.setPasos(new String[] { "Fin" });
        respuesta.add(resultado);

        return respuesta;
    }
}
