package com.softserve.edu14.utils;

import com.softserve.edu14.data.TestResultContextData;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.softserve.edu14.tests.TestsRunner.testResultContext;

public class TestResultStatus implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        boolean isTestFailed = context.getExecutionException().isPresent();
        String errorInfo = context.getExecutionException().map(Throwable::toString)
                .orElse("unknown getExecutionException()");
        String testInfo = context.getUniqueId();

        testResultContext = new TestResultContextData(isTestFailed, errorInfo, testInfo);
    }
}

