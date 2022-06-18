package mabaya.interview.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class CampaignNameAlreadyExists extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2137985986208282753L;
	public CampaignNameAlreadyExists(String name) {
		super("Campaign" + name + " already exists");
	}
}
