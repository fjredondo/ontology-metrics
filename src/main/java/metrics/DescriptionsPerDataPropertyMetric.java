package metrics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import services.OntologyUtils;
import um.ontoenrich.config.LaInputParameters;

/**
 * The Class DescriptionsPerDataPropertyMetric.
 */
public class DescriptionsPerDataPropertyMetric extends AnnotationsPerEntityAbstractMetric{
	
	/** The Constant NAME. */
	private static final String NAME = "Descriptions per data property";

	/* (non-Javadoc)
	 * @see metrics.Metric#calculate()
	 */
	@Override
	public double calculate() throws OWLOntologyCreationException, FileNotFoundException, IOException, Exception {
		super.writeToDetailedOutputFile("Metric\tData Property\tMetric Value\n");
		int numberOfDescriptions = 0;
		int numberOfEntities = 0;
		for(OWLDataProperty owlDataProperty : super.getOntology().getDataPropertiesInSignature()){
			if (OntologyUtils.isObsolete(owlDataProperty, getOntology()) || owlDataProperty.isOWLTopDataProperty()) {
				continue;
			}
			int localNumberOfdescriptions = getNumberOfDescriptions(owlDataProperty);
			super.writeToDetailedOutputFile(String.format(Locale.ROOT, "%s\t%s\t%d\n", this.getName(), owlDataProperty.toStringID(), localNumberOfdescriptions));
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
		return NAME;
	}
}

