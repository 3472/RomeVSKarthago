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
	
	/**
	 * 
	 * @param c the new City with overrides the
	 * 		City in this Path with the !same! id 
	 * 
	 * 		containsID() should be called first
	 * 		if the path does not contain the cityID, nothing happens
	 */
	public void updateCity(City c){
		if(first_city.getID() == c.getID()){
			first_city = c;
		}else if(second_city.getID() == c.getID()){
			second_city = c;
		}
	}
}
