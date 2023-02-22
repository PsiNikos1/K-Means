import java.awt.Color;
import java.awt.Shape;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.ShapeUtilities;

public class Main {
	
	
	public static List<Double[]> createTrainDataSet(){
		List<Double[]> trainDataSet = new ArrayList<Double[]>() ;
		
		//Create random x1&x2.
			//1)
			for(int i=0; i<150; i++) {
				Random random = new Random();
				Double min = 0.75;
				Double max = 1.25;
				Double x[]={min + (max - (min) ) * random.nextDouble() ,min + (max - (min) ) * random.nextDouble()};
				if(x[0]<0.75 || x[1]<0.75 || x[0]>1.25 || x[1]>1.25) {
					System.out.println("False in creating examples.");
					System.out.println("x0: "+ x[0] +"\t"+x[1]);
					System.exit(0);
				}
				trainDataSet.add(x);
			}
			//2)
			for	(int i=0; i<150; i++) {
				Random random = new Random();
				Double min = 0.0;
				Double max = 0.5;
				Double x[]={min + (max - (min) ) * random.nextDouble() ,min + (max - (min) ) * random.nextDouble()};
				
				trainDataSet.add(x);
			}
			//3)
			for	(int i=0; i<150; i++) {
				Random random = new Random();
				Double minx0 = 0.0;
				Double maxx0 = 0.5;
				Double minx1 = 1.5;
				Double maxx1 = 2.0;
				
				Double x[]={minx0 + (maxx0 - (minx0) ) * random.nextDouble() , minx1 + (maxx1 - (minx1) ) * random.nextDouble()};
				trainDataSet.add(x);
			}
			//4)
			for	(int i=0; i<150; i++) {
				Random random = new Random();
				Double minx0 = 1.5;
				Double maxx0 = 2.0;
				Double minx1 = 0.0;
				Double maxx1 = 0.5;
				
				Double x[]={minx0 + (maxx0 - (minx0) ) * random.nextDouble() , minx1 + (maxx1 - (minx1) ) * random.nextDouble()};
				trainDataSet.add(x);
			}	
			//5)
			for	(int i=0; i<150; i++) {
				Random random = new Random();
				Double minx0 = 1.5;
				Double maxx0 = 2.0;
				Double minx1 = 1.5;
				Double maxx1 = 2.0;
				
				Double x[]={minx0 + (maxx0 - (minx0) ) * random.nextDouble() , minx1 + (maxx1 - (minx1) ) * random.nextDouble()};
				trainDataSet.add(x);
			}
			
			//6)
			for	(int i=0; i<75; i++) {
				Random random = new Random();
				Double minx0 = 0.6;
				Double maxx0 = 0.8;
				Double minx1 = 0.0;
				Double maxx1 = 0.4;
				
				Double x[]={minx0 + (maxx0 - (minx0) ) * random.nextDouble() , minx1 + (maxx1 - (minx1) ) * random.nextDouble()};
				trainDataSet.add(x);
			}
			
			//7)
			for	(int i=0; i<75; i++) {
				Random random = new Random();
				Double minx0 = 0.6;
				Double maxx0 = 0.8;
				Double minx1 = 1.6;
				Double maxx1 = 2.0;
				
				Double x[]={minx0 + (maxx0 - (minx0) ) * random.nextDouble() , minx1 + (maxx1 - (minx1) ) * random.nextDouble()};
				trainDataSet.add(x);
			}
			
			//8)
			for	(int i=0; i<75; i++) {
				Random random = new Random();
				Double minx0 = 1.2;
				Double maxx0 = 1.4;
				Double minx1 = 0.0;
				Double maxx1 = 0.4;
				
				Double x[]={minx0 + (maxx0 - (minx0) ) * random.nextDouble() , minx1 + (maxx1 - (minx1) ) * random.nextDouble()};
				trainDataSet.add(x);
			}
			//9)
			for	(int i=0; i<75; i++) {
				Random random = new Random();
				Double minx0 = 1.2;
				Double maxx0 = 1.4;
				Double minx1 = 1.6;
				Double maxx1 = 2.0;
				
				Double x[]={minx0 + (maxx0 - (minx0) ) * random.nextDouble() , minx1 + (maxx1 - (minx1) ) * random.nextDouble()};
				trainDataSet.add(x);
			}
			//10)
			for	(int i=0; i<150; i++) {
				Random random = new Random();
				Double minx0 = 0.0;
				Double maxx0 = 2.0;
				Double minx1 = 0.0;
				Double maxx1 = 2.0;
				
				Double x[]={minx0 + (maxx0 - (minx0) ) * random.nextDouble() , minx1 + (maxx1 - (minx1) ) * random.nextDouble()};
				trainDataSet.add(x);
			}
		return trainDataSet;
	}
	
