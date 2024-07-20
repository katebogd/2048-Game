import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Game Window");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Model model = new Model();
        GamePanel gamePanel = new GamePanel(model);
        
        frame.add(gamePanel);
        frame.setVisible(true);
    }
}



class Model {
    private int[][] grid;
    private Random random;

    public Model() {
        grid = new int[4][4];
        random = new Random();
        addRandomTile();
        addRandomTile();
    }

    public int[][] getGrid() {
        return grid;
    }

    public void leftShift() {
        int[][] oldGrid = copyGrid(grid);
        for (int i = 0; i < 4; i++) {
            shiftRowLeft(i);
        }
        if (!gridsEqual(oldGrid, grid)) {
            addRandomTile();
        }
    }

    public void rightShift() {
        int[][] oldGrid = copyGrid(grid);
        for (int i = 0; i < 4; i++) {
            shiftRowRight(i);
        }
        if (!gridsEqual(oldGrid, grid)) {
            addRandomTile();
        }
    }

    public void upShift() {
        int[][] oldGrid = copyGrid(grid);
        for (int j = 0; j < 4; j++) {
            shiftColumnUp(j);
        }
        if (!gridsEqual(oldGrid, grid)) {
            addRandomTile();
        }
    }

    public void downShift() {
        int[][] oldGrid = copyGrid(grid);
        for (int j = 0; j < 4; j++) {
            shiftColumnDown(j);
        }
        if (!gridsEqual(oldGrid, grid)) {
            addRandomTile();
        }
    }

    private void shiftRowLeft(int row) {
        int[] newRow = new int[4];
        int index = 0;
        for (int j = 0; j < 4; j++) {
            if (grid[row][j] != 0) {
                newRow[index++] = grid[row][j];
            }
        }
        for (int j = 0; j < 3; j++) {
            if (newRow[j] == newRow[j + 1] && newRow[j] != 0) {
                newRow[j] *= 2;
                newRow[j + 1] = 0;
                j++;
            }
        }
        index = 0;
        for (int j = 0; j < 4; j++) {
            if (newRow[j] != 0) {
                grid[row][index++] = newRow[j];
            }
        }
        while (index < 4) {
            grid[row][index++] = 0;
        }
    }

    private void shiftRowRight(int row) {
        int[] newRow = new int[4];
        int index = 3;
        for (int j = 3; j >= 0; j--) {
            if (grid[row][j] != 0) {
                newRow[index--] = grid[row][j];
            }
        }
        for (int j = 3; j > 0; j--) {
            if (newRow[j] == newRow[j - 1] && newRow[j] != 0) {
                newRow[j] *= 2;
                newRow[j - 1] = 0;
                j--;
            }
        }
        index = 3;
        for (int j = 3; j >= 0; j--) {
            if (newRow[j] != 0) {
                grid[row][index--] = newRow[j];
            }
        }
        while (index >= 0) {
            grid[row][index--] = 0;
        }
    }

    private void shiftColumnUp(int col) {
        int[] newCol = new int[4];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            if (grid[i][col] != 0) {
                newCol[index++] = grid[i][col];
            }
        }
        for (int i = 0; i < 3; i++) {
            if (newCol[i] == newCol[i + 1] && newCol[i] != 0) {
                newCol[i] *= 2;
                newCol[i + 1] = 0;
                i++;
            }
        }
        index = 0;
        for (int i = 0; i < 4; i++) {
            if (newCol[i] != 0) {
                grid[index++][col] = newCol[i];
            }
        }
        while (index < 4) {
            grid[index++][col] = 0;
        }
    }

    private void shiftColumnDown(int col) {
        int[] newCol = new int[4];
        int index = 3;
        for (int i = 3; i >= 0; i--) {
            if (grid[i][col] != 0) {
                newCol[index--] = grid[i][col];
            }
        }
        for (int i = 3; i > 0; i--) {
            if (newCol[i] == newCol[i - 1] && newCol[i] != 0) {
                newCol[i] *= 2;
                newCol[i - 1] = 0;
                i--;
            }
        }
        index = 3;
        for (int i = 3; i >= 0; i--) {
            if (newCol[i] != 0) {
                grid[index--][col] = newCol[i];
            }
        }
        while (index >= 0) {
            grid[index--][col] = 0;
        }
    }

    private void addRandomTile() {
        int value = 2;
        int x, y;
        do {
            x = random.nextInt(4);
            y = random.nextInt(4);
        } while (grid[x][y] != 0);
        grid[x][y] = value;
    }

    private int[][] copyGrid(int[][] grid) {
        int[][] newGrid = new int[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(grid[i], 0, newGrid[i], 0, 4);
        }
        return newGrid;
    }

    private boolean gridsEqual(int[][] grid1, int[][] grid2) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid1[i][j] != grid2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printGrid() {
        for (int[] row : grid) {
            for (int num : row) {
                System.out.print(num + "\t");
            }
            System.out.println();
        }
    }
}




