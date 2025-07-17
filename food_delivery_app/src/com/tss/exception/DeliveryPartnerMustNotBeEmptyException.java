package com.tss.exception;


public class DeliveryPartnerMustNotBeEmptyException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DeliveryPartnerMustNotBeEmptyException() {
        super("No delivery partners are configured.");
    }
    public DeliveryPartnerMustNotBeEmptyException(String message) {
        super(message);
    }
}
 