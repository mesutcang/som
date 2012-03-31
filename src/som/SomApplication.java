package som;



import java.awt.Color;
import java.awt.RenderingHints;
import java.util.List;


import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;





import topology.ConstantFunctionalFactor;
import topology.KohonenNeuron;
import topology.MatrixTopology;

public class SomApplication extends ApplicationFrame {
	public SomApplication(String title) {
		super(title);
		
       // populateData();
        final NumberAxis domainAxis = new NumberAxis("X");
        domainAxis.setAutoRangeIncludesZero(false);
        final NumberAxis rangeAxis = new NumberAxis("Y");
        rangeAxis.setAutoRangeIncludesZero(false);
        final FastScatterPlot plot = new FastScatterPlot(this.data, domainAxis, rangeAxis);
        final JFreeChart chart = new JFreeChart("SOM", plot);
//        chart.setLegend(null);

        // force aliasing of the rendered content..
        chart.getRenderingHints().put
            (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        final ChartPanel panel = new ChartPanel(chart, true);
        panel.setPreferredSize(new java.awt.Dimension(500, 270));
  //      panel.setHorizontalZoom(true);
    //    panel.setVerticalZoom(true);
        panel.setMinimumDrawHeight(10);
        panel.setMaximumDrawHeight(200);
        panel.setMinimumDrawWidth(20);
        panel.setMaximumDrawWidth(200);
        
        setContentPane(panel);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int COUNT =0; 
	private static float[][] data = new float[2][COUNT];
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double[] maxWeight = {200,100};
		ConstantFunctionalFactor constantFactor = new ConstantFunctionalFactor(0.8); 
		
		Kohonen a = new Kohonen();
		a.setMap(10,10);
		a.setNetwork(2,maxWeight);
		a.setConstantFunctionalFactor(0.8);
		a.parseData("/home/mesut/Desktop/log.out.ligth");
		a.setLearningFunction(20,constantFactor);
		a.learn();
		/*System.out.println(a.topologyToString());
		System.out.println(a.networkToString());
		System.exit(0);
		*/
	
		
		KohonenNeuron c[]=a.getNeuronList();
		int neuronListSize = c.length;
		
		
		 COUNT  =neuronListSize;
		 data = new float[2][COUNT];
	
		
		for (int i = 0; i < neuronListSize; i++) {
			List list = a.topologyGetConnectedNeurons(i);
			Color renk = getColor(list.size());
			double  v [] =c[i].getWeight();
			data[0][i] = (float) v[0];
            data[1][i] = (float) v[1];
			
			/*
			this.data[0][i] = x;
            this.data[1][i] = 100000 + (float) Math.random() * COUNT;
			*/
			
		}
		
		final SomApplication graph = new SomApplication("Self Orginizing Maps");
		graph.pack();
        RefineryUtilities.centerFrameOnScreen(graph);
        graph.setVisible(true);
        
	
		

	}
	private static Color getColor(int size) {
		return  new Color(size*50,size*50,size*50);
	}

}
