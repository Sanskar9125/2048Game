package com.sanskar.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class Game extends JPanel implements KeyListener, Runnable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 400;
    public static final int HEIGHT = 630;

    public static final Font main = new Font("Babas Neu Regular", Font.PLAIN, 28);

    private Thread game;
    private boolean running;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private GameBoard board;
    private long startTime;
    private long elapsed;
    private boolean set;

    public Game() {
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        //board = new GameBoard(WIDTH / 2 - GameBoard.BOARD_WIDTH / 2, HEIGHT - GameBoard.BOARD_HEIGHT - 10);
    }

    private void update() {
        System.out.println("inside update");
        //board.update();
        Keyboard.update();
    }

    private void render() {
        System.out.println("inside render");
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
       // board.render(g);
        g.dispose();

        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Keyboard.kayPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Keyboard.kayReleased(e);
    }

    @Override
    public void run() {
        int fps = 0;
        int updates = 0;
        long fpsTimer = System.currentTimeMillis();
        double nsPerUpdate = 1000000000.0 / 60;
        //last update time in nanoseconds
        double then = System.nanoTime();
        double unprocessed = 0;
        while (running) {
            boolean shouldRender = false;
            double now = System.nanoTime();
            unprocessed = (now - then) / nsPerUpdate;
            then = now;
            while (unprocessed >= 1) {
                updates++;
                update();
                unprocessed--;
                shouldRender = true;
            }
            if (shouldRender) {
                fps++;
                render();
                shouldRender = false;
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }
        //update queue

        if (System.currentTimeMillis() - fpsTimer > 1000) {
            System.out.printf("%d fps %d updates ", fps, updates);
            System.out.println();
            fps = 0;
            updates = 0;
            fpsTimer += 1000;
        }
    }

    public void start() {
        System.out.println("inside start");
        if (running) return;
        running = true;
        game = new Thread(this, "game");
        game.start();
    }

    public void stop() {
        System.out.println("inside stop");
        if (!running) return;
        running = false;
        System.exit(0);
    }
}
