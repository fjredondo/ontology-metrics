package metrics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import services.OntologyUtils;
import um.ontoenrich.config.LaInputParameters;

/**
 * The Class NamesPerPropertyMetric.
 */
public class NamesPerPropertyMetric extends AnnotationsPerEntityAbstractMetric{
	
	/** The Constant METRIC_NAME. */
	private static final String METRIC_NAME = "Names per property";

	/* (non-Javadoc)
	 * @see metrics.Metric#calculate()
	 */
	@Override
	public double calculate() throws OWLOntologyCreationException, FileNotFoundException, IOException, Exception {
		super.writeToDetailedOutputFile("Metric\tProperty\tMetric Value\n");
		int numberOfNames = 0;
		int totalProperties = 0;
		
		for(OWLObjectProperty owlObjectProperty : super.getOntology().getObjectPropertiesInSignature()){
			if(OntologyUtils.isObsolete(owlObjectProperty, getOntology()) || owlObjectProperty.isOWLTopObjectProperty()){
				continue;
			}
			totalProperties++;
			int localNumberOfNames = getNumberOfNames(owlObjectProperty);
			super.writeToDetailedOutputFile(String.format(Locale.ROOT, "%s\t%s\t%d\n", this.getName(), owlObjectProperty.toStringID(), localNumberOfNames));
			numberOfNames = numberOfNames + localNumberOfNames;
		}
		
		for(OWLDataProperty owlDataProperty : super.getOntology().getDataPropertiesInSignature()){
			if(OntologyUtils.isObsolete(owlDataProperty, getOntology()) || owlDataProperty.isOWLTopDataProperty()){
				continue;
			}
			totalProperties++;
			int localNumberOfNames = getNumberOfNames(owlDataProperty);
			super.writeToDetailedOutputFile(String.format(Locale.ROOT, "%s\t%s\t%d\n", this.getName(), owlDataProperty.toStringID(), localNumberOfNames));
			numberOfNames = numberOfNames + localNumberOfNames;
		}
		
		for(OWLAnnotationProperty owlAnnotationProperty : super.getOntology().getAnnotationPropertiesInSignature()){
			totalProperties++;
			if(OntologyUtils.isObsolete(owlAnnotationProperty, getOntology())){
				continue;
			}
			int localNumberOfNames = getNumberOfNames(owlAnnotationProperty);
			super.writeToDetailedOutputFile(String.format(Locale.ROOT, "%s\t%s\t%d\n", this.getName(), owlAnnotationProperty.toStringID(), localNumberOfNames));
			numberOfNames = numberOfNames + localNumberOfNames;
		}
		return ((double) (numberOfNames)) / totalProperties;
	}

	/* (non-Javadoc)
	 * @see metrics.Metric#setParameters(um.ontoenrich.config.LaInputParameters)
	 */
	@Override
	public void setParameters(LaInputParameters parameters) {
		
	}

	/* (non-Javadoc)
	 * @see metrics.Metric#getName()
	 */
	@Override
	public String getName() {
		return METRIC_NAME;
	}

}
