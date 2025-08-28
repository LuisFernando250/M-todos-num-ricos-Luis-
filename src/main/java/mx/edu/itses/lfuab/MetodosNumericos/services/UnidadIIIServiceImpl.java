package mx.edu.itses.lfuab.MetodosNumericos.services;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.lfuab.MetodosNumericos.domain.GaussJordan;
import mx.edu.itses.lfuab.MetodosNumericos.domain.ReglaCramer;
import mx.edu.itses.lfuab.MetodosNumericos.domain.Jacobi;
import mx.edu.itses.lfuab.MetodosNumericos.domain.GaussSeidel;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnidadIIIServiceImpl implements UnidadIIIService {

    @Override
    public ReglaCramer AlgoritmoReglaCramer(ReglaCramer modelCramer) {
        ArrayList<Double> determinantes = new ArrayList<>();
        ArrayList<Double> vectorX = new ArrayList<>();

        switch (modelCramer.getMN()) {
            case 2 -> {
                ArrayList<Double> A = modelCramer.getMatrizA();
                ArrayList<Double> b = modelCramer.getVectorB();

                double[][] MatrizA = {
                    {A.get(0), A.get(1)},
                    {A.get(2), A.get(3)}
                };

                determinantes.add(Det2(MatrizA));

                double[][] MatrizX1 = {
                    {b.get(0), A.get(1)},
                    {b.get(1), A.get(3)}
                };
                determinantes.add(Det2(MatrizX1));

                double[][] MatrizX2 = {
                    {A.get(0), b.get(0)},
                    {A.get(2), b.get(1)}
                };
                determinantes.add(Det2(MatrizX2));

                vectorX.add(determinantes.get(1) / determinantes.get(0));
                vectorX.add(determinantes.get(2) / determinantes.get(0));
            }
            case 3 -> {
                ArrayList<Double> A = modelCramer.getMatrizA();
                ArrayList<Double> b = modelCramer.getVectorB();

                double[][] MatrizA = new double[modelCramer.getMN()][modelCramer.getMN()];
                int index = 0;
                for (int i = 0; i < modelCramer.getMN(); i++) {
                    for (int j = 0; j < modelCramer.getMN(); j++) {
                        MatrizA[i][j] = A.get(index++);
                    }
                }
                double[][] MatrizX1 = {
                    {b.get(0), A.get(1), A.get(2)},
                    {b.get(1), A.get(4), A.get(5)},
                    {b.get(2), A.get(7), A.get(8)}
                };
                double[][] MatrizX2 = {
                    {A.get(0), b.get(0), A.get(2)},
                    {A.get(3), b.get(1), A.get(5)},
                    {A.get(6), b.get(2), A.get(8)}
                };
                double[][] MatrizX3 = {
                    {A.get(0), A.get(1), b.get(0)},
                    {A.get(3), A.get(4), b.get(1)},
                    {A.get(6), A.get(7), b.get(2)}
                };

                determinantes.add(Det3(MatrizA));
                determinantes.add(Det3(MatrizX1));
                determinantes.add(Det3(MatrizX2));
                determinantes.add(Det3(MatrizX3));

                vectorX.add(determinantes.get(1) / determinantes.get(0));
                vectorX.add(determinantes.get(2) / determinantes.get(0));
                vectorX.add(determinantes.get(3) / determinantes.get(0));
            }
        }
        modelCramer.setVectorX(vectorX);
        modelCramer.setDeterminantes(determinantes);
        return modelCramer;
    }

    private double Det2(double[][] A) {
        return A[0][0] * A[1][1] - A[0][1] * A[1][0];
    }

    private double Det3(double[][] A) {
        double D = A[0][0] * Det2(new double[][]{
            {A[1][1], A[1][2]},
            {A[2][1], A[2][2]}
        })
        - A[0][1] * Det2(new double[][]{
            {A[1][0], A[1][2]},
            {A[2][0], A[2][2]}
        })
        + A[0][2] * Det2(new double[][]{
            {A[1][0], A[1][1]},
            {A[2][0], A[2][1]}
        });
        return D;
    }

    @Override
    public GaussJordan AlgoritmoGaussJordan(GaussJordan gaussJordan) {
        int n = gaussJordan.getN();
        double tol = gaussJordan.getTo();
        boolean pivParcial = gaussJordan.isPivoteoParcial();

        double[][] A0 = gaussJordan.getA();
        double[] B0 = gaussJordan.getB();

        if (A0 == null || B0 == null || n <= 0) {
            return gaussJordan;
        }

        double[][] Ab = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(A0[i], 0, Ab[i], 0, n);
            Ab[i][n] = B0[i];
        }

        double[][] Aorig = new double[n][n];
        double[] Borig = new double[n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(A0[i], 0, Aorig[i], 0, n);
            Borig[i] = B0[i];
        }

        double det = 1.0;
        int swaps = 0;
        String[] pasos = new String[n];

        for (int k = 0; k < n; k++) {
            int pivRow = k;
            if (pivParcial) {
                double maxVal = Math.abs(Ab[k][k]);
                for (int r = k + 1; r < n; r++) {
                    double val = Math.abs(Ab[r][k]);
                    if (val > maxVal) {
                        maxVal = val;
                        pivRow = r;
                    }
                }
            }

            if (pivRow != k) {
                double[] tmp = Ab[k];
                Ab[k] = Ab[pivRow];
                Ab[pivRow] = tmp;
                swaps++;
                pasos[k] = "Intercambio renglón " + k + " ↔ " + pivRow;
            } else {
                pasos[k] = "Pivote en renglón " + k;
            }

            double piv = Ab[k][k];
            if (Math.abs(piv) < tol) {
                gaussJordan.setDe(0.0);
                gaussJordan.setX(null);
                gaussJordan.setER(Double.NaN);
                gaussJordan.setAb(Ab);
                gaussJordan.setR(null);
                gaussJordan.setPasos(pasos);
                return gaussJordan;
            }

            det *= piv;

            for (int j = k; j <= n; j++) {
                Ab[k][j] = Ab[k][j] / piv;
            }

            for (int i = 0; i < n; i++) {
                if (i == k) continue;
                double factor = Ab[i][k];
                if (factor == 0) continue;
                for (int j = k; j <= n; j++) {
                    Ab[i][j] = Ab[i][j] - factor * Ab[k][j];
                }
            }
        }

        if ((swaps & 1) == 1) det = -det;

        double[] X = new double[n];
        for (int i = 0; i < n; i++) X[i] = Ab[i][n];

        double maxRes = 0.0;
        for (int i = 0; i < n; i++) {
            double sum = 0.0;
            for (int j = 0; j < n; j++) sum += Aorig[i][j] * X[j];
            maxRes = Math.max(maxRes, Math.abs(sum - Borig[i]));
        }

        double[][] R = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(Ab[i], 0, R[i], 0, n + 1);
        }

        gaussJordan.setDe(det);
        gaussJordan.setX(X);
        gaussJordan.setER(maxRes);
        gaussJordan.setAb(Ab);
        gaussJordan.setR(R);
        gaussJordan.setPasos(pasos);

        return gaussJordan;
    }

    @Override
    public Jacobi AlgoritmoJacobi(Jacobi jacobi) {
        int n   = jacobi.getN();
        double tol = jacobi.getTo();
        int imax   = jacobi.getIteracionesMaximas();

        double[][] A = jacobi.getA();
        double[]  B  = jacobi.getB();

        if (A == null || B == null || n <= 0) {
            return jacobi;
        }
        if (jacobi.getX0() == null || jacobi.getX0().length != n) {
            jacobi.setX0(new double[n]);
        }

        double[] xOld = jacobi.getX0().clone();
        double[] xNew = new double[n];
        String[] pasos = new String[Math.max(1, imax)];
        double ER = Double.MAX_VALUE;

        for (int iter = 1; iter <= imax; iter++) {
            for (int i = 0; i < n; i++) {
                double diag = A[i][i];
                if (Math.abs(diag) < 1e-14) {
                    jacobi.setX(null);
                    jacobi.setEr(Double.NaN);
                    jacobi.setPasos(pasos);
                    return jacobi;
                }
                double sum = 0.0;
                for (int j = 0; j < n; j++) {
                    if (j == i) continue;
                    sum += A[i][j] * xOld[j];
                }
                xNew[i] = (B[i] - sum) / diag;
            }

            double maxRel = 0.0;
            for (int i = 0; i < n; i++) {
                double denom = Math.max(1e-14, Math.abs(xNew[i]));
                double e = Math.abs((xNew[i] - xOld[i]) / denom);
                if (e > maxRel) maxRel = e;
            }
            ER = maxRel;

            pasos[iter - 1] = "Iter " + iter + " | ER=" + ER;

            if (ER <= tol) break;

            System.arraycopy(xNew, 0, xOld, 0, n);
        }

        jacobi.setX(xNew);
        jacobi.setEr(ER);
        jacobi.setPasos(pasos);
        return jacobi;
    }

    @Override
    public GaussSeidel AlgoritmoGaussSeidel(GaussSeidel gaussSeidel) {
        int n   = gaussSeidel.getN();
        double tol = gaussSeidel.getTo();
        int imax   = gaussSeidel.getIteracionesMaximas();

        double[][] A = gaussSeidel.getA();
        double[]  B  = gaussSeidel.getB();

        if (A == null || B == null || n <= 0) {
            return gaussSeidel;
        }
        if (gaussSeidel.getX0() == null || gaussSeidel.getX0().length != n) {
            gaussSeidel.setX0(new double[n]);
        }

        double[] xCurr = gaussSeidel.getX0().clone();
        double[] xPrev = new double[n];
        String[] pasos = new String[Math.max(1, imax)];
        double ER = Double.MAX_VALUE;

        for (int iter = 1; iter <= imax; iter++) {
            System.arraycopy(xCurr, 0, xPrev, 0, n);

            for (int i = 0; i < n; i++) {
                double diag = A[i][i];
                if (Math.abs(diag) < 1e-14) {
                    gaussSeidel.setX(null);
                    gaussSeidel.setEr(Double.NaN);
                    gaussSeidel.setPasos(pasos);
                    return gaussSeidel;
                }
                double sum = 0.0;
                for (int j = 0; j < n; j++) {
                    if (j == i) continue;
                    double xj = (j < i) ? xCurr[j] : xPrev[j]; // GS usa valores ya actualizados
                    sum += A[i][j] * xj;
                }
                xCurr[i] = (B[i] - sum) / diag;
            }

            double maxRel = 0.0;
            for (int i = 0; i < n; i++) {
                double denom = Math.max(1e-14, Math.abs(xCurr[i]));
                double e = Math.abs((xCurr[i] - xPrev[i]) / denom);
                if (e > maxRel) maxRel = e;
            }
            ER = maxRel;

            pasos[iter - 1] = "Iter " + iter + " | ER=" + ER;

            if (ER <= tol) break;
        }

        gaussSeidel.setX(xCurr);
        gaussSeidel.setEr(ER);
        gaussSeidel.setPasos(pasos);
        return gaussSeidel;
    }
}
