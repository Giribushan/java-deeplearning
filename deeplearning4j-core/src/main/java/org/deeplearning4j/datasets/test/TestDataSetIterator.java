package org.deeplearning4j.datasets.test;

import org.deeplearning4j.datasets.iterator.DataSetIterator;
import org.deeplearning4j.datasets.iterator.DataSetPreProcessor;
import org.nd4j.linalg.dataset.DataSet;

/**
 * Track number of times the dataset iterator has been called
 * @author agibsonccc
 *
 */
public class TestDataSetIterator implements DataSetIterator {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3042802726018263331L;
	private DataSetIterator wrapped;
	private int numDataSets = 0;
    private DataSetPreProcessor preProcessor;
	
	
	public TestDataSetIterator(DataSetIterator wrapped) {
		super();
		this.wrapped = wrapped;
	}

	@Override
	public boolean hasNext() {
		return wrapped.hasNext();
	}

	@Override
	public DataSet next() {
		numDataSets++;
		DataSet next = wrapped.next();
        if(preProcessor != null)
            preProcessor.preProcess(next);
        return next;
	}

	@Override
	public void remove() {
		wrapped.remove();
	}

	@Override
	public int totalExamples() {
		return wrapped.totalExamples();
	}

	@Override
	public int inputColumns() {
		return wrapped.inputColumns();
	}

	@Override
	public int totalOutcomes() {
		return wrapped.totalOutcomes();
	}

	@Override
	public void reset() {
		wrapped.reset();
	}

	@Override
	public int batch() {
		return wrapped.batch();
	}

	@Override
	public int cursor() {
		return wrapped.cursor();
	}

	@Override
	public int numExamples() {
		return wrapped.numExamples();
	}

    /**
     * Set a pre processor
     *
     * @param preProcessor a pre processor to set
     */
    @Override
    public void setPreProcessor(DataSetPreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    public synchronized int getNumDataSets() {
		return numDataSets;
	}

	@Override
	public DataSet next(int num) {
		return wrapped.next(num);
	}
	
	
	
}
