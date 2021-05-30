import javax.swing.JFrame;
import java.awt.event.*;
public class MainGame {
    public static void main(String[] args) {
        JFrame window = new JFrame("2048");
        JFrame game = new JFrame("2048"); 
        game.setSize(400,600); 
        game.setLocation(100, 100);
        window.setSize(800, 800);
        window.setLocation(100, 100);
        LoadingScreen load = new LoadingScreen(); 
        Game gameboard = new Game(); 
        game.setContentPane(gameboard); 
        window.setContentPane(load);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        game.setVisible(false);
        load.getStartGameButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                System.out.println("Start Button Clicked"); 
                window.setVisible(false);
                game.setVisible(true);
                startGame(gameboard); 
            }
        });
    }

    protected static void startGame(Game gameboard) {
        gameboard.setFocusable(true); 
        gameboard.requestFocus();
    }
}