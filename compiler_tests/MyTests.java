package tinycc.tests;

import org.junit.Test;
import prog2.tests.CompilerTests;
import prog2.tests.FatalCompilerError;
import tinycc.parser.Lexer;

import java.io.StringReader;

import static org.junit.Assert.fail;

/**
 * Created by Pascal on 17.07.2016.
 */
public class MyTests extends CompilerTests {

    public static String getCategory() {
        return "public";
    }

    public static String getExercise() {
        return "Semantics";
    }

    private void runIntegrationTest(final String name, final String code) {
        final StringReader r = new StringReader(code);
        final Lexer l = new Lexer(diagnostic, r, name);
        compiler.parseTranslationUnit(l);
        compiler.checkSemantics();
    }

    @Test
    public void testTypeCheckPositive() {
        runIntegrationTest("testTypeCheckPositive",  "void z(){42 + 1337 ;\n return ;}");
    }
    @Test
     public void testFalseReturn() {
         final String name = "testTypeCheckNegative";
         try {
             runIntegrationTest(name, "void foo() { return 1; }");
             fail("checkSemantics didn't fail.");
         } catch (final FatalCompilerError exc) {
         }
     }

    @Test
    public void testNullpointer() {
        final String name = "testTypeCheckNegative";
        final String code= "void swap(int *a, int *b) {\n" +
                "  int t = *a;\n" +
                "  void* k;\n" +
                "  *b = 0;\n" +
                "  k = b;\n" +
                "  b = k;\n" +
                "  *a = t;\n" +
                "}\n";
        runIntegrationTest("testTypeCheckPositive",  code);
    }
    @Test
    public void testCharCast() {
        runIntegrationTest("testTypeCheckPositive",  "void z(){char x;\n x=5; ;\n return ;}");
    }

    @Test
    public void testIFFalse1() {
        final String name = "testTypeCheckNegative";
        try {
            runIntegrationTest(name, "void foo() {int x;\n if(void) x }");
            fail("checkSemantics didn't fail.");
        } catch (final FatalCompilerError exc) {
        }
    }

    @Test
    public void testDerefVoidpointer() {
        final String name = "testTypeCheckNegative";
       try {
           runIntegrationTest(name, "void foo() {int x;\n void* k;\n  *k; }");
            fail("checkSemantics didn't fail.");
        } catch (final FatalCompilerError exc) {
       }
    }

    @Test
    public void testconditionexp() {
        final String name = "testTypeCheckNegative";
        try {
            runIntegrationTest(name, "void foo() {int x;\n int* k; x= x? 5:k ;}");
            fail("checkSemantics didn't fail.");
        } catch (final FatalCompilerError exc) {
        }
    }

    @Test
    public void testPointerArithmetik() {
        final String name = "testTypeCheckNegative";
        final String code= "void swap(int *a, int *b) {\n" +
                "  int t = *a;\n" +
                "  void* k;\n" +
                "  b = b+1;\n" +
                "  t = b-a;\n" +
                "  b = a-1;\n" +
                "  *a = t;\n" +
                "}\n";
        runIntegrationTest("testTypeCheckPositive",  code);
    }

    @Test
    public void testEqualPointer() {
        final String name = "testTypeCheckNegative";
        final String code= "void swap(int *a, int *b) {\n" +
                "  int t = *a;\n" +
                "  void* k;\n" +
                "  t = b==k;\n" +
                "  t = k==b;\n" +
                "  t = b==a;\n" +
                "  t = a==0;\n" +
                "  t = 0==a;\n" +
                "  t = a!=0;\n" +
                "  t = a!=b;\n" +
                "  t = a!=k;\n" +
                "  t = a<b;\n" +
                "  *a = t;\n" +
                "}\n";
        runIntegrationTest("testTypeCheckPositive",  code);
    }

    @Test
    public void testAssignFalse() {
        final String name = "testTypeCheckNegative";
       try {
            runIntegrationTest(name, "void foo() {int x; if(x) 5=x;}");
            fail("checkSemantics didn't fail.");
        } catch (final FatalCompilerError exc) {
        }
    }

    @Test
    public void testAnd() {
        final String name = "testTypeCheckNegative";
        try {
            runIntegrationTest(name, "void foo() {int x; x=&5;}");
            fail("checkSemantics didn't fail.");
        } catch (final FatalCompilerError exc) {
        }
    }
}
