package com.sanskar.game;

import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class Keyboard {
    private Keyboard() {
    }

    public static boolean[] pressed = new boolean[256];
    public static boolean[] prev = new boolean[256];

    public static void kayPressed(KeyEvent e) {
        pressed[e.getKeyCode()] = true;

    }

    public static void kayReleased(KeyEvent e) {
        pressed[e.getKeyCode()] = false;

    }

    public static void update() {
        for (int i = 0; i < 4; i++) {
            if (i == 0) prev[KeyEvent.VK_LEFT] = pressed[KeyEvent.VK_LEFT];
            if (i == 1) prev[KeyEvent.VK_RIGHT] = pressed[KeyEvent.VK_RIGHT];
            if (i == 2) prev[KeyEvent.VK_UP] = pressed[KeyEvent.VK_UP];
            if (i == 3) prev[KeyEvent.VK_DOWN] = pressed[KeyEvent.VK_DOWN];
        }
    }

    public static boolean typed(int keyEvent){
        return !pressed[keyEvent] && prev[keyEvent];
    }
}
