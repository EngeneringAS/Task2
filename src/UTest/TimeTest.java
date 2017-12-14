package UTest;
/**
 * Test class for Time
 * @author Alexey Titov &   Shalom Weinberger
 * ID:     334063021    &   203179403
 * @version 11.2
 */
//libraries
import static org.junit.jupiter.api.Assertions.*;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Ex1.Time;

class TimeTest {
	private Time time;
	@BeforeEach
	void setUp() throws Exception {
		time=new Time();
	}
	@After
	public void tearDown() { 
	        time = null;
	}
	@Test
	final void testSetFt() {
		assertTrue(time.setFt("1970-01-01 01:01:01"));
		assertFalse(time.setFt("1970-25-25 25:25"));			//incorrect number of subsections
		assertTrue(time.setFt("1970-25-25 25:25:66"));			//incorrect time
	}
	@Test
	final void testGetFt() {
		Time tmp=new Time();
		assertEquals(tmp.getFt(),time.getFt());
	}
	@Test
	final void testGetFtD() {
		Time tmp=new Time();
		assertEquals(tmp.getFtD(),time.getFtD());
	}
}
