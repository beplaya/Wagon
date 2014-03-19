package com.aj.wagon;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

public class TestRunner extends RobolectricTestRunner {
	public TestRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
	}
}
