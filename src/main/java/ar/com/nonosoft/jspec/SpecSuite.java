package ar.com.nonosoft.jspec;

import ar.com.nonosoft.jspec.component.description.impl.RootDescription;
import ar.com.nonosoft.jspec.exception.JSpecException;
import ar.com.nonosoft.jspec.output.Report;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpecSuite {

	public SpecSuite addAllSpecsOn(String packageName) {
		Reflections reflections = new Reflections(packageName);
		Iterator<Class<? extends Specification>> iterator = reflections.getSubTypesOf(Specification.class).iterator();
		while(iterator.hasNext())
			specifications.add((Class<Specification<?>>) iterator.next());
		return this;
	}
	public <SPEC extends Specification<?>> SpecSuite addSpec(Class<SPEC> specification) {
		specifications.add((Class<Specification<?>>) specification);
		return this;
	}

	public SpecWriter writer() {
		return new SpecWriter(this);
	}

	public void run() {
		runSpecifications();
		descriptions.forEach(RootDescription::run);
		System.out.println(report);
	}

	private void runSpecifications() {
		for(Class<Specification<?>> specification : specifications) {
			try {
				specification.newInstance().run(report);
			} catch (Exception e) {
				throw new JSpecException("Error when run " + specification.getName() + " specification.", e);
			}
		}
	}

	List<RootDescription> descriptions() {
		return descriptions;
	}

	Report report() {
		return report;
	}

	private List<Class<Specification<?>>> specifications;

	private List<RootDescription> descriptions;

	private Report report;

	public SpecSuite() {
		specifications = new ArrayList<>();
		descriptions = new ArrayList<>();
		report = new Report();
	}
}
