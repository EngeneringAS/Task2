package UTest;
/**
 * test for ClassOfAlgorithm1 class
 * @author Alexey Titov &   Shalom Weinberger
 * @version 1.0
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Ex1.Location;
import Ex1.WIFI;
import Ex2.ClassOfAlgorithm1;

class ClassOfAlgorithm1Test {
	//check get MAC
	@Test
	final void testGetMAC() {
		WIFI wifi=new WIFI();
		assertEquals("N\\A", wifi.getMAC());
		wifi.setMAC("ec:8c:a2:26:ae:28");
		assertSame("ec:8c:a2:26:ae:28", wifi.getMAC());
	}
	//check set MAC
	@Test
	final void testSetMAC() {
		WIFI wifi=new WIFI();
		assertEquals("N\\A", wifi.getMAC());
		wifi.setMAC("ec:8c:a2:26:ae:28");
		assertSame("ec:8c:a2:26:ae:28", wifi.getMAC());
	}
	//check set SignaList
	@Test
	final void testSetSignaList() {
		ClassOfAlgorithm1 tmp=new ClassOfAlgorithm1();
		assertTrue(tmp.setSignaList(-10, new Location(1,2,3)));
		assertFalse(tmp.setSignaList(-10, new Location(1,2,3)));
		assertTrue(tmp.setSignaList(-10, new Location(4,5,6)));
		assertTrue(tmp.setSignaList(-10, new Location(7,8,9)));
		assertTrue(3==tmp.getSignaList().size());
	}
	//check get SignaList
	@Test
	final void testGetSignaList() {
		ClassOfAlgorithm1 tmp=new ClassOfAlgorithm1();
		tmp.setSignaList(-10, new Location(1,2,3));
		tmp.setSignaList(-10, new Location(4,5,6));
		assertTrue(2==tmp.getSignaList().size());
	}
}
