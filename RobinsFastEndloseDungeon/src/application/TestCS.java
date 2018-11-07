package application;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

class TestCS {

	@Test
	void test() {
		CS test = new CS();
		assertFalse("Name zu Kurz",test.testName("abc"));
		assertFalse("Name zu lang",test.testName("abcdefghijklmnopq"));
		assertFalse("Name nicht nur Buchstaben",test.testName("1�4&0?.,*"));
		assertTrue("Name ist Richtig",test.testName("Robin"));
		
		assertFalse("Gr��e zu klein",test.testHeight(1));
		assertFalse("Gr��e zu gro�",test.testHeight(3.5));
	}

}
