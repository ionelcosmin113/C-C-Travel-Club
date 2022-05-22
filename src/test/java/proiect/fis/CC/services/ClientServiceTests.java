package proiect.fis.CC.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientServiceTests {
    ClientService test;
    @BeforeClass
    public static  void   before() throws Exception {
        UserService.addUser("a","a","a","a","a","a");
    }

    @Before
    public   void   setUp() throws Exception {
        test = new ClientService();
    }
    @After
    public void tearDown() throws Exception {
        test = null;
    }

    @Test
    public void displayHotels1() {

    }

    @Test
    public void writeRequest() {
        test.writeRequest("1","1","1","1","a");
        assertNotNull(ClientService.requests);
    }
}
