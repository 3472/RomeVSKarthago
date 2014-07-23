package core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VertexSet implements Iterable<City> {

	private Map<Integer, City> cityMap;

	public VertexSet() {
		cityMap = new HashMap<Integer, City>();
	}

	public VertexSet(City... cities) {
		for (City c : cities) {
			addVertex(c);
		}
	}

	/**
	 * adds a City to the Container
	 * 
	 * @param city
	 */
	public void addVertex(City city) {
		cityMap.put(city.getID(), city);
	}

	/**
	 * adds all Citys from the VertexSet, witch are not already in the set
	 * 
	 * @param s
	 */
	public void addVertexSet(VertexSet s) {
		Iterator<City> it = s.iterator();
		while (it.hasNext()) {
			City tmp = it.next();
			if (!containsID(tmp.getID())) {
				addVertex(tmp);
			}
		}
	}

	/**
	 * if needed "containsID()" should be checked first, if it's possible that
	 * the id is not inside the container
	 * 
	 * returns null if the Container does not contain the id
	 * 
	 * @param id
	 * @return the City with the given id
	 */
	public City getCityByID(int id) {
		if (containsID(id)) {
			return cityMap.get(id);
		}
		return null;
	}

	/**
	 * removes given id if id is part of Set
	 * 
	 * @param id
	 */
	public void removeID(int id) {
		if (cityMap.containsKey(id)) {
			cityMap.remove(id);
		}
	}

	/**
	 * 
	 * @return Iterator over all CityIDs inside this container
	 */
	public Iterator<Integer> getIDIterator() {
		return cityMap.keySet().iterator();
	}

	public Iterator<City> iterator() {
		return cityMap.values().iterator();
	}

	/**
	 * 
	 * @param id
	 * @return true if the VertexSet Contains the given id, if not: false
	 */
	public boolean containsID(int id) {
		return cityMap.containsKey(id);
	}

	/**
	 * 
	 * @param that
	 *            VertexSet to compare
	 * @return true if the VertexSets contain the same Citys
	 */
	public boolean equals(VertexSet that) {
		return this.cityMap.keySet().equals(that.cityMap.keySet());
	}

	public int hashCode() {
		return this.cityMap.hashCode();
	}

	public VertexSet copy() {
		VertexSet tmp = new VertexSet();
		tmp.addVertexSet(this);
		return tmp;
	}

	/**
	 * 
	 * @param cityID
	 *            ID of the City the Owner got changed
	 * @param newOwner
	 *            New Owner of the City
	 * @return returns the new City(but the City is already added in the
	 *         function)
	 */
	public City changeCityOwner(int cityID, Owner newOwner) {
		int tmpX = cityMap.get(cityID).getXLocation();
		int tmpY = cityMap.get(cityID).getYLocation();
		this.removeID(cityID);
		City c = new City(cityID, tmpX, tmpY, newOwner);
		this.addVertex(c);
		return c;
	}

	public int getSize() {
		return cityMap.size();
	}
}
