package com.softserve.edu04param;

import com.softserve.edu03elem.PageFactoryTest;
import com.softserve.edu05search.SearchTest;
import org.junit.platform.suite.api.*;

@Suite
@SuiteDisplayName("JUnit Platform Suite Demo")
//@IncludeClassNamePatterns(".Simple*") // TODO
//@SelectPackages({"com.softserve.edu04param"})
@SelectPackages({"com.softserve.edu05search"}) // Do not use @IncludeClassNamePatterns
//@SelectClasses({SearchTest.class, PageFactoryTest.class})
//@SelectMethod(type = SimpleJUnit5.class, name = "testOne")
//@SelectMethod(type = SimpleJUnit5.class, name = "testTwo")
//@SelectMethod(type = SearchTest.class, name = "findByCss")
//@SelectMethod("com.softserve.edu05search.SearchTest#findByCss")
@IncludeTags("slow")  // running only tests annotated with the @Tag("slow").
class SuiteDemo {
}