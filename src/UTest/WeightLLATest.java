package UTest;
/**
 * test for WeightLLA class
 * @author Alexey Titov &   Shalom Weinberger
 * @version 1.0
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Ex2.WeightLLA;

class WeightLLATest {
	//variables
	WeightLLA we=new WeightLLA();
	//check get of latitude
	@Test
	public void testgetWlat() {		
		assertTrue(0==we.getWlat());
	}
	//check get of longitude
	@Test
	public void testgetWlon() {		
		assertTrue(0== we.getWlon());
	}
	//check get of altitude
	public void testgetWalt() {		
		assertTrue(0== we.getWalt());
	}
	//check set of latitude
	@Test
	public void testsetWlat(){
		we.setWlat(2.3);
		assertTrue(2.3 == we.getWlat());
		assertFalse(we.setWlat(-2));
	}
	//check set of longitude
	@Test
	public void testsetWlon(){
		we.setWlon(2.3);
		assertTrue(2.3== we.getWlon());
		assertFalse(we.setWlon(-1));
	}
	//check set of altitude
	@Test
	public void testsetWalt(){
		we.setWalt(2.3);
		assertTrue(2.3== we.getWalt());
		assertFalse(we.setWalt(-3));
	}
}
