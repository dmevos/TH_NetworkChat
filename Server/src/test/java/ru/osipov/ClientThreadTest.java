package ru.osipov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ClientThreadTest {
    @Test
    void newTestNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> new ClientThread(null, null, 1));
    }

}
