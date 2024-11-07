package com.softserve.edu04param;

import org.junit.jupiter.api.Test;

public class AppTest2 {

    // mvn test -Dtest=AppTest2  -Dapplication.password=QWERTY
    @Test
    public void testApp() {
        // From Maven
        System.out.println("***surefire.java.version = " + System.getProperty("surefire.application.password"));
        // From OS
        System.out.println("***System.getenv(\"JAVA_HOME\") = " + System.getenv("JAVA_HOME"));
        System.out.println("***System.getenv(\"DEFAULT_PASS\") = " + System.getenv("DEFAULT_PASS"));
        // From Eclipse/Idea
        System.out.println("***System.getenv().MY_IDE = " + System.getenv().get("MY_IDE"));
    }
}
