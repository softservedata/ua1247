package com.softserve.edu04param;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Simple2JUnit5 {
    protected Boolean isTestSuccessful = false;

    @BeforeAll
    public void setup() {
        System.out.println("@BeforeAll executed");
    }

    @AfterAll
    public void tear() {
        System.out.println("@AfterAll executed");
    }

    @BeforeEach
    public void setupThis() {
        isTestSuccessful = false;
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

    @Test
    public void testOne() {
        System.out.println("\t\t@Test testOne()");
        Assertions.assertEquals(4, 2 + 2);
        isTestSuccessful = true;
    }

    @Test
    public void testOne2() {
        System.out.println("\t\t@Test testOne2()");
        Assertions.assertEquals(5, 2 + 3 + 1);
        isTestSuccessful = true;
    }

    @Test
    public void testOne3() {
        System.out.println("\t\t@Test testOne3()");
        Assertions.assertEquals(7, 2 + 5);
        if (2 == 1 + 1) {
            throw new RuntimeException("my errror");
        }
        isTestSuccessful = true;
    }

    @Test
    public void testTwo() {
        System.out.println("\t\t@Test testTwo()");
        Assertions.assertTrue(6 == 2 + 4);
        isTestSuccessful = true;
    }

}
