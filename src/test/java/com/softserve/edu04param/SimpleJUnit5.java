package com.softserve.edu04param;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

class RunnerExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Boolean testResult = context.getExecutionException().isPresent();
        System.out.println("\t\t\t\tException.isPresent() = " + testResult); //false - SUCCESS, true - FAILED
        System.out.println("\t\t\t\tTest context.getDisplayName(): "+ context.getDisplayName());
        //
        SimpleJUnit5.isTestSuccessful = !testResult;
    }
}

@ExtendWith(RunnerExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SimpleJUnit5 {
    protected static Boolean isTestSuccessful = false;

    @BeforeAll
    public static void setup() {
        System.out.println("@BeforeAll executed");
    }

    @AfterAll
    public static void tear() {
        System.out.println("@AfterAll executed");
    }

    @BeforeEach
    public void setupThis() {
        System.out.println("\t@BeforeEach executed");
    }

    @AfterEach
    public void tearThis(TestInfo testInfo) {
        if (!isTestSuccessful) {
            System.out.println("\t\t\tgetTestMethod = " + testInfo.getTestMethod());
            System.out.println("\t\t\tgetDisplayName = " + testInfo.getDisplayName());
            // delete session
        }
        //
        System.out.println("\t@AfterEach executed");
    }

    @DisplayName("Should pass ... testOne()")
    @Test
    @Order(4)
    public void testOne() {
        System.out.println("\t\t@Test testOne()");
        Assertions.assertEquals(4, 2 + 2);
    }

    @DisplayName("Should pass ... testOne2()")
    @Test
    @Order(3)
    public void testOne2() {
        System.out.println("\t\t@Test testOne2()");
        Assertions.assertEquals(5, 2 + 3 + 1);
    }

    @DisplayName("Should pass ... testOne3()")
    @Test
    @Order(2)
    public void testOne3() {
        System.out.println("\t\t@Test testOne3()");
        Assertions.assertEquals(7, 2 + 5);
        if (2 == 1 + 1) {
            throw new RuntimeException("my errror");
        }
    }

    @DisplayName("Should pass ... testTwo()")
    @Test
    @Order(1)
    public void testTwo() {
        System.out.println("\t\t@Test testTwo()");
        Assertions.assertTrue(6 == 2 + 4);
    }

}
