package pt.i9.template.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.i9.template.Data.CovidStatusResponse;
import pt.i9.template.Integration.CovidStatusService;

import javax.annotation.Resource;

@Controller
public class ViewsController {
    private static final Logger log = LoggerFactory.getLogger(ViewsController.class);

    //Views
    public static String HOMEPAGE_HTML = "index";

    //Messages
    public static String message = "Já posso sair?";
    public static String increasing_indicator = "+";

    @Resource
    CovidStatusService covidStatusService;

    @RequestMapping(value = "/")
    public String homepage(Model model){

        CovidStatusResponse data = covidStatusService.fetchStatus();

        model.addAttribute("mainMessage", message);
        model.addAttribute("country", data.getResponse().stream().findFirst().get().country);
        model.addAttribute("newCases", data.getResponse().stream().findFirst().get().getCases().getNewCases());
        model.addAttribute("day", data.getResponse().stream().findFirst().get().getDay());
        model.addAttribute("finallyFree", verifyNewCasesIndicator(data));

        return HOMEPAGE_HTML;
    }

    public Boolean verifyNewCasesIndicator(CovidStatusResponse data){
        Boolean finallyFree;

        try{
            if(data.getResponse().stream().findFirst().get().getCases().getNewCases().contains(increasing_indicator)){
                finallyFree = Boolean.FALSE;
            }else{
                finallyFree = Boolean.TRUE;
            }
        }catch (Exception e) {
            log.info("Not possible to determine Covid Data. Proceeding being careful!");
            finallyFree = Boolean.FALSE;
        }

        return finallyFree;
    }

}
