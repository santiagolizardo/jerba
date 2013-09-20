package com.santiagolizardo.jerba.utilities;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.santiagolizardo.jerba.utilities.StringUtils;

@RunWith(Parameterized.class)
public class StringUtilsTest extends TestCase {

	private String expectedString;
	private String inputString;

	@Parameters
	public static Collection<Object[]> dataProvider() {
		Object[][] parameters = new Object[][] { { "", null }, { "", "" },
				{ "foo-bar-baz", "FOO BAR BAZ" },
				{ "foo-bar-baz", "FOO      bar     	baz" },
				{ "foo-bar-baz", "FOO! (BAR BAZ)" },
				{ "foo-bar-baz", "     FOO!-(BAR-BAZ)" },
				{ "foo-bar-baz", "_____FOO!-(BAR-BAZ)" } };
		return Arrays.asList(parameters);
	}

	public StringUtilsTest(String expectedString, String inputString) {
		this.expectedString = expectedString;
		this.inputString = inputString;
	}

	@Test
	public void testSanitize() {
		assertEquals(expectedString, StringUtils.sanitize(inputString));
	}
}
