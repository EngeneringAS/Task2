package UTest;
/**
 * test for ALGOtwoCLASS class
 * @author Alexey Titov &   Shalom Weinberger
 * @version 1.0
 */
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Ex1.Location;
import Ex1.WIFI;
import Ex2.ALGOtwoCLASS;

class ALGOtwoCLASSTest {
	//check for GetPi
	@Test
	final void testGetPi() {
		ALGOtwoCLASS tmp=new ALGOtwoCLASS();
		assertEquals(0,tmp.getPi());
		tmp.setPi(22.2);
		assertEquals(22.2,tmp.getPi());
	}
	//check for SetPi
	@Test
	final void testSetPi() {
		ALGOtwoCLASS tmp=new ALGOtwoCLASS();
		tmp.setPi(22.2);
		assertEquals(22.2,tmp.getPi());
		tmp.setPi(0.2);
		assertEquals(0.2,tmp.getPi());
	}
	//check for GetLLA
	@Test
	final void testGetLLA() {
		Location loc=new Location();
		ALGOtwoCLASS tmp=new ALGOtwoCLASS();
		assertEquals(0,loc.compareLLA(tmp.getLLA()));
		loc.setAlt(11);  loc.setLat(-22);  	loc.setLon(0);
		tmp.setLLA(loc);
		assertEquals(0,loc.compareLLA(tmp.getLLA()));
	}
	//check for SetLLA
	@Test
	final void testSetLLA() {
		ALGOtwoCLASS tmp=new ALGOtwoCLASS();
		Location loc=new Location();
		tmp.setLLA(loc);
		assertEquals(0,loc.compareLLA(tmp.getLLA()));
		loc.setAlt(11);  loc.setLat(-22);  	loc.setLon(0);
		tmp.setLLA(loc);
		assertEquals(0,loc.compareLLA(tmp.getLLA()));
	}
	/**
	 * the function creates array list of WIFI
	 * @return array list of WIFI of two variables
	 */
	public ArrayList<WIFI> addWIFI()
	{
		ArrayList<WIFI> wifi=new ArrayList<WIFI>();
		WIFI tmp1=new WIFI();
		WIFI tmp2=new WIFI();
		tmp1.setSignal(-1);						tmp1.setFrequency(1);
		tmp1.setMAC("11:11:11:11:11:11");		tmp1.setSSID("1");
		wifi.add(tmp1);
		tmp2.setSignal(-2);						tmp2.setFrequency(2);
		tmp2.setMAC("22:22:22:22:22:22");		tmp2.setSSID("2");
		wifi.add(tmp2);
		return wifi;
	}
	//check for getWiFi
	@Test
	final void testGetWiFi() {
		ALGOtwoCLASS tmp=new ALGOtwoCLASS();
		ArrayList<WIFI> wifi=new ArrayList<WIFI>();
		wifi=addWIFI();
		assertNotNull(tmp.getWiFi());
		tmp.setWiFi(wifi.get(0));				tmp.setWiFi(wifi.get(1));
		assertEquals(wifi,tmp.getWiFi());
	}
	//check for setWiFi	
	@Test
	final void testSetWiFi() {
		ALGOtwoCLASS tmp=new ALGOtwoCLASS();
		WIFI wf=new WIFI();
		ArrayList<WIFI> wifi=new ArrayList<WIFI>();
		wf.setSignal(-3);						wf.setFrequency(3);
		wf.setMAC("33:33:33:33:33:33");			wf.setSSID("3");
		wifi=addWIFI();
		tmp.setWiFi(wifi.get(0));				tmp.setWiFi(wifi.get(1));
		assertEquals(wifi,tmp.getWiFi());
		wifi.add(wf);							tmp.setWiFi(wf);
		assertEquals(wifi,tmp.getWiFi());
	}
}
