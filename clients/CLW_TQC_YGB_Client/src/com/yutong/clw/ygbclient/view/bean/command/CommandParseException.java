package com.yutong.clw.ygbclient.view.bean.command;

public class CommandParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -184251544902681265L;

	public CommandParseException(String message) {
		super(message);
	}

	private int parseExceptionCode;

	public int getParseExceptionCode() {
		return parseExceptionCode;
	}

	public void setParseExceptionCode(int parseExceptionCode) {
		this.parseExceptionCode = parseExceptionCode;
	}

}
