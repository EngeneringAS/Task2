package UTest;
/**
 * Test class for TestCSV
 * @author Alexey Titov &   Shalom Weinberger
 * @version 11.2
 */
//libraries
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import Ex1.DataWIFI;
import Ex1.TestCSV;

class TestCSVTest {
	@Test
	final void testReadCSV() {
		//variables
		ArrayList<DataWIFI> testDataWIFI=new ArrayList<DataWIFI>();
		ArrayList<String> testFiles=new ArrayList<String>();
		new TestCSV();
		//testFiles list is empty
		testDataWIFI=TestCSV.ReadCSV(testFiles);
		assertTrue(testDataWIFI.isEmpty());
		//incorrect files from folder " Incorrect"
		//you must change the patch "C:\\Users\\PlasticMan\\Desktop\\Incorrect\\" on your computer
		testFiles.add("C:\\Users\\PlasticMan\\Desktop\\Incorrect\\1.csv");
		testFiles.add("C:\\Users\\PlasticMan\\Desktop\\Incorrect\\2.txt");
		testFiles.add("C:\\Users\\PlasticMan\\Desktop\\Incorrect\\3.txt");
		testFiles.add("C:\\Users\\PlasticMan\\Desktop\\Incorrect\\4.csv");
		testDataWIFI=TestCSV.ReadCSV(testFiles);
		assertTrue(testDataWIFI.isEmpty());
		testFiles.remove("C:\\Users\\PlasticMan\\Desktop\\Incorrect\\1.csv");
		testFiles.remove("C:\\Users\\PlasticMan\\Desktop\\Incorrect\\2.txt");
		testFiles.remove("C:\\Users\\PlasticMan\\Desktop\\Incorrect\\3.txt");
		testFiles.remove("C:\\Users\\PlasticMan\\Desktop\\Incorrect\\4.csv");
		//file does not exist
		testFiles.add("C:\\Users\\PlasticMan\\Desktop\\Incorrect\\nofile.csv");
		testDataWIFI=TestCSV.ReadCSV(testFiles);
		assertTrue(testDataWIFI.isEmpty());
	}
	@Test
	final void testWriteCSV() {
		//variables
		ArrayList<DataWIFI> testDataWIFI=new ArrayList<DataWIFI>();
		DataWIFI tmp1=new DataWIFI();
		DataWIFI tmp2=new DataWIFI();
		new TestCSV();
		//testDataWIFI list is empty
		TestCSV.WriteCSV(null);
		TestCSV.WriteCSV(testDataWIFI);
		//data is incorrect
		tmp1.setLla(null);
		testDataWIFI.add(tmp1);
		TestCSV.WriteCSV(testDataWIFI);
		//only once row is incorrect
		testDataWIFI.add(0,tmp2);
		TestCSV.WriteCSV(testDataWIFI);
	}

}
