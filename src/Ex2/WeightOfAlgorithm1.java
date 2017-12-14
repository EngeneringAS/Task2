package Ex2;
/**
 * class for question 2
 * @author Alexey Titov &   Shalom Weinberger
 * @version 2.0
 */
import java.util.HashMap;
import java.util.Map;
import Ex1.Location;

public class WeightOfAlgorithm1 {
	//variables
	private Map<Double,WeightLLA>  weight;
	//constructor
	public WeightOfAlgorithm1() 
	{
		this.weight=new HashMap<>();
	}
	/**
	 * Getting and Setting for weight
	 */
	public Map<Double, WeightLLA> getWeight() 
	{
		return this.weight;
	}
	public void setWeight(Map<Location,Integer>  SignaList) 
	{
		for (Map.Entry<Location, Integer> entry : SignaList.entrySet())
		{
			//get value
			int value = entry.getValue();
			//get key
			Location key = entry.getKey();
			double keyW=1/Math.pow(value, 2);
			WeightLLA wLLA=new WeightLLA();
			wLLA.setWlat(key.getLat()*keyW);
			wLLA.setWlon(key.getLon()*keyW);
			wLLA.setWalt(key.getAlt()*keyW);
			this.weight.put(keyW, wLLA);
		}
	}
}
