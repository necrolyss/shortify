package lv.devternity.shortity.rest;

import lv.devternity.shortity.application.ApplicationGateway;
import lv.devternity.shortity.application.Command;
import lv.devternity.shortity.controls.TrackVisitCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: jurijsgrabovskis
 * Created at: 02/08/2017.
 */
@RestController
@RequestMapping("/r")
class TrackingController {

    @Autowired
    private ApplicationGateway applicationGateway;

    @RequestMapping("/{shortlink}")
	void redirectToDestination(@PathVariable("shortlink") String shortlink, HttpServletRequest request, HttpServletResponse response) throws IOException {
        TrackVisitCommand trackVisitCommand = new TrackVisitCommand(shortlink, "77.241.168.151"/*request.getRemoteAddr()*/);
        Command.R.String executionResult = applicationGateway.executeRemote(trackVisitCommand);
        response.sendRedirect(executionResult.response());
    }

}
