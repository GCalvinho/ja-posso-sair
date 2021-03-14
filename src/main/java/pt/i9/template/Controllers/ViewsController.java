package pt.i9.template.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.i9.template.Data.CovidStatusEnum;
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
    public static String decreasing_indicator = "-";

    @Value( "${countryAtEmergencyStatus}" )
    public Boolean countryAtEmergencyStatus;

    @Resource
    CovidStatusService covidStatusService;

    @RequestMapping(value = "/")
    public String homepage(Model model){

        CovidStatusResponse data = covidStatusService.fetchStatus();

        model.addAttribute("mainMessage", message);
        model.addAttribute("country", data.getResponse().stream().findFirst().get().country);
        model.addAttribute("newCases", data.getResponse().stream().findFirst().get().getCases().getNewCases());
        model.addAttribute("day", data.getResponse().stream().findFirst().get().getDay());
        model.addAttribute("covidStatus", verifyStatus());
        model.addAttribute("numbersIndicator", verifyNewCasesIndicator(data));

        return HOMEPAGE_HTML;
    }

    public String verifyStatus(){
        CovidStatusEnum actualStatus;

        try{
            if(countryAtEmergencyStatus){
                actualStatus = CovidStatusEnum.notAllowedToTakeAWalk;
            }else if(!countryAtEmergencyStatus){
                actualStatus = CovidStatusEnum.carefullyAllowedToTakeAWalk;
            }
            else{
                actualStatus = CovidStatusEnum.allowedToTakeAWalk;
            }
        }catch (Exception e) {
            log.info("Not possible to determine Covid Data. Proceeding being careful!");
            actualStatus = CovidStatusEnum.carefullyAllowedToTakeAWalk;
        }

        return actualStatus.toString();
    }

    public String verifyNewCasesIndicator(CovidStatusResponse data){
        if(data.getResponse().stream().findFirst().get().getCases().getNewCases().contains(increasing_indicator)){
            return increasing_indicator;
        }else{
            return decreasing_indicator;
        }
    }

}
