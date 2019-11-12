package Hop.view;

import Hop.interfaces.OnKeyListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class GameView implements RenderEngine {

    private List<OnKeyListener> listeners;

    private JTextField gameState;
    private JPanel panel;
    private JTextField score;
    private JTextField highScore;

    public GameView(OnKeyListener listener) {
        listeners = new ArrayList<>();
        listeners.add(listener);

        // Short hop
        panel.getInputMap().put(KeyStroke.getKeyStroke('f'), "fAction");
        panel.getActionMap().put("fAction", new KeyAction('f'));

        // Long hop
        panel.getInputMap().put(KeyStroke.getKeyStroke('j'), "jAction");
        panel.getActionMap().put("jAction", new KeyAction('j'));

        panel.requestFocusInWindow();
    }

    @Override
    public void render(String string) {
        gameState.setText(string);
    }

    private class KeyAction extends AbstractAction{

        char pressedKey;

        public KeyAction(char pressedKey) {
            this.pressedKey = pressedKey;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            listeners.forEach(listener -> listener.onKeyPressed(pressedKey));
        }
    }

    public JTextField getGameState() {
        return gameState;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextField getScore() {
        return score;
    }

    public JTextField getHighScore() {
        return highScore;
    }
}
