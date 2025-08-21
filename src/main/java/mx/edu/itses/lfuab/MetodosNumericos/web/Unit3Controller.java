
package mx.edu.itses.lfuab.MetodosNumericos.web;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.lfuab.MetodosNumericos.domain.ReglaCramer;
import mx.edu.itses.lfuab.MetodosNumericos.services.UnidadIIIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class Unit3Controller {
 
    
   @Autowired
    private UnidadIIIService unidadIIIService;
    
    
    @GetMapping("/unit3")
    public String index(Model model){
        return "unit3/index";
    }
    
    @GetMapping("/unit3/formreglacramer")
    public String formReglaCramer(Model model){
        ReglaCramer modelCramer = new ReglaCramer();
        model.addAttribute("modelCramer", modelCramer);
        return "unit3/reglacramer/formreglacramer";
    }
    
    @PostMapping("/unit3/solvecramer")
    public String solveCramer(ReglaCramer modelCramer, Errors error, Model model){
        // log.info("OBJETOS: " + modelCramer);
      var solveCramer = unidadIIIService.AlgoritmoReglaCramer(modelCramer);
      // log.info("Solución: " + solveCramer.getVectorX());
        model.addAttribute("solveCramer", solveCramer);      
       return "unit3/reglacramer/solvecramer";
    }
}
