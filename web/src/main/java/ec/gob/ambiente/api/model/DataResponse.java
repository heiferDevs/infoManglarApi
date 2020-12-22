package ec.gob.ambiente.api.model;

import lombok.Getter;
import lombok.Setter;

public class DataResponse {

	public static final String SUCCESS_STATE = "OK";
	public static final String ERROR_STATE = "ERROR";

	@Getter
    @Setter
    private String state;

	public DataResponse(String state){
		this.state = state;
	}

}
