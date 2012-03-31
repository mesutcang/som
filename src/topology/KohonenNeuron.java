
package topology;

import java.util.Random;

public class KohonenNeuron {
    private double[] weight;
    public String[] values;
    private ActivationFunctionModel activationFunction;
    
    
    private MetricModel distanceFunction;
    
    public KohonenNeuron(int weightNumber, double[] maxWeight, ActivationFunctionModel activationFunction){
        if(weightNumber == maxWeight.length){
            Random rand = new Random();
            weight = new double[weightNumber];
            for(int i=0; i< weightNumber; i++){
                weight[i] = rand.nextDouble() * maxWeight[i];
            }
        }
        this.activationFunction = activationFunction;
    }
    
    public KohonenNeuron(double[] weightArray,ActivationFunctionModel activationFunction) {
        int weightSize = weightArray.length;
        weight = new double[weightSize];
        for(int i=0; i< weightSize; i++){
            weight[i] = weightArray[i];
        }
        this.activationFunction = activationFunction;
    }
    
    public double[] getWeight(){
        return weight.clone();
    }
    public double getValue(double[] inputVector){
        double value = 0;
        int inputSize = inputVector.length;
        if ( distanceFunction != null){
            value = distanceFunction.getDistance(weight, inputVector);
        }else{
            for(int i=0; i< inputSize; i++){
                value = value + inputVector[i] * weight[i];
            }
        }
        if( activationFunction != null)
            return activationFunction.getValue(value);
        else
            return value;
    }
    
    public void setWeight(double[] weight){
        for (int i=0; i< weight.length; i++){
            this.weight[i] = weight[i];
        }
    }
    
    public String toString(){
        String text="";
        text += "[ ";
        int weightSize = weight.length;
        for (int i=0; i< weightSize; i++){
            text += weight[i];
            if(i < weightSize -1 ){
                text += ", ";
            }
        }
        text += " ]";
        return text;
    }

    public MetricModel getDistanceFunction() {
        return distanceFunction;
    }

    public void setDistanceFunction(MetricModel distanceFunction) {
        this.distanceFunction = distanceFunction;
    }
}



interface MetricModel {
    
    public double getDistance(double[] firstVector, double[] secondVector);
}
interface ActivationFunctionModel {
    
    public void setParameteres(double[] paramateresList);
    public double[] getParamateres();
    public double getValue(double inputValue);
    
}
