package application.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> mapErrors = new HashMap<>();
	
	public ValidationException(String msg) {
		super(msg);
	}
	
	public void addErrors(String keyError, String msgError) {
		this.mapErrors.put(keyError, msgError);
	}
	
	public Map<String, String> getMapErrors(){
		return this.mapErrors;
	}

}
