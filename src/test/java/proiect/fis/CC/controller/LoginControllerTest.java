package proiect.fis.tripALME.controller;

import org.junit.Test;
import static org.junit.Assert.*;


public class LoginControllerTest {


    public static final String TEST_USER = "null";
    public static final String TEST_PASSWORD = "melisa";
    public static final String TEST_ROLE = "manager";
    private LoginController controller;


    @Test
    public void hashPassword() {
        assertNotEquals("testPass1", LoginController.HashPassword("testPass1"));
    }




}