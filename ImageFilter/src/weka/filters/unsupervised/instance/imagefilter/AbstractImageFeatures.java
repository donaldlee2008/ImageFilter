package weka.filters.unsupervised.instance.imagefilter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import weka.core.Attribute;
import weka.core.Capabilities;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.Capabilities.Capability;
import weka.filters.SimpleBatchFilter;

public abstract class AbstractImageFeatures extends SimpleBatchFilter {

	private static final long serialVersionUID = -407198591679465350L;

	// Global info method for describing the filter
	public String globalInfo() {
		return "An abstract batch filter for extracting features of any type from images.";
	}

	// Capabilities, indicating that any type of attribute with or without a class is OK
	public Capabilities getCapabilities() {
		Capabilities result = super.getCapabilities();
		result.disableAll();
		result.enable(Capability.STRING_ATTRIBUTES);
		result.enable(Capability.NOMINAL_ATTRIBUTES);
		result.enable(Capability.NUMERIC_ATTRIBUTES);
		result.enable(Capability.NO_CLASS);
		return result;
	}

	// Method to construct the header for the filtered dataset
	protected Instances determineOutputFormat(Instances inputFormat) {
		Instances result = new Instances(inputFormat, 0);
		int numFeatures=getNumFeatures();
		for (int index = numFeatures-1; index >= 0; index--) {
			result.insertAttributeAt(new Attribute(getFeatureNamePrefix() + index),1);
		}
		result.deleteAttributeAt(0);
		return result;
	}

	// Method to actually filter the dataset
	protected Instances process(Instances inst) throws Exception {
		// check that first attribute is a string
		if (!inst.attribute(0).isString()) {
			throw new Exception(
					"ImageFilter Exception: dataset's first attribute must be the string filename of an image!");
		}
		// create the new dataset
		Instances result = new Instances(determineOutputFormat(inst), 0);
		// iterate over the instances
		for (int example_index = 0; example_index < inst.numInstances(); example_index++) {
			// load the file from disk into a buffered image
			String filename = inst.instance(example_index).stringValue(0);
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File(filename));
			} catch (IOException e) {
				throw new Exception(
						"ImageFilter Exception: was unable to read file "
								+ filename + "!");
			}
			// extract features from the buffered image
			double[] features=getFeatures(img);
			// create the final feature vector and new instance
			double[] values = new double[result.numAttributes()];
			
			// Copy the features from features to values
			for (int index=0; index<features.length;index++)
				values[index]=features[index];
			// Copy any additional attributes from the old instance to values
			for (int index=1; index<inst.numAttributes();index++)
				values[features.length+index-1]=inst.instance(example_index).value(index);
			
			// Done -- create and add an instance to the new dataset
			result.add(new DenseInstance(1, values));
		}
		// done -- return new dataset
		return result;
	}

	// Each subclass *MUST* override this method so that the number of produced features is known!
	abstract protected int getNumFeatures();
	
	// Each subclass *MUST* override this method by returning a unique feature name prefix!
	abstract protected String getFeatureNamePrefix();
	
	// Each subclass *MUST* override this method
	abstract protected double[] getFeatures(BufferedImage img);
}