class GamePanel extends JPanel {
    private BufferedImage upArrowImage;
    private BufferedImage downArrowImage;
    private BufferedImage leftArrowImage;
    private BufferedImage rightArrowImage;
    private List<Object[]> assetPositions;
    private int upArrowX, upArrowY, downArrowX, downArrowY, leftArrowX, leftArrowY, rightArrowX, rightArrowY;
    private int gridX, gridY, cellSize;
    private Model model;

    public GamePanel(Model model) {
    	this.model = model;
        assetPositions = new ArrayList<>();
    	

        setBackground(Color.BLACK);

        try {
            upArrowImage = ImageIO.read(getClass().getResourceAsStream("/up_arrow.png"));
            downArrowImage = ImageIO.read(getClass().getResourceAsStream("/down_arrow.png"));
            leftArrowImage = ImageIO.read(getClass().getResourceAsStream("/left_arrow.png"));
            rightArrowImage = ImageIO.read(getClass().getResourceAsStream("/right_arrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Listen on mouse click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if (x >= upArrowX && x <= upArrowX + upArrowImage.getWidth() &&
                    y >= upArrowY && y <= upArrowY + upArrowImage.getHeight()) {
                    upArrowPressed();
                } else if (x >= downArrowX && x <= downArrowX + downArrowImage.getWidth() &&
                           y >= downArrowY && y <= downArrowY + downArrowImage.getHeight()) {
                    downArrowPressed();
                } else if (x >= leftArrowX && x <= leftArrowX + leftArrowImage.getWidth() &&
                           y >= leftArrowY && y <= leftArrowY + leftArrowImage.getHeight()) {
                    leftArrowPressed();
                } else if (x >= rightArrowX && x <= rightArrowX + rightArrowImage.getWidth() &&
                           y >= rightArrowY && y <= rightArrowY + rightArrowImage.getHeight()) {
                    rightArrowPressed();
                }
            }
        });
        
        // Listen on keyboard arrow click
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                System.out.println("Pressed");
                System.out.println(e);
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        upArrowPressed();
                        break;
                    case KeyEvent.VK_DOWN:
                        downArrowPressed();
                        break;
                    case KeyEvent.VK_LEFT:
                        leftArrowPressed();
                        break;
                    case KeyEvent.VK_RIGHT:
                        rightArrowPressed();
                        break;
                }
            }
        });
        
        setFocusable(true);
        
        this.place_numbers();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        upArrowX = (width - upArrowImage.getWidth()) / 2;
        upArrowY = 20;
        downArrowX = (width - downArrowImage.getWidth()) / 2;
        downArrowY = height - downArrowImage.getHeight() - 20;
        leftArrowX = 20;
        leftArrowY = (height - leftArrowImage.getHeight()) / 2;
        rightArrowX = width - rightArrowImage.getWidth() - 20;
        rightArrowY = (height - rightArrowImage.getHeight()) / 2;

        g.drawImage(upArrowImage, upArrowX, upArrowY, this);
        g.drawImage(downArrowImage, downArrowX, downArrowY, this);
        g.drawImage(leftArrowImage, leftArrowX, leftArrowY, this);
        g.drawImage(rightArrowImage, rightArrowX, rightArrowY, this);

        cellSize = 96;
        int gridSize = cellSize * 4;
        gridX = (width - gridSize) / 2;
        gridY = (height - gridSize) / 2;

        g.setColor(Color.WHITE);
        for (int i = 0; i <= 4; i++) {
            g.drawLine(gridX, gridY + i * cellSize, gridX + gridSize, gridY + i * cellSize);
            g.drawLine(gridX + i * cellSize, gridY, gridX + i * cellSize, gridY + gridSize);
        }
        
        for (int i = 0; i < assetPositions.size(); i++) {
        	Object[] tuple = assetPositions.get(i);
            int assetPosX = gridX + (int)tuple[0] * cellSize;
            int assetPosY = gridY + (int)tuple[1] * cellSize;
	        BufferedImage img;
			try {
				img = ImageIO.read(getClass().getResourceAsStream("/" + tuple[2]));
	            g.drawImage(img, assetPosX, assetPosY, cellSize, cellSize, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
    }

    private void upArrowPressed() {
    	System.out.println("up");
        this.model.upShift();
        this.place_numbers();
    }

    private void downArrowPressed() {
    	System.out.println("down");
        this.model.downShift();
        this.place_numbers();
    }

    private void leftArrowPressed() {
    	System.out.println("left");
        this.model.leftShift();
        this.place_numbers();
    }

    private void rightArrowPressed() {
    	System.out.println("right");
        this.model.rightShift();
        this.place_numbers();
    }
    
    private void place_numbers() {
    	int[][] grid = this.model.getGrid();
    	this.model.printGrid();
    	this.clearAssets();
    	for (int i = 0; i < 4; i++) {
    		for (int j = 0; j < 4; j++) {
    			if (grid[i][j] != 0) {
    				placeAsset(j, i, Integer.toString(grid[i][j]) + ".png");
    			}
    		}
    	}
    	System.out.println(assetPositions);
    	repaint();
    }

    public void placeAsset(int x, int y, String filename) {
    	assetPositions.add(new Object[] {x, y, filename});
    }
    
    public void clearAssets() {
    	assetPositions.clear();
    }
}
