import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.List;
import java.util.Stack;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
public class Game extends JPanel implements KeyListener{
    private int score;
    private Tile[][] tiles = new Tile[4][4];
    private Tile[][] prev_tiles = new Tile[4][4]; 
    private JPanel[][] components = new JPanel[4][4]; 
    private JPanel gridContainer;
    private JPanel score_panel; 
    private boolean gameOver = false; 
    private boolean needRestart = false; 
    private int highScore = 0; 
    private Stack<Tile[][]> prev_moves; 
    private Stack<Integer> score_history; 
    private String filepath = "bensound-anewbeginning.wav"; 
    private Sound musicObject;
    private boolean MusicAllowed = true; 
    private JPanel menu; 
    public Game() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.tiles[i][j] = new Tile(); 
                this.prev_tiles[i][j] = new Tile();
            }
        } 
        musicObject = new Sound();
        prev_moves = new Stack<>(); 
        score_history = new Stack<>(); 
        generateNewTile(); 
        this.storePrevTiles();
        JPanel game = new JPanel(); 
        score_panel = new JPanel(); 
        JLabel sc = new JLabel("Score: " + this.getScore()); 
        JLabel high_score = new JLabel("High Score: " + this.getHighScore()); 
        score_panel.add(sc); 
        score_panel.add(high_score); 
        GridLayout grid = new GridLayout(4,4); 
        this.setLayout(new BorderLayout()); 
        game.setLayout(grid);
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
                Font setFont = new Font("Arial", Font.BOLD, 100);
                lab.setFont(setFont);
                this.components[i][j].add(lab); 
                game.add(this.components[i][j]);
            }
        } 
        this.add(score_panel, BorderLayout.NORTH); 
        this.add(game,BorderLayout.CENTER); 
        JButton undo = new JButton("Undo"); 
        JButton new_game = new JButton("Play New Game!"); 
        JButton turn_music_off = new JButton("Turns Music Off"); 
        JButton turn_music_on = new JButton("Turns Music On"); 
        undo.addActionListener(new ActionListener() {    	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				undo(); 
			}
		});
        new_game.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                resetGame();

            }
            
        });
        turn_music_off.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				turn_music_off(); 
			}
        	
        });
        turn_music_on.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				turn_music_on(); 
			}
        	
        });
        menu = new JPanel(); 
        menu.add(new_game); 
        menu.add(undo); 
        menu.add(turn_music_off); 
        menu.add(turn_music_on); 
        this.add(menu, BorderLayout.SOUTH); 
        this.addKeyListener(this);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JPanel tile = this.getTile(i, j); 
                JLabel label = (JLabel)tile.getComponent(0); 
                String num = "" + this.tiles[i][j].getNumber(); 
                label.setText(num);
                tile.setBackground(this.tiles[i][j].setColor(this.tiles[i][j].getNumber()));
            }
        }
        JLabel score = (JLabel)this.getScorePanel().getComponent(0); 
        score.setText("Score: " + this.getScore()); 
        JLabel hs = (JLabel)this.getScorePanel().getComponent(1); 
        hs.setText("High Score: " + this.getHighScore());
        if (this.getGameOver() == true) {
        	return; 
        }
        if (this.gameOver == false && this.LoseCheck() == true) {
        	this.gameOver = true; 
            JLabel lose = new JLabel("You lost!"); 
            this.getMenu().add(lose); 
        }
        else if (this.gameOver == false && this.WinCheck() == true) {
        	this.gameOver = true; 
            JLabel win = new JLabel("You Win!"); 
            this.getMenu().add(win);  
        }
        else {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    JPanel tile = this.getTile(i, j); 
                    JLabel label = (JLabel)tile.getComponent(0); 
                    String num = "" + this.tiles[i][j].getNumber(); 
                    label.setText(num);
                    tile.setBackground(this.tiles[i][j].setColor(this.tiles[i][j].getNumber()));
                }
            }
            JLabel scoreOne = (JLabel)this.getScorePanel().getComponent(0); 
            score.setText("Score: " + this.getScore()); 
        } 
    }
    public JPanel getMenu() {
    	return this.menu; 
    }
    public void turn_music_off() {
    	this.MusicAllowed = false; 
    	this.setFocusable(true);
        this.requestFocus();
    }
    public void turn_music_on() {
    	this.MusicAllowed = true; 
    	this.setFocusable(true);
        this.requestFocus();
    }
    public void displayStack() {
    	for (int i = 0; i < prev_moves.size(); i++) {
    		System.out.println(); 
    		System.out.println(); 
    		System.out.println(i + "'st move"); 
    		this.displayGameBoard(prev_moves.get(i));
    	}
    }
    public void undo() {
    	if (prev_moves.empty()) {
    		System.out.println("Stack is empty"); 
    		this.setFocusable(true);
            this.requestFocus(); 
        	this.repaint();
    		return; 
    	}
    	Tile[][] last_move = prev_moves.pop();
    	if (score_history.empty() || score_history.size() == 1) {
    		this.score = 0; 
    	}
    	else {
    		int last_score = score_history.pop(); 
    		this.score = score_history.lastElement();
    	}
    	JLabel score = (JLabel)this.getScorePanel().getComponent(0); 
        score.setText("Score: " + this.getScore());
        this.repaint();
    	for (int i = 0; i < 4; i++) {
    		for (int j = 0; j < 4; j++) {
    			this.tiles[i][j].setNumber(last_move[i][j].getNumber());
    		}
    	}
    	this.setFocusable(true);
        this.requestFocus(); 
    	this.repaint();
    }
    public int getHighScore() {
    	return this.highScore; 
    }
    public void setHighScore() {
    	if (this.score > this.highScore) {
    		this.highScore = this.score; 
    		this.repaint();
    	}
    }
    public void resetGame() {
    	boolean deleteComp = false; 
    	if (this.LoseCheck() || this.WinCheck()) {
    		deleteComp = true; 
    	}
        this.tiles = new Tile[4][4];
        this.setHighScore();
        this.score = 0;
        this.prev_tiles = new Tile[4][4]; 
        this.gameOver = false; 
        this.needRestart = false; 
        this.prev_moves.clear();
        this.score_history.clear();
        if (deleteComp == true) {
        	this.getMenu().remove(this.getMenu().getComponentCount() - 1);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.tiles[i][j] = new Tile(); 
                this.prev_tiles[i][j] = new Tile();
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JPanel tile = this.getTile(i, j); 
                JLabel label = (JLabel)tile.getComponent(0); 
                String num = "" + this.tiles[i][j].getNumber(); 
                label.setText(num);
                tile.setBackground(this.tiles[i][j].setColor(this.tiles[i][j].getNumber()));
            }
        }
        JLabel score = (JLabel)this.getScorePanel().getComponent(0); 
        score.setText("Score: " + this.getScore()); 
        JLabel hs = (JLabel)this.getScorePanel().getComponent(1); 
        hs.setText("High Score: " + this.getHighScore());
        this.generateNewTile();
        this.addKeyListener(this);
        this.storePrevTiles();
        this.setFocusable(true);
        this.requestFocus();
        JLabel lose = new JLabel("You lost!"); 
        this.getMenu().remove(lose);
        this.repaint(); 
    }
    public boolean getRestart() {
    	return this.needRestart; 
    }
    public void setRestart(boolean a) {
    	this.needRestart = a; 
    }
    public JPanel getScorePanel() {
        return this.score_panel; 
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
    public void printScoreHistory() {
    	System.out.println("Score History for this Move. "); 
    	for (int i = 0; i < score_history.size(); i++) {
    		System.out.println("Score: " + score_history.get(i)); 
    	}
    }

    public void setScore(int sc) {
        this.score = sc;
    }
    public void displayGameBoard(Tile[][] tiles) {
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
    public boolean getGameOver() {
    	return this.gameOver; 
    }

    public boolean LoseCheck() {
        return (!(this.checkMovePossible())); 
        /*
        else {
            for (int i = 0; i < 4; i++) {
                for (int j = 1; j < 4; j++) {
                    if (this.tiles[i][j].getNumber() == this.tiles[i][j - 1].getNumber()) {
                        return false; 
                    }
                }
            }
            for (int i = 0; i < 4; i++) {
                for (int j = 1; j < 4; j++) {
                    if (this.tiles[j][i].getNumber() == this.tiles[j - 1][i].getNumber()) {
                        return false; 
                    }
                }
            }
            System.out.println("Lost triggered"); 
            return true; 
        }
        */ 
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
        this.storePrevTiles();
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
                            this.tiles[j][i].setClipTime(this.tiles[i][j].getNumber()); 
                            if (this.MusicAllowed == true) {
                            	musicObject.music(filepath,this.tiles[i][j].getClipTime());
                            }
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
                score_history.push(this.score);
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
                            this.tiles[i][j].setClipTime(this.tiles[i][j].getNumber()); 
                            if (this.MusicAllowed == true) {
                            	musicObject.music(filepath,this.tiles[i][j].getClipTime());
                            }
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
                score_history.push(this.score);
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
                            this.tiles[j][i].setClipTime(this.tiles[j][i].getNumber()); 
                            if (this.MusicAllowed == true) {
                            	musicObject.music(filepath,this.tiles[j][i].getClipTime());
                            }
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
                score_history.push(this.score);
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
                            this.tiles[i][j].setClipTime(this.tiles[i][j].getNumber()); 
                            if (this.MusicAllowed == true) {
                            	musicObject.music(filepath,this.tiles[i][j].getClipTime());
                            }
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
                score_history.push(this.score);
                break;
            default:
                break;
        }
    }

    public boolean checkMovePossible() {
    	for (int i = 0; i < 4; i++) {
    		for (int j = 0; j < 4; j++) {
    			if (this.tiles[i][j].getNumber() == 0) {
    				return true; 
    			}
    		}
    	}
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile temp = tiles[i][j];
                if (i < 3) {
                    Tile bottom = tiles[i + 1][j];
                    if ((temp.getNumber()) == (bottom.getNumber())) {
                        return true;
                    }
                }
                if (j < 3) {
                   Tile right = tiles[i][j + 1];
                   if (right.getNumber() == temp.getNumber()) {
                
                	   return true;
                   }
                }
            }
        }
        return false;
    }
    public void calculateScore(int x1, int y1, int x2, int y2) {
        this.score = getScore() + this.tiles[x1][y1].getNumber() + this.tiles[x2][y2].getNumber(); 
        this.setHighScore();
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
            	if (this.prev_moves.size() == 1) {
            		this.generateNewTile();
            	}
                this.move(1);
                this.repaint();
            }
        } else if (key == KeyEvent.VK_LEFT) {
            if (this.nextTurnPossible()) {
            	if (this.prev_moves.size() == 1) {
            		this.generateNewTile();
            	}
                this.move(3);
                this.repaint();
            }
        } else if (key == KeyEvent.VK_UP) {
            if (this.nextTurnPossible()) {
            	if (this.prev_moves.size() == 1) {
            		this.generateNewTile();
            	}
                this.move(0);
                this.repaint();
            } 
        } else if (key == KeyEvent.VK_DOWN) {
            if (this.nextTurnPossible()) {
            	if (this.prev_moves.size() == 1) {
            		this.generateNewTile();
            	}
                this.move(2);
                this.repaint(); 
            }
        }
        else {
           return; 
        }
    }
    public boolean nextTurnPossible() {
    	this.repaint();
        if (gameOver == false && this.WinCheck() == true) {
            JLabel win = new JLabel("You Win!"); 
            this.getMenu().add(win); 
            gameOver = true; 
            this.repaint(); 
            return false; 
        } else if (gameOver == false && this.LoseCheck() == true) { 
        	this.gameOver = true; 
            JLabel lose = new JLabel("You Lose"); 
            this.getMenu().add(lose);
            System.out.println("added lose"); 
            this.repaint();
            /* 
            try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            this.getMenu().remove(this.getMenu().getComponentCount() - 1); 
            this.repaint();
            */ 
            return false; 
        } else {
            if (this.noChanges() == false) { 
            	this.generateNewTile();
            	this.repaint();
            }
            return true; 
        }
    }
    public void storePrevTiles() { 
    	Tile[][] copy_prev = new Tile[4][4];
    	for (int i = 0; i < 4; i++) {
    		for (int j = 0; j < 4; j++) {
    			copy_prev[i][j] = new Tile(); 
    			copy_prev[i][j].setNumber(tiles[i][j].getNumber());
    		}
    	}
    	prev_moves.push(copy_prev); 
        for (int i = 0; i < 4; i++) {
        	for (int j = 0; j < 4; j++) {
        		this.prev_tiles[i][j].setNumber(this.tiles[i][j].getNumber());; 
        	}
        }
        this.repaint(); 
    }
    public boolean noChanges() {
        for (int i = 0; i < 4; i++) {
        	for (int j = 0; j < 4; j++) {
        		if (prev_tiles[i][j].getNumber() != tiles[i][j].getNumber()) {
        			return false; 
        		}
        	}
        }
        return true; 
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

}