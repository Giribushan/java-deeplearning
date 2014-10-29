package org.deeplearning4j.scaleout.iterativereduce.single;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import org.deeplearning4j.nn.BaseNeuralNetwork;
import org.deeplearning4j.scaleout.iterativereduce.Updateable;


public class UpdateableSingleImpl implements Updateable<BaseNeuralNetwork> {

	
	private static final long serialVersionUID = 6547025785641217642L;
	private BaseNeuralNetwork wrapped;
	private Class<? extends BaseNeuralNetwork> clazz;
	

	public UpdateableSingleImpl(BaseNeuralNetwork network) {
		wrapped = network;
		if(clazz == null)
			clazz = network.getClass();
	}
	
	@Override
	public ByteBuffer toBytes() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os2 = new ObjectOutputStream(os);
			os2.writeObject(wrapped);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		
		return ByteBuffer.wrap(os.toByteArray());
		
	}

	@Override
	public void fromBytes(ByteBuffer b) {
		wrapped = new BaseNeuralNetwork.Builder<>()
				.withClazz(clazz).buildEmpty();
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(b.array())));
		wrapped.load(dis);
	}

	@Override
	public void fromString(String s) {
		
	}

	@Override
	public BaseNeuralNetwork get() {
		return wrapped;
	}

	@Override
	public void set(BaseNeuralNetwork type) {
		this.wrapped = type;
	}

	@Override
	public void write(DataOutputStream dos) {
		wrapped.write(dos);
	}




}
