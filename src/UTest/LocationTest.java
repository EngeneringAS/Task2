package UTest;
/**
 * Test class for location
 * @author Alexey Titov &   Shalom Weinberger
 * @version 11.2
 */
//libraries
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import Ex1.Location;

class LocationTest {
	private Location loc;
	@AfterEach
	void tearDown() throws Exception {
		loc=null;
	}
	@Test
	final void testLocationDoubleDoubleDouble() {
		loc=new Location(1.2,1.2,1.2);
	}
	@Test
	final void testLocation() {
		loc=new Location();
	}
	@Test
	final void testGetLat() {
		loc=new Location(1.2,1.2,1.2);
		assertEquals(1.2,loc.getLat());
	}
	@Test
	final void testSetLat() {
		loc=new Location();
		assertEquals(0,loc.setLat(-91));		//the latitude should be between -90 and 90
		assertEquals(0,loc.setLat(91));			//the latitude should be between -90 and 90
		assertEquals(1,loc.setLat(0));
	}
	@Test
	final void testGetLon() {
		loc=new Location(1.2,1.2,1.2);
		assertEquals(1.2,loc.getLon());
	}
	@Test
	final void testSetLon() {
		loc=new Location();
		assertEquals(0,loc.setLon(-181));			//the longitude should be between -180 and 180
		assertEquals(0,loc.setLon(181));			//the longitude should be between -180 and 180
		assertEquals(1,loc.setLon(0));
	}
	@Test
	final void testGetAlt() {
		loc=new Location(1.2,1.2,1.2);
		assertEquals(1.2,loc.getAlt());
	}
	@Test
	final void testSetAlt() {
		loc=new Location();
		loc.setAlt(-22.22);
		assertEquals(-22.22,loc.getAlt());
		loc.setAlt(22.22);
		assertEquals(22.22,loc.getAlt());
	}
	@Test
	final void testCompareLLA() {
		loc=new Location();
		Location tmp=new Location();
		assertEquals(0,loc.compareLLA(tmp));			//equals
		loc.setLat(22.22);
		assertEquals(1,loc.compareLLA(tmp));			//loc is more than tmp
		tmp.setLat(22.22);
		tmp.setAlt(22.22);
		assertEquals(-1,loc.compareLLA(tmp));			//tmp is more than loc
	}
	@Test
	final void testToString() {
		loc=new Location();
		assertEquals("0.0,0.0,0.0,",loc.toString());
		loc.setLat(11.11);		loc.setLon(-22.22);			loc.setAlt(33.33);
		assertEquals("11.11,-22.22,33.33,",loc.toString());
	}
}
