package Ex2;
/**
 * class for question 2
 * @author Alexey Titov &   Shalom Weinberger
 * @version 2.0
 */
//libraries
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import com.csvreader.CsvReader;
import Ex1.DataWIFI;
import Ex1.Location;
import Ex1.WIFI;
public class ALGORITHMtwo extends JFrame 
{
	//variables
	private JFormattedTextField MAC1;												//text field of user MAC1
	private JFormattedTextField MAC2;												//text field of user MAC2
	private JFormattedTextField MAC3;												//text field of user MAC3
	private JFormattedTextField Signal1;											//text field of user Signal1
	private JFormattedTextField Signal2;											//text field of user Signal2
	private JFormattedTextField Signal3;											//text field of user Signal3
	//define
	private static final long serialVersionUID = 1L;
    private final  String  BUTTON_NAME = "OK";
    private final int limit=3;
    //constructor
    public ALGORITHMtwo()
    {
        super("Entery MACs and Signal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        NumberFormat number = new DecimalFormat("000");
        //container panel
        Container container = getContentPane();
        //install the sequential location manager.
        container.setLayout(new FlowLayout());
        //create a formatted MAC and Signal text fields
        //-------------------------------------------------------1------------------------------------------------
        MAC1 = new JFormattedTextField("MAC 1");
		MAC1.setColumns(20);
		Signal1 = new JFormattedTextField(new NumberFormatter(number));
		Signal1.setText("Signal");
		Signal1.setColumns(5);
		container.add(MAC1);
		container.add(Signal1);
		//-------------------------------------------------------2------------------------------------------------
        MAC2 = new JFormattedTextField("MAC 2");
		MAC2.setColumns(20);
		Signal2 = new JFormattedTextField(new NumberFormatter(number));
		Signal2.setText("Signal");
		Signal2.setColumns(5);
		container.add(MAC2);
		container.add(Signal2);
		//-------------------------------------------------------3------------------------------------------------
        MAC3 = new JFormattedTextField("MAC 3");
		MAC3.setColumns(20);
		Signal3 = new JFormattedTextField(new NumberFormatter(number));
		Signal3.setText("Signal");
		Signal3.setColumns(5);
		container.add(MAC3);
		container.add(Signal3);
        //create button
        Action action = new SimpleAction();
        JButton button = new JButton(action);
        button.setName(BUTTON_NAME);
        button.setText("OK");
        button.setMnemonic('F');
        container.add(button);
        //display the window on the screen
      	setContentPane(container);
        setSize(350, 150);						//size of window
        setVisible(true);						//make the panel visible
        setResizable(false);					//disallow resizing to panel
    }
    /**
     * the function read csv files
     * @param csvFiles list of names cvs files
     * @param MACs the list of MAC names whose data we need
     * @return up to limit data that we need
     */
	private Map<String,ArrayList<DataWIFI>> ReadCSV(ArrayList<String> csvFiles,ArrayList<String> MACs)
	{
		//variables
		Map<String,ArrayList<DataWIFI>> MapDataWIFI=new HashMap<>();
		ArrayList<DataWIFI> dwf=new ArrayList<DataWIFI>();			//data of WiFiNetwork
		String IDphone="";											//id of cellular phone
		for(int i=0;i<MACs.size();i++)
			MapDataWIFI.put(MACs.get(i), dwf);
		CsvReader row =null;
		//read file csv and filtering data
		for (int i=0;i<csvFiles.size();i++)
		{
			try {
				row=new CsvReader(new FileReader(csvFiles.get(i)));
				//save ID of phone in to IDphone
				if (row.readRecord())
				{
					if (row.getColumnCount()==11 || row.getColumnCount()==8)  //if were is less or more then 8 it's not our format
					{
						try {
							IDphone=row.get(5).substring(8); 
						}catch(StringIndexOutOfBoundsException e) {
							JOptionPane.showMessageDialog(null, csvFiles.get(i)+"\nID is incorrect.");
							row.close();
							continue;
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null,csvFiles.get(i)+"\nFirst row is incorrect.");
						row.close();
						continue;
					}
				}
				//read line with names of columns
				if (!row.readRecord() || row.getColumnCount()!=11)
				{
					JOptionPane.showMessageDialog(null, csvFiles.get(i)+"\nRow with column names is incorrect.");
					row.close();
					continue;
		       	}	
				//read data from columns
				while (row.readRecord())
				{
					if (row.getColumnCount()==11 && row.get(10).equals("WIFI")) //check that 11 columns are in the line and type is wi-fi network
					{
						DataWIFI tmpWIFI=new DataWIFI();
						WIFI wf=new WIFI();
						int cnt=0;					//variables for check MAC,Signal
						int cnt2=0;					//variables for check latitude,longitude
						boolean flagtime;			//flag for check time
						tmpWIFI.setID(IDphone);
						//location
						Location place=new Location();
						try{
							place.setAlt(Double.parseDouble(row.get(8)));
							cnt2+=place.setLat(Double.parseDouble(row.get(6)));
							cnt2+=place.setLon(Double.parseDouble(row.get(7)));
						}catch(NumberFormatException e){
							continue;
						}
						//WIFI data
						try {
							cnt+=wf.setFrequency(Integer.parseInt(row.get(4)));
							cnt+=wf.setMAC(row.get(0));
							wf.setSSID(row.get(1));
							cnt+=wf.setSignal(Integer.parseInt(row.get(5)));
						}catch(NumberFormatException e){
							continue;
						}	
						tmpWIFI.setWiFi(wf);
						tmpWIFI.setLla(place);
						flagtime=tmpWIFI.setTIME(row.get(3));
						tmpWIFI.setWIFINetwork(1);
						//check if MAC,Signal,time and location are correct
						if (cnt!=3 || cnt2!=2 || !flagtime)   
							continue;
						if (MapDataWIFI.containsKey(wf.getMAC()))
						{	
							dwf=MapDataWIFI.get(wf.getMAC());
							//first wifi
							if (dwf.size()==0)
								dwf.add(tmpWIFI);
							else
							{
								int j;
								for (j=0;j<dwf.size();j++)								//location is equals
									if (dwf.get(j).getLla().compareLLA(tmpWIFI.getLla())==0)
									{
										if (dwf.get(j).getWiFi().get(0).getSignal()<tmpWIFI.getWiFi().get(0).getSignal())
										{
											dwf.get(j).getWiFi().get(0).setSignal(tmpWIFI.getWiFi().get(0).getSignal());
											dwf.get(j).setID(tmpWIFI.getID());
											dwf.get(j).setTIME(tmpWIFI.getTIME());
										}
										break;
									}
									//location is not equals
									if (j==dwf.size())
									{	
										for (j=0;j<dwf.size();j++)								//signal is better
											if (dwf.get(j).getWiFi().get(0).getSignal()<tmpWIFI.getWiFi().get(0).getSignal())
											{
												dwf.add(j, tmpWIFI);
												break;
											}
									}
									//up to limit
									if (limit<dwf.size())
										dwf.remove(limit);
									else
										if (limit>dwf.size())
											dwf.add(tmpWIFI);
							}
							MapDataWIFI.replace(wf.getMAC(), dwf);
						}
					}
				}
				row.close();
				}catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, csvFiles.get(i)+"\nFile not found.");
					continue;
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, csvFiles.get(i)+"\nInput or Output are incorrect.");
					continue;
				}
			}
		return MapDataWIFI;
	}
    /**
	 * the function processes the data of csv file 
	 */
    private void Program(ArrayList<String> MACs,ArrayList<Integer> Signals)
    {
    	//variables
    	ArrayList<String> files=new ArrayList<String>();								//list csv files
    	Map<String,ArrayList<DataWIFI>> MapDataWIFI=new HashMap<>();								//data of WiFiNetwork
    	ArrayList<ClassOfAlgorithm1> coaList=new ArrayList<ClassOfAlgorithm1>();		//data of MAC
    	HelpFunctions hlp=new HelpFunctions();
    	Location center=new Location();
    	int ret;
		//File chooser
		JFileChooser fileopen = new JFileChooser();
		//only directory
		fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileopen.setAcceptAllFileFilterUsed(false);
		//dialog box for determining the desired directory
		ret=fileopen.showDialog(null, "Open directory");
		//check if directory is not selected
		if (!(ret == JFileChooser.APPROVE_OPTION))
		{
			JOptionPane.showMessageDialog(null, "Directory is not selected\ngoodbye");
		    System.exit(2);
		}	
		File []fList;        
		File F = new File(fileopen.getSelectedFile().getAbsolutePath());  //the path to the directory
		//all files that are in the folder     
		fList = F.listFiles();
		//runs at the folder.
		for(int i=0; i<fList.length; i++)           
		{
			String mark=fList[i].getName();  
			//check if name is csv file
			if(fList[i].isFile() && mark.substring(mark.length()-3).equals("csv"))
				files.add(fileopen.getSelectedFile().getAbsolutePath()+"\\"+fList[i].getName());
		}
		//no files
		if (files.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "in the folder there are no csv files");
		    System.exit(2);
		}
		else
			MapDataWIFI=ReadCSV(files,MACs);
		try {
			coaList=hlp.Exchange(MapDataWIFI);
			center=hlp.WeightAlgo2(coaList,Signals);
			System.out.println(center.toString());
			hlp.WriteKML(hlp.MapToList(MapDataWIFI),center);
		}catch(NullPointerException e){
			System.out.println("NULL :(");
		} catch (IOException e) {
			System.out.println("NULL :(");
		}
    } 
    /**
     * internal class
     */
    class SimpleAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        SimpleAction() {
        	//command parameters
            putValue(SHORT_DESCRIPTION, "If you want so, then click");
        }
        /**
         * the function check if field is no null
         */
        private boolean CheckMACandSignal()
        {
        	try {
        	if (MAC1.getValue().equals("") || MAC2.getValue().equals("") || MAC3.getValue().equals(""))
        	{
        		JOptionPane.showMessageDialog(null, "MAC field is null");
        		return false;
        	}
        	if (Signal1.getValue().equals("") || Signal1.getValue().equals("") || Signal1.getValue().equals(""))
        	{
        		JOptionPane.showMessageDialog(null, "Signal field is null");
        		return false;
        	}
        	return true;
        	}catch(NullPointerException e) {
        		JOptionPane.showMessageDialog(null, "Signal field is null");
        		return false;
        	}
        }
        /**
         * the function check if values are correct
         */
        private boolean CheckValueMACandSignal()
        {
        	WIFI tmp=new WIFI();
        	int a,b,c;
        	a=Integer.parseInt(Signal1.getValue().toString());
        	b=Integer.parseInt(Signal2.getValue().toString());
        	c=Integer.parseInt(Signal3.getValue().toString());
        	if(0==tmp.setMAC(MAC1.getValue().toString()) || 0==tmp.setMAC(MAC2.getValue().toString()) || 0==tmp.setMAC(MAC3.getValue().toString()))
        		return false;
        	if (a>-1 || a<-120 || b>-1 || b<-120 || c>-1 || c<-120)
        		return false;
        	return true;
        }
        /**
         * processing the button click event
         */
        public void actionPerformed(ActionEvent e) 
        {
            JButton btn = (JButton) e.getSource();
            //user click OK
            if (btn.getName().equalsIgnoreCase(BUTTON_NAME)) 
            {
            	//check if text field is null
            	if (!CheckMACandSignal() || !CheckValueMACandSignal())
            		return;
            	setVisible(false);
            	//transference date from text field to string
            	//---------------------------------------------------MAC
            	ArrayList<String> MACs=new ArrayList<String>();
            	MACs.add(MAC1.getValue().toString());
            	MACs.add(MAC2.getValue().toString());
            	MACs.add(MAC3.getValue().toString());
            	//--------------------------------------------------Signals
            	ArrayList<Integer> Signals=new ArrayList<Integer>();
            	Signals.add(Integer.parseInt(Signal1.getValue().toString()));
            	Signals.add(Integer.parseInt(Signal2.getValue().toString()));
            	Signals.add(Integer.parseInt(Signal3.getValue().toString()));
            	Program(MACs,Signals);
                System.exit(0);
            }
        }
    };
    public static void main(String[] args) {
        new  ALGORITHMtwo();
    }
}