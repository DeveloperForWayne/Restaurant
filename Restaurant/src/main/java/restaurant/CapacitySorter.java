package restaurant;

import java.util.Comparator;

public class CapacitySorter implements Comparator<Table> {
	@Override
	public int compare(Table t1, Table t2) {
		return t1.getCapacity()-t2.getCapacity();
	}
}
