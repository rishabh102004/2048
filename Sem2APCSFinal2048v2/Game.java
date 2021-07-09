import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.LayoutManager;
import java.awt.event.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.*;
public class Game extends JPanel implements KeyListener{
    private int score;
    private Tile[][] tiles = new Tile[4][4];
    private JPanel[][] components = new JPanel[4][4]; 
    private JPanel gridContainer;
    public Game() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.tiles[i][j] = new Tile(); 
            }
        }
        generateNewTile();
        GridLayout grid = new GridLayout(4,4); 
        this.setLayout(grid);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.components[i][j] = new JPanel(); 
                Border blackline = BorderFactory.createLineBorder(Color.BLACK); 
                LayoutManager layout = new FlowLayout(); 
                this.components[i][j].setLayout(layout); 
                this.components[i][j].setBorder(blackline);
                this.components[i][j].setBackground(this.tiles[i][j].getColor());
                int value = this.tiles[i][j].getNumber(); 
                String val = Integer.toString(value); 
                JLabel lab = new JLabel(val);
                Font setFont = new Font("Verdana", Font.BOLD, 60);
                lab.setFont(setFont);
                this.components[i][j].add(lab); 
                this.add(this.components[i][j]);
            }
        } 
        this.addKeyListener(this);
        System.out.println("Reached end of Main"); 
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("Entered the Paint Component"); 
        GridLayout grid = new GridLayout(4,4); 
        this.setLayout(grid);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JPanel tile = this.getTile(i, j); 
                JLabel label = (JLabel)tile.getComponent(0); 
                String num = "" + this.tiles[i][j].getNumber(); 
                label.setText(num);
                tile.setBackground(this.tiles[i][j].setColor(this.tiles[i][j].getNumber()));
            }
        }

    }
    public JPanel getTile(int row, int col) {
        return this.components[row][col]; 
    }
    public void fillGrid() { 
        this.removeAll();
    }
    public void generateNewTile() {
        int random_x = (int) (Math.random() * 4);
        int random_y = (int) (Math.random() * 4);
        while ((this.tiles[random_x][random_y]).getNumber() != 0) {
            random_x = (int) (Math.random() * 4);
            random_y = (int) (Math.random() * 4);
        }
        this.tiles[random_x][random_y] = new Tile(random_x, random_y);
    }
    public JPanel getgridContainer() {
        return this.gridContainer;
    }

    public void addTile(JPanel tile) {
        this.gridContainer.add(tile);
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int sc) {
        this.score = sc;
    }
    public void displayGameBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%3d  ", (tiles[i][j]).getNumber());
            }
            System.out.println(" ");
        }
    }
    public Tile[][] getTiles() {
        return this.tiles;
    }

    public int getTileScore(int x, int y) {
        int tile = (this.tiles[x][y]).getNumber();
        return tile;
    }
    public boolean checkTileOccupied(int x, int y) {
        if ((this.tiles[x][y]).getNumber() != 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean WinCheck() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.tiles[i][j].getNumber() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean LoseCheck() {
        return !(this.checkMovePossible()); 
        /*
        boolean possibleMovesDone = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.tiles[i][j].getNumber() == 0) {
                    return false;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0) {
                    if (j == 0) {
                        if (this.tiles[0][0].getNumber() == this.tiles[0][1].getNumber()
                                || this.tiles[0][0].getNumber() == this.tiles[1][0].getNumber()) {
                            return false;
                        } else {
                            possibleMovesDone = true;
                        }
                    } else {
                        for (int k = 3; k > 0; k--) {
                            if (this.tiles[0][k].getNumber() == this.tiles[1][k].getNumber()
                                    || this.tiles[0][k].getNumber() == this.tiles[0][k - 1].getNumber()) {
                                return false;
                            } else {
                                possibleMovesDone = true;
                            }
                        }
                    }
                }
                if (i == 1 || i == 2) {
                    if (j == 0) {
                        if (this.tiles[i][0].getNumber() == this.tiles[i + 1][0].getNumber()
                                || this.tiles[i][1].getNumber() == this.tiles[i][0].getNumber()) {
                            return false;
                        } else {
                            possibleMovesDone = true;
                        }
                    } else {
                        for (int k = 3; k > 0; k--) {
                            if (this.tiles[i][k].getNumber() == this.tiles[i - 1][k].getNumber()
                                    || this.tiles[i][k].getNumber() == this.tiles[i + 1][k].getNumber()
                                    || this.tiles[i][k].getNumber() == this.tiles[i][k - 1].getNumber()) {
                                return false;
                            } else {
                                possibleMovesDone = true;
                            }
                        }
                    }
                }
                if (i == 3) {
                    if (j == 0) {
                        if (this.tiles[i][0].getNumber() == this.tiles[i - 1][0].getNumber()
                                || this.tiles[i][1].getNumber() == this.tiles[i][0].getNumber()) {
                            return false;
                        } else {
                            possibleMovesDone = true;
                        }
                    } else {
                        for (int k = 3; k > 0; k--) {
                            if (this.tiles[3][k].getNumber() == this.tiles[2][k].getNumber()
                                    || this.tiles[3][k].getNumber() == this.tiles[3][k - 1].getNumber()) {
                                return false;
                            } else {
                                possibleMovesDone = true;
                            }
                        }
                    }
                }
            }
        }
        return possibleMovesDone;
    */ 

    }
    
    public void move(int direction) {
        int temp = 0;
        switch (direction) {
            case 0:
                // movement up
                for (int i = 0; i < tiles.length; i++) {
                    for (int j = 0; j < tiles[i].length; j++) {
                        if (this.tiles[j][i].getNumber() != 0) {
                            this.tiles[temp][i].setNumber(this.tiles[j][i].getNumber());
                            if (temp != j) {
                                this.tiles[j][i].setNumber(0);

                            }
                            temp++;
                        }
                    }
                    temp = 0;
                }
                for (int i = 0; i < tiles.length; i++) {
                    for (int j = 0; j < tiles[i].length - 1; j++) {
                        if (this.tiles[j][i].getNumber() == this.tiles[j + 1][i].getNumber()) {
                            calculateScore(j, i, j + 1, i);
                            this.tiles[j][i].setNumber(this.tiles[j][i].getNumber() + this.tiles[j + 1][i].getNumber());
                            this.tiles[j + 1][i].setNumber(0);
                            break;
                        }
                    }
                }
                for (int i = 0; i < tiles.length; i++) {
                    for (int j = 0; j < tiles[i].length; j++) {
                        if (this.tiles[j][i].getNumber() != 0) {
                            this.tiles[temp][i].setNumber(this.tiles[j][i].getNumber());
                            if (temp != j) {
                                this.tiles[j][i].setNumber(0);

                            }
                            temp++;
                        }
                    }
                    temp = 0;
                }
                break;
            case 1:
                // move right
                for (int i = 0; i < 4; i++) {
                    for (int j = 3; j >= 0; j--) {
                        if (this.tiles[i][j].getNumber() != 0) {
                            this.tiles[i][3 - temp].setNumber(this.tiles[i][j].getNumber());
                            if (3 - temp != j) {
                                this.tiles[i][j].setNumber(0);
                            }
                            temp++;
                        }
                    }
                    temp = 0;
                }
                for (int i = 0; i < 4; i++) {
                    for (int j = 3; j > 0; j--) {
                        if (this.tiles[i][j].getNumber() == this.tiles[i][j - 1].getNumber()) {
                            calculateScore(i, j, i, j - 1);
                            this.tiles[i][j].setNumber(this.tiles[i][j].getNumber() + this.tiles[i][j - 1].getNumber());
                            this.tiles[i][j - 1].setNumber(0);
                            break;
                        }
                    }
                }
                for (int i = 0; i < 4; i++) {
                    for (int j = 3; j >= 0; j--) {
                        if (this.tiles[i][j].getNumber() != 0) {
                            this.tiles[i][3 - temp].setNumber(this.tiles[i][j].getNumber());
                            if (3 - temp != j) {
                                this.tiles[i][j].setNumber(0);
                            }
                            temp++;
                        }
                    }
                    temp = 0;
                }
                break;
            case 2:
                // move down
                for (int i = 0; i < 4; i++) {
                    for (int j = 3; j >= 0; j--) {
                        if (this.tiles[j][i].getNumber() != 0) {
                            this.tiles[3 - temp][i].setNumber(this.tiles[j][i].getNumber());
                            if (3 - temp != j) {
                                this.tiles[j][i].setNumber(0);

                            }
                            temp++;
                        }
                    }
                    temp = 0;
                }
                for (int i = 0; i < 4; i++) {
                    for (int j = 3; j > 0; j--) {
                        if (this.tiles[j][i].getNumber() == this.tiles[j - 1][i].getNumber()) {
                            calculateScore(j, i, j - 1, i);
                            this.tiles[j][i].setNumber(this.tiles[j][i].getNumber() + this.tiles[j][i].getNumber());
                            this.tiles[j - 1][i].setNumber(0);
                            break;
                        }
                    }
                }
                for (int i = 0; i < 4; i++) {
                    for (int j = 3; j >= 0; j--) {
                        if (this.tiles[j][i].getNumber() != 0) {
                            this.tiles[3 - temp][i].setNumber(this.tiles[j][i].getNumber());
                            if (3 - temp != j) {
                                this.tiles[j][i].setNumber(0);
                            }
                            temp++;
                        }
                    }
                    temp = 0;
                }
                break;
            case 3:
                // move left
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (this.tiles[i][j].getNumber() != 0) {
                            this.tiles[i][temp].setNumber(this.tiles[i][j].getNumber());
                            if (temp != j) {
                                this.tiles[i][j].setNumber(0);
                            }
                            temp++;
                        }
                    }
                    temp = 0;
                }
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (this.tiles[i][j].getNumber() == this.tiles[i][j + 1].getNumber()) {
                            calculateScore(i, j, i, j + 1);
                            this.tiles[i][j].setNumber(this.tiles[i][j].getNumber() + this.tiles[i][j + 1].getNumber());
                            this.tiles[i][j + 1].setNumber(0);
                            break;
                        }
                    }
                }
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (this.tiles[i][j].getNumber() != 0) {
                            this.tiles[i][temp].setNumber(this.tiles[i][j].getNumber());
                            if (temp != j) {
                                this.tiles[i][j].setNumber(0);
                            }
                            temp++;
                        }
                    }
                    temp = 0;
                }
                break;
            default:
                System.out.println("This is not a valid move. Please try again. ");
                break;
        }
    }

    public boolean checkMovePossible() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile temp = tiles[i][j];
                if (temp.getNumber() == 0) {
                    return true;
                }
                if (i < 3) {
                    Tile bottom = tiles[i + 1][j];
                    if ((temp.getNumber()) == (bottom.getNumber())) {
                        return true;
                    }
                    if (j < 3) {
                        Tile right = tiles[i][j + 1];
                        if (right.getNumber() == temp.getNumber()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public void calculateScore(int x1, int y1, int x2, int y2) {
        this.score = getScore() + this.tiles[x1][y1].getNumber() + this.tiles[x2][y2].getNumber();
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {
            if (this.nextTurnPossible()) {
                System.out.println("Pressed Right Button"); 
                this.move(1);
                displayGameBoard();
                this.repaint();
            }
        } else if (key == KeyEvent.VK_LEFT) {
            if (this.nextTurnPossible()) {
                System.out.println("Pressed Left Button"); 
                this.move(3);
                displayGameBoard();
                this.repaint();
            }
        } else if (key == KeyEvent.VK_UP) {
            if (this.nextTurnPossible()) {
                System.out.println("Pressed Up Button"); 
                this.move(0);
                displayGameBoard(); 
                this.repaint();
            } 
        } else if (key == KeyEvent.VK_DOWN) {
            if (this.nextTurnPossible()) {
                System.out.println("Pressed Down button"); 
                this.move(2);
                displayGameBoard();
                this.repaint(); 
            }
        }
        else {
            System.out.println("You're out"); 
        }
    }
    public boolean nextTurnPossible() {
        if (this.WinCheck() == true) {
            this.removeAll();
            System.out.println("Won the game"); 
            JLabel win = new JLabel("You Win!"); 
            this.add(win); 
            return false; 
        } else if (this.LoseCheck() == true) {
            System.out.println("Lost the game");
            this.removeAll();
            JLabel lose = new JLabel("You Lose"); 
            this.add(lose); 
            return false; 
        } else {
            this.generateNewTile();
            return true; 
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    public void switchTile(int x1, int y1, int x2, int y2) {
        Tile placeholder = tiles[x1][y1];
        tiles[x1][y1] = tiles[x2][y2];
        tiles[x2][y2] = placeholder;

    }
    public void delete(int x, int y) {
        this.tiles[x][y].setNumber(0);
    }

    public void combine(int x1, int y1, int x2, int y2, int direction) {
        this.tiles[x1][y1].setNumber(this.tiles[x1][y1].getNumber() * 2);
        this.tiles[x2][y2].setNumber(0);
    }
}