package structure;

public class RoomFactory {
	public static Room createRoom(int number) {
		Room r = new Room(number);
		Room.addRoom(r);
		return r;
	}
}
