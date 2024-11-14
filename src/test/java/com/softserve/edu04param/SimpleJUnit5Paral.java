package com.softserve.edu04param;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
public class SimpleJUnit5Paral {

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
        System.out.println("\t\t\tgetTestMethod = " + testInfo.getTestMethod());
        System.out.println("\t\t\tgetDisplayName = " + testInfo.getDisplayName());
        //
        System.out.println("\t@AfterEach executed");
    }

    @Test
    public void testOne() {
        System.out.println("\t\t@Test testOne(), ThreadId = " + Thread.currentThread().getId());
        Assertions.assertEquals(4, 2 + 2);
    }

    @Test
    public void testTwo() {
        System.out.println("\t\t@Test testTwo(), ThreadId = " + Thread.currentThread().getId());
        Assertions.assertTrue(6 == 2 + 4);
    }

}
