package mabaya.interview.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoSuchCategoryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6452743307754783761L;

	public NoSuchCategoryException(String category) {
		super("Category" + category + " does not exist");
	}
}
