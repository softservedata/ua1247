package com.softserve.edu11_12_13.test;



import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnerSuccess implements AfterTestExecutionCallback {
    public static final Logger logger = LoggerFactory.getLogger(TestRunner.class);

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Boolean testResult = context.getExecutionException().isPresent();
        System.out.println("\t\t\t\tException.isPresent() = " + testResult); //false - SUCCESS, true - FAILED
        System.out.println("\t\t\t\tTest context.getDisplayName(): "+ context.getDisplayName());
        //
        TestRunner.isTestSuccessful = !testResult;
    }
}
