package com.slickqa.client.junit;

import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Created by Keith on 11/28/16.
 */
public class SlickSuite extends Suite {
    public final SlickJunitController controller = SlickJunitController.getInstance();

    public SlickSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
        init();
    }

    protected void init() {
        controller.createSuiteResults(this.getDescription().getChildren());
    }
}
