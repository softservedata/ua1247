package com.softserve.edu14.data;

public class TestResultContextData {
    private final boolean isTestFailed;
    private final String errorInfo;
    private final String testInfo;

    public TestResultContextData(boolean isTestFailed, String errorInfo, String testInfo) {
        this.isTestFailed = isTestFailed;
        this.errorInfo = errorInfo;
        this.testInfo = testInfo;
    }

    public boolean isTestFailed() {
        return isTestFailed;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public String getTestInfo() {
        return testInfo;
    }
}