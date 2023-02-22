import java.awt.Color;
import java.awt.Shape;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Renderer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

public class P3 {

	private final int M;
	private List<Double[]> trainDataSet;
	private List<ArrayList<Double[]> > clusters;
	private	Double[][] centroids;

	public P3(int M , List<Double[]> trainDataSet ) {
		this.M=M;
		this.trainDataSet=trainDataSet;
		this.centroids = new Double[M][2];
		this.clusters = new ArrayList<ArrayList<Double[]> >();

	}
	
	private void initializeCentroid() {
		for(int centroid=0; centroid<M; centroid++) {
			//Pick M random points from trainingSet as centroids.
		    int rnd = new Random().nextInt(this.trainDataSet.size());
		    centroids[centroid][0] = this.trainDataSet.get(rnd)[0];
		    centroids[centroid][1] = this.trainDataSet.get(rnd)[1];
		    
		    this.clusters.add(new ArrayList<Double[]>());//Initialize our cluster.
		}
		
	}
	
	public Double k_means() { //Clusters all the training data and calculates the total error.
		int t =0;
		initializeCentroid();

		while(true) {
			t+=1;
			
			this.clusters = new ArrayList<ArrayList<Double[]> >();
			for(int m=0; m<M; m++) {
			    this.clusters.add(new ArrayList<Double[]>());//Initialize our cluster.
			}
			
			for(Double[] x : this.trainDataSet) {
				Double[] distances = new Double[M];
				
				//Calculate distances from centroids.
				for(int i=0; i<M; i++) {
					Double a = x[0] - this.centroids[i][0];
					Double b = x[1] - this.centroids[i][1];
					Double distance = Math.pow(a, 2) + Math.pow(b, 2); 
					distances[i] = distance;
								
				}
				
				//Find minimum distance from centroids.
				Double minDistance=1000000.0;
				int index=0;
				for(int centroid=0; centroid<M; centroid++) {
					if(distances[centroid] < minDistance) {//Pairnw thn mikroteri diafora kai briskw to antistoixo cluster tou centroid,me to index.
						minDistance = distances[centroid];
						index = centroid;
					}
				}
				
				//Add x to cluster.
				this.clusters.get(index).add(x);
	
			}//end of outter for
				
			//Up until now we have our clusters filled with elements.
			
			//Calculate new centroids.
			int m=0;
			Double[][] newCentroids= new Double[M][2];

			for(ArrayList<Double[]> cluster : this.clusters) {//For each cluster.
				Double[] newCentroid= {0.0 , 0.0};
				Double allx0 = 0.0;
				Double allx1 = 0.0;
			
				
				
				for(Double[] x : cluster) {
					allx0= allx0 + x[0];
					allx1= allx1 + x[1];
				}
				newCentroid[0]= allx0 / cluster.size();
				newCentroid[1]= allx1 / cluster.size();
				//Change centroids.
				newCentroids[m][0] = newCentroid[0];
				newCentroids[m][1] = newCentroid[1];
				m = m + 1;
			}
			
			//Check for termination.
			int flag = haveChangedCentroids(newCentroids);
			if(flag == 0) {
				break;
			}
			else {
				for(int i=0; i<M; i++) {
					this.centroids[i][0] = newCentroids[i][0];
					this.centroids[i][1] = newCentroids[i][1];
				}
			}
			
		}//end of while.
		//System.out.println("t= "+t);
		return clusteringError();
	}//End of method.

	public Double clusteringError() {
		int clusterIndex=0;
		Double error =0.0;
		for(ArrayList<Double[]>  cluster : this.clusters) {
			for(Double[] x : cluster) {
				Double a = x[0] - this.centroids[clusterIndex][0];
				Double b = x[1] - this.centroids[clusterIndex][1];
				error = error + Math.pow(a, 2) + Math.pow(b, 2); 
			}
			clusterIndex+=1;

		}
		return error;
		
	}
	
	public void printCentroids() {
		
		/*System.out.print("\n");
		for(int i=0; i<M; i++) {
			System.out.println("Centroid: " +i+ " ,x0: "+this.centroids[i][0] +" x1: "+this.centroids[i][1]);
		}*/
	}
	
	public int haveChangedCentroids(Double[][] newCentroids) {//This method checks if centroids have changed. Returns 0 centroids are the same.
		
		/*for(int m=0; m<M; m++) {
			System.out.println("\nCentroid: "+m);
			System.out.println("Old: "+ this.centroids[m][0] + "\t" + this.centroids[m][1]);
			System.out.println("New: "+ newCentroids[m][0] + "\t" + newCentroids[m][1]);
		}*/
		
		int flag=0;
		for(int m=0; m<M; m++) {
			if(newCentroids[m][0].equals(this.centroids[m][0]) && newCentroids[m][1].equals(this.centroids[m][1])  ) {
				
			}
			else {
				flag=-1;
			}
		}
		
		return flag;
	}

	public List<ArrayList<Double[]>>  getClusters(){
		return this.clusters;
	}
	
	public Double[][] getCentroids(){
		return this.centroids;
	}

	public void createChart() {
		Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
		Shape diamond =ShapeUtilities.createDiamond(5);
		XYSeriesCollection clusterDataset = new XYSeriesCollection();
		XYSeriesCollection centroidDataset = new XYSeriesCollection();
		
        XYSeries clusterSeries = new XYSeries("Clusters");
        XYSeries centroidSeries = new XYSeries("Centroids");
        JFreeChart clusterPlot = ChartFactory.createScatterPlot("Clusters", "X","Y",clusterDataset,PlotOrientation.VERTICAL, false,false,false);
        JFreeChart centroidPlot = ChartFactory.createScatterPlot("Centroids", "X","Y",centroidDataset,PlotOrientation.VERTICAL, false,false,false);
        
        for(ArrayList<Double[]> cluster : clusters) {
        	for(Double[] x : cluster) {
        		clusterSeries.add(x[0],x[1]);
        	}
        }
        //Change the shape of the points.
        XYPlot plot = (XYPlot) clusterPlot.getPlot();
        XYItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesShape(0, cross);
        clusterDataset.addSeries(clusterSeries);
        
        for(Double[] centroid : centroids) {
        	centroidSeries.add(centroid[0],centroid[1]);
		}        
        
        plot = (XYPlot) centroidPlot.getPlot();
        renderer = plot.getRenderer();
        renderer.setSeriesShape(0, diamond);
        centroidDataset.addSeries(centroidSeries);
        
        String filename1 = "resources/clusters(M="+this.M+").png";
        String filename2 = "resources/centroids(M="+this.M+").png";
        
        try {
			ChartUtilities.saveChartAsPNG(new File(filename1), clusterPlot, 600, 400);
			ChartUtilities.saveChartAsPNG(new File(filename2), centroidPlot, 600, 400);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}//End of class.
