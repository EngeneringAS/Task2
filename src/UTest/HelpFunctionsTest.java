package UTest;
/**
 * test for HelpFunctions class
 * @author Alexey Titov &   Shalom Weinberger
 * @version 2.0
 */
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import Ex1.Location;
import Ex2.ClassOfAlgorithm1;
import Ex2.HelpFunctions;
import Ex2.LaLoAlWe;

class HelpFunctionsTest {
	//check for WriteWeight
	@Test
	final void testWriteWeight() {
		HelpFunctions hf=new HelpFunctions();
		String MAC="10:5a:f7:0f:d5:32";
		Location l1=new Location(32.103,35.208,650);
		Location l2=new Location(32.105,35.205,660);
		Location l3=new Location(32.103,35.307,680);
		Location check=new Location(32.10322468793343,35.2164507628294,653.7864077669904);
		int sig1=-30,sig2=-80,sig3=-90;
		ClassOfAlgorithm1 tmp=new  ClassOfAlgorithm1();
		tmp.setMAC(MAC);
		tmp.setSignaList(sig1,l1);
		tmp.setSignaList(sig2,l2);
		tmp.setSignaList(sig3,l3);
		assertTrue(check.compareLLA(hf.WriteWeight(tmp))==0);
	}
	//check for WriteWeight2
	@Test
	final void testWriteWeight2() {
		HelpFunctions hf=new HelpFunctions();
		Location l1=new Location(32.103,35.208,650);
		Location l2=new Location(32.105,35.205,660);
		Location l3=new Location(32.103,35.307,680);
		double w1=0.476989,w2=0.173813,w3=0.158379;
		Location check=new Location(32.103429602276876,35.22673262224397,658.0198373417072);
		hf.addAll(new LaLoAlWe(w1,l1),new LaLoAlWe(w2,l2),new LaLoAlWe(w3,l3));
		assertTrue(check.compareLLA(hf.WriteWeight2(hf.limitRowsWithHighestWeight()))==0);
	}
	//check for Dif
	@Test
	final void testDif() 
	{
		HelpFunctions hf=new HelpFunctions();
		assertEquals(100,hf.Dif(-50, -120));
		assertEquals(100,hf.Dif(-50, 0));
		assertEquals(3,hf.Dif(-50, -51));
		assertEquals(12,hf.Dif(-50, -62));
	}
	//check for weight_pi
	@Test
	final void testweight_pi() 
	{
		HelpFunctions hf=new HelpFunctions();
		int[] coa= {-62,-79,-71};
		ArrayList<Integer> Signals=new ArrayList<Integer>();
		Signals.add(-50);	Signals.add(-70);	Signals.add(-90);
		assertEquals(0.4769885454073238,hf.weight_pi(Signals, coa));
		coa[0]=-82;		coa[1]=-120;		coa[2]=-82;
		assertEquals(0.17381326045114823,hf.weight_pi(Signals, coa));
		coa[0]=-120;		coa[1]=-120;		coa[2]=-120;
		assertEquals(0.0401216599197276,hf.weight_pi(Signals, coa));
		hf.weight_pi(Signals, null);
		hf.weight_pi(null, coa);
	}
	//check for WeightAlgo2
	@Test
	final void testWeightAlgo2() {
		ArrayList<Integer> Signals=new ArrayList<Integer>();
		ArrayList<ClassOfAlgorithm1> list_coa=new ArrayList<ClassOfAlgorithm1>();
		String MAC1="10:5a:f7:0f:d5:32";	String MAC2="10:5a:f7:0f:d5:31"; 	String MAC3="10:5a:f7:0f:d5:30";
		Location l1=new Location(32.103,35.208,650);
		Location l2=new Location(32.105,35.205,660);
		Location l3=new Location(32.103,35.307,680);
		Location check=new Location(32.10358289257879,35.25424172044318,667.192056624698);
		ClassOfAlgorithm1 tmp1=new  ClassOfAlgorithm1();
		ClassOfAlgorithm1 tmp2=new  ClassOfAlgorithm1();
		ClassOfAlgorithm1 tmp3=new  ClassOfAlgorithm1();
		tmp1.setMAC(MAC1);					tmp2.setMAC(MAC2);					tmp3.setMAC(MAC3);
		tmp1.setSignaList(-62,l1);			tmp2.setSignaList(-82,l1);			tmp3.setSignaList(-120,l1);
		tmp1.setSignaList(-79,l2);			tmp2.setSignaList(-120,l2);			tmp3.setSignaList(-89,l2);
		tmp1.setSignaList(-71,l3);			tmp2.setSignaList(-82,l3);			tmp3.setSignaList(-73,l3);
		Signals.add(-50);		Signals.add(-70);		Signals.add(-90);
		HelpFunctions hf=new HelpFunctions();
		assertNull(hf.WeightAlgo2(null,null));
		assertNull(hf.WeightAlgo2(list_coa,Signals));
		list_coa.add(tmp1);					list_coa.add(tmp2);					list_coa.add(tmp3);
		assertTrue(check.compareLLA(hf.WeightAlgo2(list_coa,Signals))==0);
	}
	//check for two function Exchange
	@Test
	final void testExchange() {
		HelpFunctions hf=new HelpFunctions();
		String str="no";
		assertNull(hf.Exchange(null,null));
		assertNull(hf.Exchange(null,str));
		assertNull(hf.Exchange(null));
	}
	//check for MapToList
	@Test
	final void testMapToList(){
		HelpFunctions hf=new HelpFunctions();
		assertNull(hf.MapToList(null));
	}
}