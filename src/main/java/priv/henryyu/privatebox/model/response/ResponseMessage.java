package priv.henryyu.privatebox.model.response;

import priv.henryyu.privatebox.model.response.error.ResponseError;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2017年12月22日上午9:41:42
 * @version 1.0.0
 */
public class ResponseMessage<T> {
	private ResponseError error;
	private String message;
	private T data;
	
	public ResponseError getError() {
		return error;
	}
	public void setError(ResponseError error) {
		this.error = error;
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
 

