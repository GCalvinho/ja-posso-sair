package pt.i9.template.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.i9.template.Data.CovidStatusResponse;
import pt.i9.template.Integration.CovidStatusService;

import javax.annotation.Resource;

@Controller
public class ViewsController {

    //Views
    public static String HOMEPAGE_HTML = "index";

    //Messages
    public static String message = "Já posso sair?";

    @Resource
    CovidStatusService covidStatusService;

    @RequestMapping(value = "/")
    public String homepage(Model model){

        model.addAttribute("mainMessage", message);
        CovidStatusResponse data = covidStatusService.fetchStatus();

        model.addAttribute("country", data.getResponse().stream().findFirst().get().country);
        model.addAttribute("newCases", data.getResponse().stream().findFirst().get().getCases().getNewCases());
        model.addAttribute("day", data.getResponse().stream().findFirst().get().getDay());

        return HOMEPAGE_HTML;
    }

}
