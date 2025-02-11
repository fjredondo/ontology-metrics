package metrics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import services.OntologyUtils;
import um.ontoenrich.config.LaInputParameters;

/**
 * The Class DescriptionsPerAnnotationPropertyMetric.
 */
public class DescriptionsPerAnnotationPropertyMetric extends AnnotationsPerEntityAbstractMetric{
	
	/** The Constant NAME. */
	private static final String NAME = "Descriptions per annotation property";

	/* (non-Javadoc)
	 * @see metrics.Metric#calculate()
	 */
	@Override
	public double calculate() throws OWLOntologyCreationException, FileNotFoundException, IOException, Exception {
		super.writeToDetailedOutputFile("Metric\tAnnotation Property\tMetric Value\n");
		int numberOfDescriptions = 0;
		int numberOfEntities = 0;
		for(OWLAnnotationProperty annotationProperty : super.getOntology().getAnnotationPropertiesInSignature()){
			if (OntologyUtils.isObsolete(annotationProperty, getOntology())) {
				continue;
			}
			int localNumberOfdescriptions = getNumberOfDescriptions(annotationProperty);
			super.writeToDetailedOutputFile(String.format(Locale.ROOT, "%s\t%s\t%d\n", this.getName(), annotationProperty.toStringID(), localNumberOfdescriptions));
			numberOfDescriptions = numberOfDescriptions + localNumberOfdescriptions;
			numberOfEntities ++;
		}
		return ((double) (numberOfDescriptions)) / numberOfEntities;

	}

	/* (non-Javadoc)
	 * @see metrics.Metric#setParameters(um.ontoenrich.config.LaInputParameters)
	 */
	@Override
	public void setParameters(LaInputParameters parameters) {
		// Not used
		
	}

	/* (non-Javadoc)
	 * @see metrics.Metric#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
	}
}
