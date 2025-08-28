package mx.edu.itses.lfuab.MetodosNumericos.services;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.lfuab.MetodosNumericos.domain.DDNewton;
import mx.edu.itses.lfuab.MetodosNumericos.domain.Lagrange;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnidadIVServiceImpl implements UnidadIVService {

    @Override
    public ArrayList<DDNewton> AlgoritmoDDNewton(DDNewton ddnewtony) {
        ArrayList<DDNewton> respuesta = new ArrayList<>();

        int n = ddnewtony.getN();
        double[] X = ddnewtony.getX();
        double[] Y = ddnewtony.getY();
        double[] evalX = ddnewtony.getEvalX();

        if (X == null || Y == null || n <= 0) return respuesta;
        if (X.length < n || Y.length < n) return respuesta;

        double[][] tabla = new double[n][n];
        for (int i = 0; i < n; i++) tabla[i][0] = Y[i];

        for (int j = 1; j < n; j++) {
            for (int i = 0; i < n - j; i++) {
                double denom = X[i + j] - X[i];
                if (Math.abs(denom) < 1e-14) {
                    return respuesta;
                }
                tabla[i][j] = (tabla[i + 1][j - 1] - tabla[i][j - 1]) / denom;
            }

            DDNewton renglon = new DDNewton();
            renglon.setN(n);
            renglon.setX(X.clone());
            renglon.setY(Y.clone());

            double[][] snap = new double[n][n];
            for (int r = 0; r < n; r++)
                System.arraycopy(tabla[r], 0, snap[r], 0, n);
            renglon.setTabla(snap);

            double[] coefParcial = new double[j + 1];
            for (int k = 0; k <= j; k++) coefParcial[k] = tabla[0][k];
            renglon.setCoef(coefParcial);

            renglon.setPasos(new String[] { "Columna " + j + " calculada" });
            respuesta.add(renglon);
        }

        double[] coef = new double[n];
        for (int k = 0; k < n; k++) coef[k] = tabla[0][k];

        double[] evalY = null;
        if (evalX != null && evalX.length > 0) {
            evalY = new double[evalX.length];
            for (int t = 0; t < evalX.length; t++) {
                double xq = evalX[t];
                double acc = coef[n - 1];
                for (int k = n - 2; k >= 0; k--) {
                    acc = coef[k] + (xq - X[k]) * acc;
                }
                evalY[t] = acc;
            }
        }

        DDNewton finalRow = new DDNewton();
        finalRow.setN(n);
        finalRow.setX(X.clone());
        finalRow.setY(Y.clone());

        double[][] full = new double[n][n];
        for (int r = 0; r < n; r++)
            System.arraycopy(tabla[r], 0, full[r], 0, n);
        finalRow.setTabla(full);

        finalRow.setCoef(coef);
        finalRow.setEvalX(evalX);
        finalRow.setEvalY(evalY);
        finalRow.setPasos(new String[] { "Coeficientes listos y evaluación (si se solicitó)" });
        respuesta.add(finalRow);

        return respuesta;
    }

    @Override
    public ArrayList<Lagrange> AlgoritmoLagrange(Lagrange lagrange) {
        ArrayList<Lagrange> respuesta = new ArrayList<>();

        int n = lagrange.getN();
        double[] X = lagrange.getX();
        double[] Y = lagrange.getY();
        double[] evalX = lagrange.getEvalX();

        if (X == null || Y == null || n <= 0) return respuesta;
        if (X.length < n || Y.length < n) return respuesta;

        double[] denom = new double[n];
        for (int k = 0; k < n; k++) {
            double d = 1.0;
            for (int i = 0; i < n; i++) {
                if (i == k) continue;
                double diff = X[k] - X[i];
                if (Math.abs(diff) < 1e-14) {
                    return respuesta;
                }
                d *= diff;
            }
            denom[k] = d;

            Lagrange renglon = new Lagrange();
            renglon.setN(n);
            renglon.setX(X.clone());
            renglon.setY(Y.clone());
            renglon.setEvalX(null);
            renglon.setEvalY(null);
            renglon.setPasos(new String[] { "Base L" + k + "(x) construida" });
            respuesta.add(renglon);
        }

        double[] evalY = null;
        if (evalX != null && evalX.length > 0) {
            evalY = new double[evalX.length];
            for (int t = 0; t < evalX.length; t++) {
                double xq = evalX[t];
                double px = 0.0;
                for (int k = 0; k < n; k++) {
                    double num = 1.0;
                    for (int i = 0; i < n; i++) {
                        if (i == k) continue;
                        num *= (xq - X[i]);
                    }
                    px += Y[k] * (num / denom[k]);
                }
                evalY[t] = px;
            }
        }

        Lagrange finalRow = new Lagrange();
        finalRow.setN(n);
        finalRow.setX(X.clone());
        finalRow.setY(Y.clone());
        finalRow.setEvalX(evalX);
        finalRow.setEvalY(evalY);
        finalRow.setPasos(new String[] { "Evaluación completada" });
        respuesta.add(finalRow);

        return respuesta;
    }
}
