package fr.inria.lille.commons.trace;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import xxl.java.extensions.collection.ListLibrary;
import xxl.java.extensions.collection.MapLibrary;
import xxl.java.extensions.collection.SetLibrary;
import xxl.java.extensions.junit.TestCase;
import xxl.java.extensions.junit.TestCasesListener;
import xxl.java.extensions.support.Function;

public class SpecificationTestCasesListener<T> extends TestCasesListener {

	public SpecificationTestCasesListener(RuntimeValues runtimeValues, Function<Integer, T> outputForEachTrace) {
		this.runtimeValues = runtimeValues;
		this.outputForEachTrace = outputForEachTrace;
		collectedTraces = MapLibrary.newHashMap();
	}
	
	@Override
	protected void processBeforeRun() {
    	runtimeValues().enable();
    }
    
	@Override
	protected void processSuccessfulRun(TestCase testCase) {
		for (Entry<Map<String, Object>, Integer> uniqueTrace : runtimeValues().uniqueTraceSet()) {
			Map<String, Object> trace = uniqueTrace.getKey();
			T output = outputForEachTrace().outputFor(uniqueTrace.getValue());
			if (! collectedTraces().containsKey(trace)) {
				collectedTraces().put(trace, output);
			} else {
				T specifiedOutput = specifiedOutput(trace);
				if (! (specifiedOutput == null || specifiedOutput.equals(output))) {
					collectedTraces().put(trace, null);
				}
			}
		}
	}
	
	@Override
    protected void processTestFinished(TestCase testCase) {
    	runtimeValues().reset();
	}
    
	@Override
    protected void processAfterRun() {
    	runtimeValues().disable();
    }
    
    public Collection<Specification<T>> specifications() {
    	Collection<Specification<T>> specifications = ListLibrary.newLinkedList();
    	for (Map<String, Object> input : collectedTraces().keySet()) {
    		if (! isInconsistentTrace(input)) {
    			specifications.add(new Specification<T>(input, specifiedOutput(input)));
    		}
    	}
    	return specifications;
    }
    
    protected Collection<Map<String, Object>> inconsistentTraces() {
    	Collection<Map<String, Object>> inconsistentTraces = SetLibrary.newHashSet();
    	for (Map<String, Object> input : collectedTraces().keySet()) {
    		if (isInconsistentTrace(input)) {
    			inconsistentTraces.add(input);
    		}
    	}
    	return inconsistentTraces;
    }
    
    protected boolean isInconsistentTrace(Map<String, Object> trace) {
    	return specifiedOutput(trace) == null;
    }
    
    private T specifiedOutput(Map<String, Object> trace) {
    	return collectedTraces().get(trace);
    }
    
    private RuntimeValues runtimeValues() {
    	return runtimeValues;
    }
    
    private Function<Integer, T> outputForEachTrace() {
    	return outputForEachTrace;
    }
    
    protected Map<Map<String, Object>, T> collectedTraces() {
    	return collectedTraces;
    }

    private RuntimeValues runtimeValues;
    private Function<Integer, T> outputForEachTrace;
    private Map<Map<String, Object>, T> collectedTraces;
}