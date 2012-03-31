
package topology;

public class ConstantFunctionalFactor {
    
    private double constant;
    
    public ConstantFunctionalFactor(double constant) {
        this.constant = constant;
    }
    
    public double[] getParameters(){
        double[] parameteres = new double[1];
        parameteres[0] = constant;
        return parameteres;
    }
    public void setParameters(double[] parameters){
        constant = parameters[0];
    }
    
    public double getValue(int k){
        return constant;
    }
}
