package core;

public class City {

	public City(int IDNumber, int xLocation, int yLocation, Owner owner) {
		this.xLocation = xLocation;
		this.yLocation = yLocation;
		this.IDNumber = IDNumber;
		this.owner = owner;
	}

	// just a git testvfffv

	private int xLocation;
	private int yLocation;
	private int IDNumber;
	private Owner owner;

	public int getXLocation() {
		return xLocation;
	}

	public int getYLocation() {
		return yLocation;
	}

	public int getID() {
		return IDNumber;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner o) {
		owner = o;
	}

	public boolean equals(City city) {
		return this.getID() == city.getID();
	}

}
