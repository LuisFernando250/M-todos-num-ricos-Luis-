package mx.edu.itses.lfuab.MetodosNumericos.web;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.lfuab.MetodosNumericos.domain.ReglaCramer;
import mx.edu.itses.lfuab.MetodosNumericos.domain.GaussJordan;
import mx.edu.itses.lfuab.MetodosNumericos.domain.Jacobi;
import mx.edu.itses.lfuab.MetodosNumericos.domain.GaussSeidel;
import mx.edu.itses.lfuab.MetodosNumericos.services.UnidadIIIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class Unit3Controller {

    @Autowired
    private UnidadIIIService unidadIIIService;

    @GetMapping("/unit3")
    public String index(Model model) {
        return "unit3/index";
    }

    // ---------- Regla de Cramer ----------
    @GetMapping("/unit3/formreglacramer")
    public String formReglaCramer(Model model) {
        ReglaCramer modelCramer = new ReglaCramer();
        model.addAttribute("modelCramer", modelCramer);
        return "unit3/reglacramer/formreglacramer";
    }

    @PostMapping("/unit3/solvecramer")
    public String solveCramer(ReglaCramer modelCramer, Errors error, Model model) {
        var solveCramer = unidadIIIService.AlgoritmoReglaCramer(modelCramer);
        model.addAttribute("solveCramer", solveCramer);
        return "unit3/reglacramer/solvecramer";
    }

    // ---------- Gauss-Jordan ----------
    @GetMapping("/unit3/formgaussjordan")
    public String formGaussJordan(Model model) {
        GaussJordan gaussJordan = new GaussJordan();
        model.addAttribute("gaussJordan", gaussJordan);
        return "unit3/gaussjordan/formgaussjordan";
    }

    @PostMapping("/unit3/solvegaussjordan")
    public String solveGaussJordan(GaussJordan gaussJordan, Errors error, Model model) {
        var solveGaussJordan = unidadIIIService.AlgoritmoGaussJordan(gaussJordan);
        model.addAttribute("solveGaussJordan", solveGaussJordan);
        return "unit3/gaussjordan/solvegaussjordan";
    }

    // ---------- Jacobi ----------
    @GetMapping("/unit3/formjacobi")
    public String formJacobi(Model model) {
        Jacobi jacobi = new Jacobi();
        model.addAttribute("jacobi", jacobi);
        return "unit3/jacobi/formjacobi";
    }

    @PostMapping("/unit3/solvejacobi")
    public String solveJacobi(
            Jacobi jacobi,
            Errors error,
            Model model,
            @RequestParam("n") int n,
            @RequestParam("to") double to,
            @RequestParam("iteracionesMaximas") int iteracionesMaximas,
            @RequestParam("matrizA[]") List<Double> matrizAFlat,
            @RequestParam("vectorB[]") List<Double> vectorBFlat,
            @RequestParam(value = "vectorX0[]", required = false) List<Double> vectorX0Flat
    ) {
        double[][] A = new double[n][n];
        double[] B = new double[n];
        double[] X0 = new double[n];

        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = matrizAFlat.get(idx++);
            }
        }
        for (int i = 0; i < n; i++) {
            B[i] = vectorBFlat.get(i);
        }
        if (vectorX0Flat != null && vectorX0Flat.size() == n) {
            for (int i = 0; i < n; i++) X0[i] = vectorX0Flat.get(i);
        } else {
            for (int i = 0; i < n; i++) X0[i] = 0.0;
        }

        jacobi.setN(n);
        jacobi.setTo(to);
        jacobi.setIteracionesMaximas(iteracionesMaximas);
        jacobi.setA(A);
        jacobi.setB(B);
        jacobi.setX0(X0);

        var solveJacobi = unidadIIIService.AlgoritmoJacobi(jacobi);
        model.addAttribute("solveJacobi", solveJacobi);
        return "unit3/jacobi/solvejacobi";
    }

    // ---------- Gauss-Seidel ----------
    @GetMapping("/unit3/formseidel")
    public String formSeidel(Model model) {
        GaussSeidel seidel = new GaussSeidel();
        model.addAttribute("seidel", seidel);
        return "unit3/seidel/formseidel";
    }

    @PostMapping("/unit3/solveseidel")
    public String solveSeidel(
            GaussSeidel seidel,
            Errors error,
            Model model,
            @RequestParam("n") int n,
            @RequestParam("to") double to,
            @RequestParam("iteracionesMaximas") int iteracionesMaximas,
            @RequestParam("matrizA[]") List<Double> matrizAFlat,
            @RequestParam("vectorB[]") List<Double> vectorBFlat,
            @RequestParam(value = "vectorX0[]", required = false) List<Double> vectorX0Flat
    ) {
        double[][] A = new double[n][n];
        double[] B = new double[n];
        double[] X0 = new double[n];

        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = matrizAFlat.get(idx++);
            }
        }
        for (int i = 0; i < n; i++) {
            B[i] = vectorBFlat.get(i);
        }
        if (vectorX0Flat != null && vectorX0Flat.size() == n) {
            for (int i = 0; i < n; i++) X0[i] = vectorX0Flat.get(i);
        } else {
            for (int i = 0; i < n; i++) X0[i] = 0.0;
        }

        seidel.setN(n);
        seidel.setTo(to);
        seidel.setIteracionesMaximas(iteracionesMaximas);
        seidel.setA(A);
        seidel.setB(B);
        seidel.setX0(X0);

        var solveSeidel = unidadIIIService.AlgoritmoGaussSeidel(seidel);
        model.addAttribute("solveSeidel", solveSeidel);
        return "unit3/seidel/solveseidel";
    }
}
