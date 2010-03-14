package structure.tests;

import junit.framework.TestCase;
import structure.Room;
import structure.RoomFactory;

import com.feup.contribution.aida.annotations.TestFor;

@TestFor("structure")
public class RoomTest extends TestCase {
	public void testRoomList(){
		assertEquals(0, Room.getRooms().size());
		RoomFactory.createRoom(100);
		assertEquals(1, Room.getRooms().size());
		RoomFactory.createRoom(101);
		assertEquals(2, Room.getRooms().size());
	}
}