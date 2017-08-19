package lv.devternity.shortity.rest;

import lv.devternity.shortity.application.ApplicationGateway;
import lv.devternity.shortity.controls.GetAllShortlinksCommand;
import lv.devternity.shortity.controls.GetVisitsCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: jurijsgrabovskis
 * Created at: 03/08/2017.
 */
@RestController
@RequestMapping("/stat")
public class StatisticsController {

    @Autowired
    private ApplicationGateway applicationGateway;

    @RequestMapping("/visits/{shortlink}")
    String visits(@PathVariable("shortlink") String shortlink) {
        GetVisitsCommand visitsCommand = new GetVisitsCommand(shortlink);
        return applicationGateway.execute(visitsCommand).response();
    }

    @RequestMapping("shortlinks")
    String allShortLinks() {
        GetAllShortlinksCommand allShortlinksCommand = new GetAllShortlinksCommand();
        return applicationGateway.execute(allShortlinksCommand).response();
    }

}
