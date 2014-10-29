package org.deeplearning4j.iterativereduce.actor.core;

import java.io.Serializable;
/**
 * Used to reset the batch actor
 * @author Adam Gibson
 *
 */
public class ResetMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2662222481547249559L;

	
	private static ResetMessage INSTANCE = new ResetMessage();
	
	private ResetMessage() {}
	
	public static ResetMessage getInstance() {
		return INSTANCE;
	}


}
