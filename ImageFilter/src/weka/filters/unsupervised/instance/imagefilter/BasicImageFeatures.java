/*
 * A batch filter enabling features to be extracted from images.
 * Uses the LIRE 0.9.3 feature extraction libaries.
 * 
 * The first attribute in the data *must* be a string, specifically the filename 
 * of the image. Image feature attributes are added to the dataset by the filter, 
 * and the all string attributes are removed.
 */

package weka.filters.unsupervised.instance.imagefilter;

import java.awt.image.BufferedImage;

import net.semanticmetadata.lire.imageanalysis.BasicFeatures;


public class BasicImageFeatures extends AbstractImageFeatures {

	private static final long serialVersionUID = 8658527406486139573L;

	// Global info method for describing the filter
	public String globalInfo() {
		return "A batch filter for extracting eight basic statistical features from images.";
	}

	// Each subclass *MUST* override this method so that the number of produced features is known!
	protected int getNumFeatures(){
		return 8;
	}
	
	// Each subclass *MUST* override this method by returning a unique feature name prefix!
	protected String getFeatureNamePrefix(){
		return "B";
	}
	
	// Each subclass *MUST* override this method
	protected double[] getFeatures(BufferedImage img){
		BasicFeatures features=new BasicFeatures();
		features.extract(img);
		return features.getDoubleHistogram();
	}
	
	// Main method
	public static void main(String[] args) {
		runFilter(new BasicImageFeatures(), args);
	}
}