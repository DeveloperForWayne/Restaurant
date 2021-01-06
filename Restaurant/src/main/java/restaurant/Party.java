package restaurant;

/**
 * Represents a group of people to be seated together at the restaurant.
 */
public class Party {
	private int size;
	

	private boolean isVIP;

	protected Party(int size, boolean isVIP) {
		this.size=size;
		this.isVIP=isVIP;
	}
	
    public boolean isVIP() {
        // TODO
        return this.isVIP;
    }
    
    public int getSize() {
		return size;
	}
}
