package priv.henryyu.privatebox.model.response;

import priv.henryyu.privatebox.model.response.error.ResponseCode;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2017年12月22日上午9:41:42
 * @version 1.0.0
 */
public class ResponseMessage<T> {
	private ResponseCode code;
	private String message;
	private T data;
	
	
	public ResponseCode getCode() {
		return code;
	}
	public void setCode(ResponseCode code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
 

