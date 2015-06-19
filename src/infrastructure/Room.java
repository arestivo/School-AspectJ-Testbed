package infrastructure;

import java.util.Collection;
import java.util.HashSet;

public class Room {
	private int number;
	
	private static Collection<Room> rooms = new HashSet<Room>();

	protected Room(int number) {
		this.number = number;
	}	

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}
	
	public static void addRoom(Room r) {
		rooms.add(r);
	}

	public static Collection<Room> getRooms() {
		return rooms;
	}
}
