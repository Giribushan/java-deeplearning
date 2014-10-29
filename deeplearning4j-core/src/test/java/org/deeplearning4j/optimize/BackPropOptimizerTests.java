package org.deeplearning4j.optimize;

import org.nd4j.linalg.api.activation.Activations;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.deeplearning4j.nn.BaseMultiLayerNetwork.ParamRange;

import org.deeplearning4j.models.classifiers.dbn.DBN;
import org.deeplearning4j.models.featuredetectors.rbm.RBM;
import org.deeplearning4j.optimize.optimizers.BackPropOptimizer;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the back prop optimizer
 */
public class BackPropOptimizerTests {

    private static Logger log = LoggerFactory.getLogger(BackPropOptimizerTests.class);


    @Test
    public void testHessianFree() {

    }

    @Test
    public void testBackPropOptimizerIndices() {

        double preTrainLr = 0.01;
        int preTrainEpochs = 10000;
        int k = 1;
        int nIns = 4,nOuts = 3;
        int[] hiddenLayerSizes = new int[] {4,3,2};
        double fineTuneLr = 0.01;
        int fineTuneEpochs = 10000;


        DBN dbn = new DBN.Builder()
                .hiddenLayerSizes(hiddenLayerSizes)
                .build();

        INDArray params = dbn.params();
        assertEquals(1,params.rows());
        assertEquals(params.columns(),params.length());
        dbn.setLabels(Nd4j.create(1, nOuts));


        BackPropOptimizer op = new BackPropOptimizer(dbn,1e-1f,1000);
        INDArray layerParams = op.getParameters();

        ParamRange r = dbn.startIndexForLayer(0);
        double firstWeightForParam = (double) layerParams.getScalar(r.getwStart() + 1).element();
        double firstWeightInNetwork = (double) dbn.getNeuralNets()[0].getW().getScalar(1).element();
        assertEquals(0,r.getwStart());
        int len = dbn.getNeuralNets()[0].getW().length();
        assertEquals(len,r.getwEnd());
        assertEquals(dbn.getNeuralNets()[0].gethBias().length(),Math.abs(r.getBiasStart() - r.getBiasEnd()));

        ParamRange r2 = dbn.startIndexForLayer(1);
        assertEquals(dbn.getNeuralNets()[0].getW().length() + dbn.getNeuralNets()[0].gethBias().length(),r2.getwStart());


        double secondWeightForParam = (double) layerParams.getScalar(r2.getwStart() + 1).element();
        double secondWeightInNetwork = (double) dbn.getNeuralNets()[1].getW().getScalar(1).element();


        assertEquals(true,firstWeightForParam == firstWeightInNetwork);
        assertEquals(true,secondWeightForParam == secondWeightInNetwork);

        assertEquals(op.getNumParameters(),op.getParameters().length());
        assertEquals(op.getNumParameters(),op.getValueGradient(0).length());
        assertEquals(op.getParameters().length(),op.getValueGradient(0).length());


        assertEquals(dbn.getNeuralNets()[1].gethBias().length(),Math.abs(r2.getBiasStart() - r2.getBiasEnd()));

    }


}
