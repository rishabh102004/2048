import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.GridLayout;
public class LoadingScreen extends JPanel{
    private JButton startGame; 
    public LoadingScreen() {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(400,100));
        JLabel title = new JLabel("2048"); 
        Font title_font = new Font("Verdana", Font.BOLD, 150); 
        title.setFont(title_font);
        title.setForeground(Color.BLACK);
        /*
        GridLayout grid = new GridLayout(9,1); 
        this.setLayout(grid);
        */
        this.add(title); 
        Dimension instruction_size = new Dimension(100,100); 
        JLabel instructionO = new JLabel("This is a game of 2048! Tiles with the same number merge into one when they touch."); 
        instructionO.setSize(instruction_size);
        JLabel instructionN = new JLabel("Add them up to reach 2048! You have to do this before you run out of availible spaces!"); 
        instructionN.setSize(instruction_size);
        JLabel instructionA = new JLabel("To go up, press the top-facing arrow key."); 
        instructionA.setSize(instruction_size); 
        JLabel instructionB = new JLabel("To go down, press the down-facing arrow key."); 
        instructionB.setSize(instruction_size);
        JLabel instructionC = new JLabel("To go left, press the west-facing arrow key."); 
        instructionC.setSize(instruction_size);
        JLabel instructionD = new JLabel("To go right, press the east-facing arrow key. ");
        instructionD.setSize(instruction_size); 
        startGame = new JButton("Start Game/New Game"); 
        this.add(instructionO); 
        this.add(instructionN); 
        this.add(instructionA); 
        this.add(instructionB); 
        this.add(instructionC); 
        this.add(instructionD); 
        this.add(startGame); 
    }
    public JButton getStartGameButton() {
        return this.startGame; 
    }

}