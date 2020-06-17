package com.engine;

public class EngineException extends Exception {

	public EngineException() {
		super();
	}

	public EngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EngineException(String message, Throwable cause) {
		super(message, cause);
	}

	public EngineException(String message) {
		super(message);
	}

	public EngineException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = 1L;

}
