package Ex2;
/**
 * class for question 2
 * @author Alexey Titov &   Shalom Weinberger
 * @version 2.0
 */
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.io.FileWriter;
import java.io.IOException;
import Ex1.DataWIFI;
import Ex1.Location;
import Ex1.WIFI;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;

public class HelpFunctions {
	//define
	private final int LLAW=4;
	private final int power=2;
	private final int norm=10000;
	private final double sig_diff=0.4;
	private final double min_dif=3;
	private final int no_signal=-120;
	private final int no_mysignal=0;
	private final int diff_no_sig=100;
	private final int limit=3;
	//variables
	private List<LaLoAlWe> points = new ArrayList<>();
	/**
	 * the function adds data to the list
	 * @param points-location and weight
	 */
	public void addAll(LaLoAlWe... points) { 
		Collections.addAll(this.points, points); 
	}
	/**
	 * the function calculates the weight according to the first algorithm 
	 * @param coa-locations and signals 
	 * @return location by weighted average
	 */
	public Location WriteWeight(ClassOfAlgorithm1 coa)
	{
		Location w_center=new Location();
		WeightOfAlgorithm1 woa=new WeightOfAlgorithm1();
		if (coa.getSignaList().isEmpty())
			return null;
		woa.setWeight(coa.getSignaList());
		double[] sum = new double[LLAW];
		for (int i=0;i<LLAW;i++)
			sum[i]=0;
		double[] w_sum = new double[LLAW-1];
		for (int i=0;i<LLAW-1;i++)
			w_sum[i]=0;
		for (Map.Entry<Double, WeightLLA> entry : woa.getWeight().entrySet())
		{
			//get value
			WeightLLA value = entry.getValue();
			//get key
			double key = entry.getKey();
			sum[0]+=value.getWlat();
			sum[1]+=value.getWlon();
			sum[2]+=value.getWalt();
			sum[3]+=key;
		}
		//calculate LLA
		w_sum[0]=sum[0]/sum[3];
		w_sum[1]=sum[1]/sum[3];
		w_sum[2]=sum[2]/sum[3];
		//set LLA
		w_center.setLat(w_sum[0]);
		w_center.setLon(w_sum[1]);
		w_center.setAlt(w_sum[2]);
		return w_center;
	}
	/**
	 * the function calculates dif
	 * @param Sig_User- signal from user input
	 * @param Sig_csv- signal from csv files
	 * @return dif
	 */
	public double Dif(int Sig_User,int Sig_csv)
	{
		if (Sig_csv==no_signal || Sig_csv==no_mysignal)
			return diff_no_sig;
		return Math.abs(Sig_User-Sig_csv)>min_dif ?  Math.abs(Sig_User-Sig_csv):min_dif;
	}
	/**
	 * the function calculates pi(weight)
	 * @param Signals-list signal from user input
	 * @param coa-array from csv files
	 * @return pi
	 */
	public double weight_pi(ArrayList<Integer> Signals, int[] coa)
	{
		//variables
		int sig1,sig2,sig3;
		double dif1,dif2,dif3,w1,w2,w3,out=-1;
		try {
		//initialization for signals
		sig1=Signals.get(0);
		sig2=Signals.get(1);
		sig3=Signals.get(2);
		//initialization for different
		dif1=Dif(sig1,coa[0]);
		dif2=Dif(sig2,coa[1]);
		dif3=Dif(sig3,coa[2]);
		//initialization for weight
		w1=norm/(Math.pow(dif1,sig_diff)*Math.pow(sig1, power));
		w2=norm/(Math.pow(dif2,sig_diff)*Math.pow(sig2, power));
		w3=norm/(Math.pow(dif3,sig_diff)*Math.pow(sig3, power));
		out=w1*w2*w3;
		}catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null,"not enough data");
		}
		return out;
	}
	/**
	 * Returns the limit rows with the highest weight values.
	 * Implemented using Streams.
	 */
	public List<LaLoAlWe> limitRowsWithHighestWeight() {
		return points
			.stream()
			.sorted(Comparator.comparing(points -> -points.getWeight()))
			.limit(limit)
			.collect(Collectors.toList());
	}
	/**
	 * the function calculates the weight according to the second algorithm 
	 * @param items-locations and weight 
	 * @return location by weighted average
	 */
	public Location WriteWeight2(List<LaLoAlWe> items)
	{
		Location w_center=new Location();
		double[] sum = new double[LLAW];
		for (int i=0;i<LLAW;i++)
			sum[i]=0;
		double[] w_sum = new double[LLAW-1];
		for (int i=0;i<LLAW-1;i++)
			w_sum[i]=0;
		if (items.isEmpty())
			return null;
		for(LaLoAlWe item : items){
			double tmp_weight=item.getWeight();
			sum[3]+=tmp_weight;
			sum[2]+=item.getLLA().getAlt()*tmp_weight;
			sum[0]+=item.getLLA().getLat()*tmp_weight;
			sum[1]+=item.getLLA().getLon()*tmp_weight;
		}
		//calculate LLA
		w_sum[0]=sum[0]/sum[3];
		w_sum[1]=sum[1]/sum[3];
		w_sum[2]=sum[2]/sum[3];
		//set LLA
		w_center.setLat(w_sum[0]);
		w_center.setLon(w_sum[1]);
		w_center.setAlt(w_sum[2]);
		return w_center;
	}
	/**
	 * the function calculates the space according to 3 MAC and gives out the position according to the weight
	 * @param list_coa-data from csv files
	 * @param Signals-signals from user input
	 * @return the position according to the weight
	 */
	public Location WeightAlgo2(ArrayList<ClassOfAlgorithm1> list_coa,ArrayList<Integer> Signals)
	{
		HelpFunctions hlpf = new HelpFunctions();
		try{
			for (ClassOfAlgorithm1 object: list_coa) 
			{
				int[] signals_object=new int[3];
				int i=0;
				double weight;
				for (Map.Entry<Location,Integer> entry : object.getSignaList().entrySet())
				{
					signals_object[i++]=entry.getValue();
				}
				weight=weight_pi(Signals,signals_object);
				if (weight==-1)
					System.exit(2);
				hlpf.addAll(new LaLoAlWe(weight,WriteWeight(object)));
			}
			return WriteWeight2(hlpf.limitRowsWithHighestWeight());
		}catch(NullPointerException e) {
			return null;
		}
	}
	/**
	 * function exchanges the necessary data from the DataWIFI list into the ClassOfAlgorithm1
	 * @param dwf list with data networks
	 * @param MAC_NAME	MAC name that we need
	 * @return list with need data
	 */
	public ClassOfAlgorithm1 Exchange(ArrayList<DataWIFI> dwf,String MAC_NAME)
	{
		ClassOfAlgorithm1 coa=new ClassOfAlgorithm1();			//data of MAC
		try {
			coa.setMAC(MAC_NAME);
			for (int j=0;j<dwf.size();j++)
				coa.setSignaList(dwf.get(j).getWiFi().get(0).getSignal(), dwf.get(j).getLla());
		}catch(NullPointerException e){
			return null;
		}
		return coa;
	}
	/**
	 * function exchanges the necessary data from the String and DataWIFI map into the ClassOfAlgorithm1 list
	 * @param dwf	list with data networks and macs
	 * @return list with need data
	 */
	public ArrayList<ClassOfAlgorithm1> Exchange(Map<String,ArrayList<DataWIFI>> dwf)
	{
		ArrayList<ClassOfAlgorithm1> coa=new ArrayList<ClassOfAlgorithm1>();			//data of MAC
		try{
			for (Entry<String, ArrayList<DataWIFI>> entry : dwf.entrySet())
			{
				//get value
				ArrayList<DataWIFI> value = entry.getValue();
				//get key
				String key = entry.getKey();
				coa.add(Exchange(value,key));
			}
		}catch(NullPointerException e){
			return null;
		}
		return coa;
	}
	/**
	 * the function generates and set a placemark object, with the given statistical data
	 * @param document structure of the KML file
	 * @param folder to add wifi data
	 * @param place location of wifi mark
	 */
	private void createPlacemarkWithChart(Document document, Folder folder,Location place)
	{
		try {
			Placemark placemark = folder.createAndAddPlacemark();
			//name of mark and style
			placemark.withName("!!!HeRe!!!").withStyleUrl("#style_place")
			//description of mark
			.withDescription("Algorithm 1");
			//set coordinates
			placemark.createAndSetPoint().addToCoordinates(place.getLon(),place.getLat(),place.getAlt());
		}catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Why null????");
		}
	}
	/**
	 * the function generates and set a placemark object, with the given statistical data
	 * @param document structure of the KML file
	 * @param folder to add wifi data
	 * @param place location of wifi mark
	 * @param dwf wifi data of wifi mark 
	 */
	private void createPlacemarkWithChart(Document document, Folder folder,Location place,WIFI dwf)
	{
		try {
			Placemark placemark = folder.createAndAddPlacemark();
			//name of mark and style
			placemark.withName(dwf.getSSID()).withStyleUrl("#style_place")
			//description of mark
			.withDescription("MAC: "+dwf.getMAC()+"<br/>Frequency: <b>"+dwf.getFrequency()+"</b><br/>Signal: <b>"+dwf.getSignal()+"</b>");
			//set coordinates
			placemark.createAndSetPoint().addToCoordinates(place.getLon(),place.getLat(),place.getAlt());
		}catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Why null????");
		}
	}
	/**
	 * the function write kml file
	 * @param dwf list of data for kml file
	 */
	public void WriteKML(ArrayList<DataWIFI> dwf,Location center) throws IOException
	{
		//variables
        String OUTkmlFile="";								//output kml file
		final Kml kml = new Kml();
		Document doc = kml.createAndSetDocument().withName("WIFI").withOpen(true);
		//create a Folder
		Folder folder = doc.createAndAddFolder();
		folder.withName("WiFi Signal").withOpen(true);
        //select the location of the file
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.KML","*.*");
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
            try {
            	//user wrote at the end kml
            	if (fc.getSelectedFile().getAbsolutePath().substring(fc.getSelectedFile().getAbsolutePath().length()-4).equals(".kml"))
            		OUTkmlFile=fc.getSelectedFile().getAbsolutePath();
            	else	//user did not wrote at the end kml
            		OUTkmlFile=fc.getSelectedFile().getAbsolutePath()+".kml";
            }
            catch (Exception e ) {
            	JOptionPane.showMessageDialog(null, "an error occurred, the file was not saved.\ngoodbye");
    	    	return;
            }
        }
        else	//user did not select a file to save
        {
        	JOptionPane.showMessageDialog(null, "you did not select a file to save,\ngoodbye");
	    	return;
        }
        //create placemark elements
        try {
        	Icon icon = new Icon()
        	.withHref("http://maps.google.com/mapfiles/ms/icons/green-dot.png");
        	Style style = doc.createAndAddStyle();
        	style.withId("style_place") // set the stylename to use this style from the placemark
        	.createAndSetIconStyle().withScale(1.0).withIcon(icon); // set size and icon
        	style.createAndSetLabelStyle().withColor("ff0ff0ff").withScale(1.0); // set color and size of the wifi name
        	//wifi data
	        for (int j=0;j<dwf.size();j++)
	        {
	        	int counter=dwf.get(j).getWIFINetwork();
	            int i=0;
	            while (counter!=0)
	            {
	            	//create placemark for wifi
	            	try {
	            		createPlacemarkWithChart(doc,folder,dwf.get(j).getLla(),dwf.get(j).getWiFi().get(i));
	            	}catch(IndexOutOfBoundsException e){
	                	System.out.println("Err");
	                }
	                counter--;
	                i++;
	            }
	        }
	        createPlacemarkWithChart(doc,folder,center);
        }catch (Exception e){
    		JOptionPane.showMessageDialog(null, "kml record not saved");
     		return;
    	}
        //print and save
		kml.marshal(new FileWriter(OUTkmlFile,false));
        JOptionPane.showMessageDialog(null, "kml record saved");
	}
	/**
	 * the function translates information from the map into one list
	 * @param dwf map of DataWIFI and MAC names data 
	 * @return list of DataWIFI
	 */
	public ArrayList<DataWIFI> MapToList(Map<String,ArrayList<DataWIFI>> dwf)
	{
		ArrayList<DataWIFI> out=new ArrayList<DataWIFI>();
		try {
			for (Entry<String, ArrayList<DataWIFI>> entry : dwf.entrySet())
			{
				//get value
				ArrayList<DataWIFI> value = entry.getValue();
				out.addAll(value);
			}
			return out;
		}catch(NullPointerException e) {
			return null;
		}
	}
}
