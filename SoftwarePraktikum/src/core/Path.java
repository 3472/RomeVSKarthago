package core;


public class Path {

	public Path(City a, City b){
		first_city = a;
		second_city = b;
	}
	
	private City first_city;
	private City second_city;
	
	
	public City getFirstCity(){
		return first_city;
	}
	
	public City getSecondCity(){
		return second_city;
	}
	
	public int getFirstID(){
		return first_city.getID();
	}
	
	public int getSecondID(){
		return second_city.getID();
	}
	
	public boolean containsID(int id){
		return (first_city.getID() == id || second_city.getID() == id);
	}
}
