package restaurant;

import restaurant.exceptions.EmptyTableException;
import restaurant.exceptions.MissingPartyException;
import restaurant.exceptions.MissingTableException;
import restaurant.exceptions.NoTablesAvailableException;
import restaurant.exceptions.NonPositiveArgumentException;
import restaurant.exceptions.OccupiedTableException;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

	private ArrayList<Table> tables = new ArrayList<Table>();
	private ArrayList<Party> parties = new ArrayList<Party>();

	/**
	 * Creates a table with the given capacity and adds it to the restaurant. If the
	 * capacity is less than or equal to zero, this method throws an instance of
	 * {@link NonPositiveArgumentException} with the capacity filled in.
	 * 
	 * @param capacity The capacity of the table.
	 * @return A reference to the new table.
	 * @throws NonPositiveArgumentException
	 */
	public Table addTable(final int capacity) throws NonPositiveArgumentException {

		if (capacity <= 0) {
			throw new NonPositiveArgumentException(capacity);
		}

		Table table = new Table(capacity);
		tables.add(table);
		tables.sort(new CapacitySorter());
		return table;

	}

	/**
	 * Removes the specified table from the restaurant. If the table is occupied,
	 * this method throws an instance of {@link OccupiedTableException} with the
	 * specified table and occupying party filled in. If the table is null or does
	 * not exist, this method throws an instance of {@link MissingTableException}
	 * with the specified table filled in.
	 * 
	 * @param table The table to remove from the restaurant.
	 * @throws OccupiedTableException
	 */
	public void removeTable(final Table table) throws OccupiedTableException, MissingTableException {

		if (table == null || !tables.contains(table)) {
			throw new MissingTableException(table);
		}

		if (table.getParty() != null) {
			throw new OccupiedTableException(table, table.getParty());
		}

		tables.remove(table);
	}

	/**
	 * Creates a party with the given size and VIP status and adds it to the queue
	 * of parties waiting for a table. If the size is less than or equal to zero,
	 * this method throws an instance of {@link NonPositiveArgumentException} with
	 * the size filled in.
	 * 
	 * @param size  The size of the party.
	 * @param isVIP Whether the party is a VIP.
	 * @return A reference to the new party.
	 * @throws NonPositiveArgumentException
	 */
	public Party bookParty(final int size, final boolean isVIP) throws NonPositiveArgumentException {

		if (size <= 0) {
			throw new NonPositiveArgumentException(size);
		}

		Party p = new Party(size, isVIP);
		parties.add(p);

		return p;
	}

	/**
	 * Removes the specified party from the restaurant. If the party is already
	 * seated, this method should empty the table that the party is currently seated
	 * at. If the party does not exist, this method throws an instance of
	 * {@link MissingPartyException} with the specified party filled in.
	 * 
	 * @param party The party to remove from the restaurant.
	 */
	public void removeParty(final Party party) throws MissingPartyException {

		if (parties.contains(party)) {
			if (!this.getUnseatedParties().contains(party)) {
				for (Table tbl : this.tables) {
					if (tbl.getParty() == party) {
						tbl.setParty(null);
						break;
					}
				}
			}
			parties.remove(party);
		} else {
			throw new MissingPartyException(party);
		}
	}

	/**
	 * Seats the next eligible party to an empty table. Priority should first be
	 * given to the earliest unseated VIP party. If no VIP party can be seated, then
	 * priority should be given to the earliest unseated non-VIP party. The table
	 * chosen should be any table with the smallest capacity that can seat the
	 * party. If there is no table with a capacity large enough to seat the party,
	 * the next party should be checked. If there is no table available that can
	 * seat any party in the queue, this method throws an instance of
	 * {@link NoTablesAvailableException} with the first party in the queue filled
	 * in. If no parties are currently waiting, this method does nothing.
	 * 
	 * @throws NoTablesAvailableException
	 */
	public void seatParty() throws NoTablesAvailableException {

		ArrayList<Table> ts = new ArrayList<Table>();
		ts = (ArrayList<Table>) this.getEmptyTables();
		if (ts.size() == 0) {
			throw new NoTablesAvailableException(parties.get(0));
		}
		ArrayList<Party> ps = new ArrayList<Party>();
		ps = (ArrayList<Party>) this.getUnseatedParties();
		if (ps.size() == 0) {
			return;
		}
		// Seat VIP Party
		for (Party p : ps) {
			if (p.isVIP()) {
				for (Table t : ts) {
					if (p.getSize() < t.getCapacity()) {
						t.setParty(p);
						return;
					}
				}
			}
		}
		// Seat Non-VIP Party
		for (Party p : ps) {
			if (!p.isVIP()) {
				for (Table t : ts) {
					if (p.getSize() <= t.getCapacity()) {
						t.setParty(p);
						return;
					}
				}
			}
		}
		throw new NoTablesAvailableException(ps.get(0));
	}

	/**
	 * Removes a party from an occupied table. If the table is not currently
	 * occupied, this method throws an instance of {@link EmptyTableException} with
	 * the specified table filled in.
	 * 
	 * @param table The table to empty.
	 * @throws EmptyTableException
	 */
	public Party emptyTable(final Table table) throws EmptyTableException {

		Party p = table.getParty();
		if (p == null) {
			throw new EmptyTableException(table);
		} else {
			table.setParty(null);
		}
		return p;
	}

	/**
	 * @return The list of tables currently occupied by a party.
	 */
	public List<Table> getFilledTables() {

		ArrayList<Table> ts = new ArrayList<Table>();
		for (Table t: tables) {
			if (t.getParty() != null) {
				ts.add(t);
			}
		}
		return ts;
	}

	/**
	 * @return The list of tables not occupied by a party.
	 */
	public List<Table> getEmptyTables() {

		ArrayList<Table> ts = new ArrayList<Table>();
		for (Table t: tables) {
			if (t.getParty() == null) {
				ts.add(t);
			}
		}
		return ts;
	}

	/**
	 * @return The list of parties waiting for a table, with the earliest booked
	 *         parties at the beginning of the list.
	 */
	public List<Party> getUnseatedParties() {

		ArrayList<Party> pts = new ArrayList<Party>();
		pts = this.parties;
		for (Table t : tables) {
			if (pts.contains(t.getParty())) {
				pts.remove(t.getParty());
			}
		}

		return pts;
	}
}
