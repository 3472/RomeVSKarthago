package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class City_Graph implements Iterable<City> {

	public City_Graph() {

	}

	int amountOfCitys;

	/**
	 * loads the map given by the param
	 * 
	 * 
	 * @param mappath
	 *            String to the mapfile
	 * @return true if loading was sucessfull false if not
	 */
	public boolean loadMapByPath(String mappath) {

		BufferedReader fileReader = null;
		String line = null;

		ArrayList<String> mapdata = new ArrayList<>();

		try {
			fileReader = new BufferedReader(new FileReader(mappath));

			while ((line = fileReader.readLine()) != null) {
				mapdata.add(line);
			}

		} catch (FileNotFoundException e) {
			System.out.println("Failed to read File");
			return false;
		} catch (IOException e) {
			System.out.println("Failed to read File");
			return false;
		}

		return this.initMap(mapdata);

	}

	/**
	 * loads the map given by the param
	 * 
	 * 
	 * @param mapdata
	 * @return true if loading was sucessfull false if not
	 */
	public boolean loadMapByStrings(ArrayList<String> mapdata) {
		return this.initMap(mapdata);
	}

	private ArrayList<Path> pathList;
	private VertexSet cityList;

	private int biggestX;
	private int biggestY;

	private boolean initMap(ArrayList<String> mapdata) {

		pathList = new ArrayList<>();
		cityList = new VertexSet();

		biggestX = 0;
		biggestY = 0;

		String line = null;

		int currentLine = 0;
		line = mapdata.get(currentLine);

		amountOfCitys = Integer.parseInt(line);

		while (mapdata.size() > ++currentLine) {

			line = mapdata.get(currentLine);

			String prefix = line.split(" ")[0];

			if (prefix.equals("V")) {
				// add City

				int x = 0;
				int y = 0;
				int id = 0;
				String owner = "";
				String splitLine[] = new String[4];

				try {
					splitLine = line.split(" ");
					id = Integer.parseInt(splitLine[1]);
					x = Integer.parseInt(splitLine[3]);
					y = Integer.parseInt(splitLine[4]);
					owner = splitLine[2];
				} catch (NullPointerException ex) {
					System.out.println("Too Few Arguments in line: \n" + line);
					return false;
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.out.println("Too Few Arguments in line: \n" + line);
					return false;
				}

				if (!cityList.containsID(id) && id >= 0) {
					Owner o = null;
					switch (owner) {
					case "N":
						o = Owner.Neutral;
						break;
					case "R":
						o = Owner.Rom;
						break;
					case "C":
						o = Owner.Cathargo;
						break;
					default:
						System.out.println("Wrong Owner set int Line: \n"
								+ line);
						return false;
					}

					cityList.addVertex(new City(id, x, y, o));

					if (x > biggestX) {
						biggestX = x;
					}
					if (y > biggestY) {
						biggestY = y;
					}
				} else {
					System.out
							.println("ID already exists or is below 0, at line \n"
									+ line);
					return false;
				}

			} else if (prefix.equals("E")) {
				// add Path

				int firstID = 0;
				int secondID = 0;

				try {
					firstID = Integer.parseInt(line.split(" ")[1]);
					secondID = Integer.parseInt(line.split(" ")[2]);
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.out.println("Too Few Arguments in line: \n" + line);
					return false;
				}

				if (cityList.containsID(firstID)
						&& cityList.containsID(secondID)) {
					if (firstID == secondID) {
						System.out.println("IDs are equal in this line: \n"
								+ line);
						return false;
					}
					pathList.add(new Path(cityList.getCityByID(firstID),
							cityList.getCityByID(secondID)));

				} else {
					System.out.println("Found Unknown ID in this line: \n"
							+ line);
					return false;
				}

			}

		}
		return true;
	}

	/**
	 * 
	 * @return returns the X-Value of the City with the hightest X-Value in the
	 *         Graph
	 */
	public int getBiggestX() {
		return biggestX;
	}

	/**
	 * 
	 * @return the X-Value of the City with the hightest Y-Value in the Graph
	 */
	public int getBiggestY() {
		return biggestY;
	}

	public Iterator<City> iterator() {
		return cityList.iterator();
	}

	public Iterator<Path> getPathIterator() {
		return pathList.iterator();
	}

	/**
	 * 
	 * @return returns how much citys the graph contains
	 */
	public int getAmountOfCitys() {
		return amountOfCitys;
	}

	/**
	 * 
	 * @param id
	 *            id of the wanted city
	 * @return City of the given id
	 */
	public City getCity(int id) {
		return cityList.getCityByID(id);
	}

	/**
	 * 
	 * @param city
	 * @return returns all Citys with are Connected to the given City not
	 *         including the city Itself
	 */
	public VertexSet getNeighbourhood(City city) {

		if (cityList.containsID(city.getID())) {

			VertexSet result = new VertexSet();
			City cityOne;
			City cityTwo;
			for (Path p : pathList) {
				cityOne = p.getFirstCity();
				cityTwo = p.getSecondCity();
				if (cityOne.equals(city)) {
					if (!result.containsID(cityTwo.getID())) {
						result.addVertex(cityTwo);
					}

				} else if (cityTwo.equals(city)) {
					if (!result.containsID(cityOne.getID())) {
						result.addVertex(cityOne);
					}
				}
			}

			return result;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param S
	 * @return returns all Neighbours from all Citys in the Set not including
	 *         the Citys in the Set itself
	 */
	public VertexSet getVertexSetNeighbourhood(VertexSet S) {

		VertexSet vertexSetNeighbourhood = new VertexSet();
		VertexSet vertexSetTemp = null;
		Iterator<City> cityIterator = S.iterator();

		while (cityIterator.hasNext()) {
			vertexSetTemp = getNeighbourhood(cityIterator.next());
			Iterator<City> vertexSetTempIterator = vertexSetTemp.iterator();
			while (vertexSetTempIterator.hasNext()) {
				City nextCity = vertexSetTempIterator.next();
				if (!vertexSetNeighbourhood.containsID(nextCity.getID()))
					vertexSetNeighbourhood.addVertex(nextCity);
			}
		}

		// added by johannes (remove elements from S)
		for (City c : S) {
			if (vertexSetNeighbourhood.containsID(c.getID())) {
				vertexSetNeighbourhood.removeID(c.getID());
			}
		}
		return vertexSetNeighbourhood;
	}

	/**
	 * 
	 * @param city
	 * @return
	 */
	public VertexSet getConnectedComponents(City city) {

		VertexSet set = getNeighbourhood(city);

		if (set == null) {
			return set;
		} else {

			VertexSet result = new VertexSet();

			// aufgerufene Stadt wird hinzugefgt
			result.addVertex(city);

			getFriendlyNeighbours(city, result);

			return result;

		}
	}

	private void getFriendlyNeighbours(City city, VertexSet result) {

		VertexSet set = getNeighbourhood(city);

		for (City c : set) {
			// ist eine Stadt noch nicht im Result Set und gehrt zum
			// gleichen Owner, wird sie hinzugefgt und mit getFriendlyNeighbours
			// aufgerufen
			if (c.getOwner() == city.getOwner()
					&& !result.containsID(c.getID())) {
				result.addVertex(c);
				getFriendlyNeighbours(c, result);
			}
		}
	}

	/**
	 * 
	 * @param owner
	 * @return returns the current Score of the given Owner
	 */
	public int getScore(Owner owner) {
		if (owner == Owner.Neutral)
			return -1;

		int Score = 0;
		City tempCity;
		Iterator<City> CityIterator = iterator();

		while (CityIterator.hasNext()) {
			tempCity = CityIterator.next();
			if (tempCity.getOwner() == owner)
				Score++;
			else if (tempCity.getOwner() == Owner.Neutral) {
				if (isCutOffBy(tempCity, owner))
					Score++;
			}
		}
		return Score;
	}

	private boolean isCutOffBy(City city, Owner owner) {

		VertexSet neighbours = getVertexSetNeighbourhood(getConnectedComponents(city));

		for (City c : neighbours) {
			if (c.getOwner() == ReturnOtherPlayer(owner))
				return false;
		}
		return true;
	}

	private Owner ReturnOtherPlayer(Owner owner) {
		if (owner == owner.Rom)
			return owner.Cathargo;
		if (owner == owner.Cathargo)
			return owner.Rom;
		else
			return owner;
	}

	// maurice
	public String convertGameStateToString() {

		// Hey Maurice
		// habe die Funktion mal kurz implementiert
		// um die Aufgabe 4 zu testen, kannst sie gerne
		// neu schreiben
		String ergebniss = "";
		Iterator<City> it = this.iterator();
		while (it.hasNext()) {
			City c = it.next();
			if (c.getOwner() == Owner.Cathargo) {
				ergebniss += "C";
			} else if (c.getOwner() == Owner.Rom) {
				ergebniss += "R";
			} else {
				ergebniss += "N";
			}

		}
		return ergebniss;
	}

	// johannes
	/**
	 * 
	 * @param move
	 * @return returns the new graph after the move if the move is valid
	 *         otherwise it returns the unchanged graph
	 */
	public City_Graph gameStateTransition(Move move) {

		City_Graph nextGraph = this.copy();

		// stadt gibts nicht
		if (!cityList.containsID(move.getCityID())) {
			return nextGraph;
		}

		// stadt gehrt nicht neutral
		if (cityList.getCityByID(move.getCityID()).getOwner() != Owner.Neutral) {
			return nextGraph;
		}

		nextGraph.changeCityOwner(move.getCityID(),
				PlayerToOwner(move.getPlayer()));

		/*
		 * changes all enemmy's citys to neutral with are ausgehungert
		 */
		VertexSet citysToBeTested = nextGraph.getNeighbourhood(nextGraph
				.getCity(move.getCityID()));
		citysToBeTested.addVertex(nextGraph.getCity(move.getCityID()));

		for (City test : citysToBeTested) {
			if (test.getOwner() == this.ReturnOtherPlayer(this
					.PlayerToOwner(move.getPlayer()))) {

				VertexSet connectedComponents = nextGraph
						.getConnectedComponents(test);
				VertexSet neighbours = nextGraph
						.getVertexSetNeighbourhood(connectedComponents);

				Owner owner = test.getOwner();

				// TODO: check empty
				boolean isDead = true;
				for (City n : neighbours) {
					if (n.getOwner() == Owner.Neutral) {
						isDead = false;
					}
				}

				if (isDead) {
					for (City d : connectedComponents) {
						nextGraph.changeCityOwner(d.getID(), Owner.Neutral);
					}
				}
			}
		}

		/*
		 * Hungert
		 */
		VertexSet connectedCitys = nextGraph.getConnectedComponents(nextGraph
				.getCity(move.getCityID()));
		VertexSet neighbourCitys = nextGraph
				.getVertexSetNeighbourhood(connectedCitys);

		boolean isDead = true;
		for (City neigh : neighbourCitys) {

			if (neigh.getOwner() != this.ReturnOtherPlayer(this
					.PlayerToOwner(move.getPlayer()))) {
				isDead = false;
			}
		}

		if (isDead) {
			for (City dead : connectedCitys) {
				nextGraph.changeCityOwner(dead.getID(), Owner.Neutral);
			}
		}
		return nextGraph;
	}

	/**
	 * removes the old City, adds city with same id but new owner
	 * 
	 * @param cityID
	 *            id of the city
	 * @param newOwner
	 *            newOwner of the city
	 */
	public void changeCityOwner(int cityID, Owner newOwner) {
		City c = cityList.changeCityOwner(cityID, newOwner);
		ArrayList<Path> newPaths = new ArrayList<>();
		Iterator<Path> it = pathList.iterator();
		while (it.hasNext()) {
			Path p = it.next();
			if (p.containsID(cityID)) {
				newPaths.add(p.updateCity(c));
				it.remove();
			}
		}

		for (int i = 0; i < newPaths.size(); i++) {
			pathList.add(newPaths.get(i));
		}
	}

	private Owner PlayerToOwner(Player p) {
		if (p == Player.Cathargo) {
			return Owner.Cathargo;
		}
		return Owner.Rom;
	}

	public City_Graph copy() {
		City_Graph nextCity = new City_Graph();

		ArrayList<Path> pathListCopy = new ArrayList<>();
		for (int i = 0; i < pathList.size(); i++) {
			pathListCopy.add(pathList.get(i));
		}
		nextCity.setContainers(cityList.copy(), pathListCopy);
		return nextCity;
	}

	public void setContainers(VertexSet S, ArrayList<Path> P) {
		this.cityList = S;
		this.pathList = P;
	}

}
