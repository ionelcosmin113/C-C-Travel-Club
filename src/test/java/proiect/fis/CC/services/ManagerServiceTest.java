package proiect.fis.tripALME.services;

import org.junit.Test;

import static org.junit.Assert.*;

public class ManagerServiceTest {

    @Test
    public void loadRoomsFromFile() {
        ManagerService.loadRoomsFromFile();
        assertNotNull( ManagerService.room);
        assertEquals(0,  ManagerService.room.size());
    }

    @Test
    public void testAddOneRoom() {
        ManagerService.loadRoomsFromFile();
        ManagerService.addRooms("2","100","abc","Single","abcd","ana");
        assertNotNull(ManagerService.room);
        assertEquals(1, ManagerService.room.size());
    }
}