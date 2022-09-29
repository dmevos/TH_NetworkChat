package ru.osipov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class MessageTest {
    @Test
    void testToString(){
        Date date = new Date();
        String name = "name";
        String text = "text";
        Message actualMsg = new Message(name, text, date);
        String expected = "["+date+"]{"+name+"} "+text;
        Assertions.assertEquals(expected, actualMsg.toString());
    }
}
