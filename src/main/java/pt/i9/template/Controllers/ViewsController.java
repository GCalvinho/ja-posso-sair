package pt.i9.template.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewsController {

    public static String message = "Hello from the server side!";

    @RequestMapping(value = "/")
    public String homepage(Model model){
        model.addAttribute("variableFromController", message);
        return "homepage";
    }

}
