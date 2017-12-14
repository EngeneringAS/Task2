package UTest;
/**
 * test for LaLoWe class
 * @author Alexey Titov &   Shalom Weinberger
 * @version 1.0
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Ex1.Location;
import Ex2.LaLoAlWe;

class LaLoAlWeTest {
		//check get of weight
		@Test
		public void testgetWeight() {
			Location loc=new Location();
			double weight=12;
			LaLoAlWe llaw=new LaLoAlWe(weight,loc);
			assertTrue(weight==llaw.getWeight());
		}
		//check get of location
		@Test
		public void testgetLLA() {		
			Location loc=new Location();
			double weight=12;
			LaLoAlWe llaw=new LaLoAlWe(weight,loc);
			assertTrue(loc.compareLLA(llaw.getLLA())==0);
		}
}
