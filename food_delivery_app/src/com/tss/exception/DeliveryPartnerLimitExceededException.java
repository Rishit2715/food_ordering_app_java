package com.tss.exception;

public class DeliveryPartnerLimitExceededException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeliveryPartnerLimitExceededException(int max) {
        super("Cannot add more than " + max + " delivery partners.");
    }
}
