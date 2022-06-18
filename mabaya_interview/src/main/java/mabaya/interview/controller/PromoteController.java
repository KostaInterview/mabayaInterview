package mabaya.interview.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mabaya.interview.dto.CampaignDto;
import mabaya.interview.dto.ProductResponceDto;
import mabaya.interview.service.PromoteService;

@RestController
@RequestMapping("/promotion")
public class PromoteController {

	
	PromoteService promoteService;
	
	public PromoteController(PromoteService promoteService) {
		this.promoteService = promoteService;
	}

	@PostMapping(value = "/createCampaign")
	public CampaignDto createCampaign(@RequestBody CampaignDto campaignDto) {
		if(campaignDto != null)
		return promoteService.createCampaign(campaignDto);
		return null;
	}
	
	@GetMapping(value ="/serveAd/{category}")
	public ProductResponceDto serveAd(@PathVariable String category) {
		return  promoteService.serveAd(category);
	}
}
