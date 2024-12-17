package com.softserve.edu11_12_13.test;



import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnerSuccess implements AfterTestExecutionCallback {
    public static final Logger logger = LoggerFactory.getLogger(TestRunner.class);

    @Override
    public void afterTestExecution(ExtensionContext context) {
        Boolean testResult = context.getExecutionException().isPresent();
        logger.info("\t\t\tException.isPresent() = {}", testResult);
        logger.info("\t\t\tTest context.getDisplayName(): {}", context.getDisplayName());

        TestRunner.isTestSuccessful = !testResult;
    }
}
