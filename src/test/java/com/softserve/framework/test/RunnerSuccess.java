package com.softserve.framework.test;

import com.softserve.edu06arch.TestRunnerGoogle;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RunnerSuccess implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Boolean testResult = context.getExecutionException().isPresent();
        System.out.println("\t\t\t\tException.isPresent() = " + testResult); //false - SUCCESS, true - FAILED
        System.out.println("\t\t\t\tTest context.getDisplayName(): "+ context.getDisplayName());
        //
        TestRunner.isTestSuccessful = !testResult;
    }
}
