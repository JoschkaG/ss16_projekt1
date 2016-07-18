package tinycc.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

//import org.junit.Rule;
import org.junit.Test;

//import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import prog2.tests.CompilerTests;
import prog2.tests.FatalCompilerError;
import tinycc.diagnostic.PrintDiagnostic;
import tinycc.implementation.Compiler;
import tinycc.parser.Lexer;

import java.io.StringReader;

/**
 * Within this class and/or package you can implement your own tests that will
 * be run with the reference implementation.
 *
 * Note that no classes or interfaces will be available, except those initially
 * provided. Make use of {@link} to get other factory instances.
 */
public class MyCompilerTest extends CompilerTests {

	/*
	@Rule
	public TestRule timeout = new DisableOnDebug(new Timeout(25000));
	*/

	private void runIntegrationTest(final String name, final String code) {
		final StringReader r = new StringReader(code);
		final Lexer l = new Lexer(diagnostic, r, name);
		compiler.parseTranslationUnit(l);
		compiler.checkSemantics();
	}

	@Test
	public void testTypeCheckNegativeReturn() {
		final String name = "testTypeCheckNegativeReturn";
		try {
			runIntegrationTest(name, "int foo() { return; }");
			fail("checkSemantics didn't fail.");
		} catch (final FatalCompilerError exc) {
			//checkLocation(exc, name, 1, 24);
		}
	}

	@Test
	public void testVoidAssign() {
		final String name = "testVoidAssign";
		try {
			runIntegrationTest(name, "void foo() { void y;\ny = 5;\nreturn; ");
			fail("checkSemantics didn't fail");
		} catch (final FatalCompilerError exc) {
			//TODO checkLocation
		}
	}

	@Test
	public void testPointer1() {
		final String name = "testPointer1";
		runIntegrationTest(name, "void foo() {int *x;\nint **y;\nint *z;\nint a;\nint b;\n" +
				"a = 5;\nb = 3;\nx = &a;\ny = &x;\nz = &b;\nreturn;}");
	}

	@Test
	public void testPointer2() {
		final String name = "testPointer2";
		runIntegrationTest(name, "void foo() {int *x;\nint **y;\nint *z;\nint a;\nint b;\n" +
				"a = 5;\nb = 3;\nx = &a;\ny = &x;\nz = &b;\n" +
				"a = ((*y) > z);\nreturn;}");
	}

	@Test
	public void testNullPointerConstant() {
		final String name = "testNullPointerConstant";
		runIntegrationTest(name,"void foo() { int a = 2;\nint *b = &a;\na = ((b) == 0);\nreturn;}");
	}

	@Test
	public void testTypeCheckPositiveReturn() {
		final String name = "testTypeCheckPositiveReturn";
		runIntegrationTest(name, "void foo() { return; }");
	}

}
