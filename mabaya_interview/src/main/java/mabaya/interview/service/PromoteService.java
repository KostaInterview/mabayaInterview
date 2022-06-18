package mabaya.interview.service;

import mabaya.interview.dto.CampaignDto;
import mabaya.interview.dto.ProductResponceDto;

public interface PromoteService {

	CampaignDto createCampaign(CampaignDto campaignDto);
	
	ProductResponceDto serveAd(String category);
}
