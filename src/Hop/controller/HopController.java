package Hop.controller;

import Hop.interfaces.Game;
import Hop.interfaces.OnKeyListener;
import Hop.models.ConcretePlatform;
import Hop.models.Platform;
import Hop.view.GameView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HopController implements Game, OnKeyListener {

    private GameView gameView;
    private List<ConcretePlatform> platforms;
    private final int PLATFORM_MAX_COUNT = 6;


    private int score;
    private int highScore;

    public HopController() {
    }


    @Override
    public void initialize() {

        score = 0;
        highScore = 0;

        // It's up to the controller to create a view.
        initializeGame();
        initializeUI();
    }

    @Override
    public void update() {
    }

    @Override
    public void finish() {

    }

    /**
     * Initializes game variables
     */
    private void initializeGame(){
        initializePlatforms();
    }

    /**
     * Starts the user screen for user to see.
     */
    private void initializeUI(){

        Runnable runnable = new LaunchRunnable(this);
        SwingUtilities.invokeLater(runnable);
    }

    private void handleInput(){
    }

    private void hop(int distance){
        // Calculate score
        if(platforms.get(distance).getPlatformType() != Platform.PLATFORM_NONE){
            score+= distance;
        }

        else{
            score = 0;
        }

        // Replace platforms
        for(int i = 0; i < distance; i++){
            platforms.remove(0);
            addPlatform();
        }
        gameView.getGameState().setText(getStateAsString());
    }

    private void initializePlatforms(){
        platforms = new ArrayList<>();
        for(int i = 0; i < PLATFORM_MAX_COUNT; i++){
            addPlatform();
        }
    }

    private void addPlatform(){

        // If there are no platforms, the first one must be a normal platform
        if(platforms.size() == 0){
            platforms.add(new ConcretePlatform(Platform.PLATFORM_SOLID));
        }

        // If the current platform is none, then the next platform must be a solid, to ensure that the player can jump pass.
        else if(platforms.get(platforms.size() - 1).getPlatformType() == Platform.PLATFORM_NONE){
            platforms.add(new ConcretePlatform(Platform.PLATFORM_SOLID));
        }

        // Add a platform normally.
        else {
            boolean randomValue = new Random().nextBoolean();
            if (randomValue) {
                platforms.add(new ConcretePlatform(Platform.PLATFORM_SOLID));
            } else {
                platforms.add(new ConcretePlatform(Platform.PLATFORM_NONE));
            }
        }
    }

    private String getStateAsString(){
        StringBuilder resultBuilder = new StringBuilder();

        platforms.forEach(platform -> resultBuilder.append(platform.getAsChar()));

        return resultBuilder.toString();
    }

    @Override
    public void onKeyPressed(char keyPressed) {
        if(keyPressed == 'f'){
            hop(1);
        }

        else if(keyPressed == 'j'){
            hop(2);
        }

        gameView.getScore().setText(String.format("Score: %d", score));
        if(score > highScore){
            highScore = score;
            gameView.getHighScore().setText(String.format("High Score: %d", highScore));
        }
    }

    private class LaunchRunnable implements Runnable {

        OnKeyListener listener;

        LaunchRunnable(OnKeyListener listener) {
            this.listener = listener;
        }

        @Override
        public void run() {
            JFrame frame = new JFrame("GameView");
            gameView = new GameView(listener);
            frame.setContentPane(gameView.getPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

            gameView.getGameState().setText(getStateAsString());
        }
    }
}