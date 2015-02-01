#Image Classification Filter for WEKA

##Preparing the datasets

Datasets (in ARFF format) should contain a minimum of two attributes: a string (which stores the filenames of the images) and a class.
Additional attributes may also be in the dataset and they will be copied unchanged by the filter.

For example, this is dogs.arff from the data folder:
````
@relation dogs
@attribute filename string
@attribute class {BEAGLE,GERMAN_SHORTHAIRED}
@data
img_beagle_1.jpg,BEAGLE
img_beagle_10.jpg,BEAGLE
img_beagle_12.jpg,BEAGLE
img_beagle_13.jpg,BEAGLE
img_beagle_14.jpg,BEAGLE
img_beagle_15.jpg,BEAGLE
img_german_shorthaired_42.jpg,GERMAN_SHORTHAIRED
img_german_shorthaired_43.jpg,GERMAN_SHORTHAIRED
img_german_shorthaired_44.jpg,GERMAN_SHORTHAIRED
img_german_shorthaired_45.jpg,GERMAN_SHORTHAIRED
img_german_shorthaired_46.jpg,GERMAN_SHORTHAIRED
img_german_shorthaired_47.jpg,GERMAN_SHORTHAIRED
img_german_shorthaired_48.jpg,GERMAN_SHORTHAIRED
img_german_shorthaired_49.jpg,GERMAN_SHORTHAIRED

````

##Using the filters from the command line
Run the image filter, specifying your ARFF file with the image filenames and the class index as the input. The output should be the name of the file where you want the images stored, e.g.:
````
java -cp classpath_to_weka weka.filters.unsupervised.instance.imagefilter.BasicImageFeatures -i dogs.arff -o dogs_features.arff

````

Note: In dogs.arff above, filenames are *not* fully qualified.
Therefore you should change working directory to the directory containing the images before executing the above command.
Alternatively, it is possible to replace each filename with its fully qualified name and run the filtering command from anywhere.

##To perform an image classification experiment from the command line
Use the feature-extracted ARFF file to run experiments, e.g.:

````
java -cp classpath_to_weka weka.classifiers.functions.SMO -t ~/Desktop/dogs_features.arff
````

##Sources for the software and data used in this repository

LIRE 0.9.3 https://code.google.com/p/lire/

WEKA 3.7.12 http://www.cs.waikato.ac.nz/ml/weka/

Butterfly & birds images http://www-cvr.ai.uiuc.edu/ponce_grp/data/

Oxford IIIT Pet Dataset http://www.robots.ox.ac.uk/~vgg/data/pets/
