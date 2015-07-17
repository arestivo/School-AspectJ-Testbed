package infrastructure.tests;

import infrastructure.Room;
import infrastructure.RoomFactory;
import junit.framework.TestCase;

import com.feup.contribution.aida.annotations.PackageName;
import com.feup.contribution.aida.annotations.TestFor;

@PackageName("infrastructure")
@TestFor("infrastructure")
public class RoomTest extends TestCase {
	public void testCreateRoom(){
		assertEquals(0, Room.getRooms().size());
		RoomFactory.createRoom(100);
		assertEquals(1, Room.getRooms().size());
		RoomFactory.createRoom(101);
		assertEquals(2, Room.getRooms().size());
	}
}