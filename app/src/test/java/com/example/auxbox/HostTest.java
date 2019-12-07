package com.example.auxbox;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HostTest {
    @Test
    public void playingFalse() {
        Host hostTest = new Host();
        assertFalse(hostTest.playing);
    }
}
