package restaurant;

public class Table {
    private Party party = null;
    private int capacity;

    protected Table(int capacity) {
        // TODO
    	this.capacity=capacity;
    }

    public Party getParty() {
        return party;
    }

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	
}
