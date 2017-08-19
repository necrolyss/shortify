package lv.devternity.shortity.rest;

import lv.devternity.shortity.application.ApplicationGateway;
import lv.devternity.shortity.application.Command;
import lv.devternity.shortity.controls.GenerateShortLinkCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Author: jurijsgrabovskis
 * Created at: 02/08/2017.
 */
@RestController
@RequestMapping("/shortLink")
class ShortLinkController {

	@Autowired
	private ApplicationGateway applicationGateway;


	@RequestMapping(method = RequestMethod.POST)
	String generateShortLink(@RequestBody String input) {
		BasicJsonParser basicJsonParser = new BasicJsonParser();
		Map<String, Object> parsedJson = basicJsonParser.parseMap(input);

		GenerateShortLinkCommand generateShortLinkCommand = new GenerateShortLinkCommand(
			String.valueOf(parsedJson.get("inputUrl")), String.valueOf(parsedJson.get("customPath"))
		);
		Command.R.String executionResult = applicationGateway.execute(generateShortLinkCommand);
		return executionResult.response();
	}



}
