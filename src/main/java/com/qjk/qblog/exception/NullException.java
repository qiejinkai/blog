package com.qjk.qblog.exception;

public class NullException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NullException() {
		super();
	}
	
	public NullException(String message){
		super(message);
	}
	
	public NullException(String message,Throwable e){
		super(message, e);
	}

}