	public static void main(String[] args) {				
		List<Double[]> trainDataSet = createTrainDataSet();
		List<ArrayList<Double[]>> clusters = new ArrayList<ArrayList<Double[]>>();
		Double[][]  centroids = new Double[3][2];

		Double minError=10000.0;
		int M =3;
		
		//Run for M=3.
			P3 p3 = new P3(M,trainDataSet);
			for(int i=0; i<20; i++) {
				Double error = p3.k_means(); 
				//System.out.println(error);
				if(minError > error ) {		
					minError = error;
					//System.out.println(minError);
					clusters = p3.getClusters();
					centroids = p3.getCentroids();
				}
				
			}	
			System.out.println("minError for M=3: "+minError);
			p3.createChart();
			
		//Run for M=5;
			M=5;
			P3 p5 = new P3(M,trainDataSet);
			for(int i=0; i<20; i++) {
				Double error = p5.k_means(); 
				//System.out.println(error);
				if(minError > error ) {		
					minError = error;
					//System.out.println(minError);
					clusters = p5.getClusters();
					centroids = p5.getCentroids();
				}
				
			}	
			System.out.println("minError for M=5: "+minError);
			p5.createChart();
	        
		//Run for M=7;
			M=7;
			P3 p7 = new P3(M,trainDataSet);
			for(int i=0; i<20; i++) {
				Double error = p7.k_means(); 
				//System.out.println(error);
				if(minError > error ) {		
					minError = error;
					//.out.println(minError);
					clusters = p7.getClusters();
					centroids = p7.getCentroids();
				}
			}	
			System.out.println("minError for M=7: "+minError);
			p7.createChart();
			
			
			//Run for M=9;
			M=9;
			P3 p9 = new P3(M,trainDataSet);
			for(int i=0; i<20; i++) {
				Double error = p9.k_means(); 
				//System.out.println(error);
				if(minError > error ) {		
					minError = error;
					//System.out.println(minError);
					clusters = p9.getClusters();
					centroids = p9.getCentroids();
				}
				
			}	
			System.out.println("minError for M=9: "+minError);
			p9.createChart();
	        		
		//Run for M=11; 
			M=11;
			P3 p11 = new P3(M,trainDataSet);
			for(int i=0; i<20; i++) {
				Double error = p11.k_means(); 
				//System.out.println(error);
				if(minError > error ) {		
					minError = error;
					//System.out.println(minError);
					clusters = p11.getClusters();
					centroids = p11.getCentroids();
				}
				
			}	
			System.out.println("minError for M=11: "+minError);
			p11.createChart();
	        	
		//Run for M=13;
			M=13;
			P3 p13 = new P3(M,trainDataSet);
			for(int i=0; i<20; i++) {
				Double error = p13.k_means(); 
				//System.out.println(error);
				if(minError > error ) {		
					minError = error;
					//System.out.println(minError);
					clusters = p13.getClusters();
					centroids = p13.getCentroids();
				}
				
			}	
			System.out.println("minError for M=13: "+minError);
			p13.createChart();

		System.out.println("Programm ended successfully");
				
				
		        
		
		
		
	}//end of main.
}//end of class.


	