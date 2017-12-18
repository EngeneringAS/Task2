package UTest;
/**
 * test for ALGOtwoCLASS class
 * @author Alexey Titov &   Shalom Weinberger
 * @version 1.0
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Ex1.Location;
import Ex2.ALGOoneCLASS;

class ALGOoneCLASSTest {
	//check get MAC
	@Test
	final void testGetDATAmac() {
		Location l1=new Location(32.103,35.208,650);
		Location l2=new Location(32.105,35.205,660);
		String MAC1="10:5a:f7:0f:d5:32";	String MAC2="10:5a:f7:0f:d5:31"; 
		ALGOoneCLASS tmp=new ALGOoneCLASS();
		assertTrue(tmp.getDATAmac().isEmpty());
		tmp.setDATAmac(MAC1, l1, -1);					tmp.setDATAmac(MAC2, l2, -2);
		assertEquals(2,tmp.getDATAmac().size());
	}
	//check set MAC
	@Test
	final void testSetDATAmac() {
		Location l1=new Location(32.103,35.208,650);
		Location l2=new Location(32.105,35.205,660);
		String MAC1="10:5a:f7:0f:d5:32";	String MAC2="10:5a:f7:0f:d5:31"; 
		ALGOoneCLASS tmp=new ALGOoneCLASS();
		assertTrue(tmp.getDATAmac().isEmpty());
		tmp.setDATAmac(MAC1, l1, -1);					tmp.setDATAmac(MAC2, l2, -2);
		tmp.setDATAmac(null, null, 0);
		assertEquals(3,tmp.getDATAmac().size());
	}

}
