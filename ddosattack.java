import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;
import java.util.Random;

public class DDoSDetection {
    public static void main(String[] args) throws Exception {
        // Load dataset
        DataSource source = new DataSource("path_to_dataset.arff");
        Instances dataset = source.getDataSet();
        
        // Set class index to the last attribute
        if (dataset.classIndex() == -1)
            dataset.setClassIndex(dataset.numAttributes() - 1);

        // Randomize the dataset
        dataset.randomize(new Random(1));

        // Split dataset into training (70%) and testing (30%) set
        int trainSize = (int) Math.round(dataset.numInstances() * 0.7);
        int testSize = dataset.numInstances() - trainSize;
        Instances train = new Instances(dataset, 0, trainSize);
        Instances test = new Instances(dataset, trainSize, testSize);

        // Create a classifier (J48 decision tree)
        J48 classifier = new J48();
        classifier.buildClassifier(train);

        // Evaluate the classifier
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(classifier, test);
        System.out.println(eval.toSummaryString("\nResults\n======\n", false));
    }
}
