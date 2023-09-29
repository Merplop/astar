import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.naming.ldap.UnsolicitedNotification;
import javax.swing.*;
public class Grid {
    public Grid(int r, int c, int l) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
                String title = "";
                if (l == 1) {
                    title = "A* Algorithm, Miro Haapalainen";
                } else {
                    title = "A*-algoritmi, Miro Haapalainen";
                }
                JFrame frame = new JFrame(title);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.setPreferredSize(new Dimension(c*20, r*20));
                frame.add(new Pane(c, r));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);
            }
        });
    }

    public static class Pane extends JPanel {
        public enum SelectionMode {togglePassable, origin, target}
        public SelectionMode selectionMode;
        private final int colCount;
        private final int rowCount;
        private final ArrayList<Cell> cells;
        private final ArrayList<Cell> notPassableCells;
        private Point selectedCell;
        private Point originCell;
        private Point targetCell;

        public Pane(int c, int r) {
            colCount = c;
            rowCount = r;
            cells = new ArrayList<>(colCount * rowCount);
            notPassableCells = new ArrayList<>(colCount * rowCount);

            MouseAdapter mouseHandler;
            mouseHandler = new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    Point point = e.getPoint();
                    int width = getWidth();
                    int height = getHeight();
                    int cellWidth = width / colCount;
                    int cellHeight = height / rowCount;
                    int xOffset = (width - (colCount * cellWidth)) / 2;
                    int yOffset = (height - (rowCount * cellHeight)) / 2;
                    selectedCell = null;
                    if (e.getX() >= xOffset && e.getY() >= yOffset) {
                        int column = (e.getX() - xOffset) / cellWidth;
                        int row = (e.getY() - yOffset) / cellHeight;

                        if (column >= 0 && row >= 0 && column < colCount && row < rowCount) {
                            selectedCell = new Point(column, row);
                        }
                    }
                    repaint();

                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    int index = selectedCell.x + (selectedCell.y * colCount);
                    Cell cell = cells.get(index);
                    if (cell.cellStatus == Cell.CellStatus.passable) {
                        cell.cellStatus = Cell.CellStatus.notPassable;
                        notPassableCells.add(cell);
                    }
                }
            };
            addMouseMotionListener(mouseHandler);    // add this stupid shit to this other stupid shitty thing
            addMouseListener(mouseHandler);         // apparently this needs to be here too for some fucking reason >:(
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(colCount*20, rowCount*20);
        }

        @Override
        public void invalidate() {
            cells.clear();
            selectedCell = null;
            super.invalidate();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            int width = getWidth();
            int height = getHeight();
            int cellWidth = width / colCount;
            int cellHeight = height / rowCount;
            int xOffset = (width - (colCount * cellWidth)) / 2;
            int yOffset = (height - (rowCount * cellHeight)) / 2;
            if (cells.isEmpty()) {
                for (int row = 0; row < rowCount; row++) {
                    for (int col = 0; col < colCount; col++) {
                        Rectangle cellRect = new Rectangle(
                                xOffset + (col * cellWidth),
                                yOffset + (row * cellHeight),
                                cellWidth,
                                cellHeight);
                        Cell cell = new Cell(Cell.CellStatus.passable, cellRect);
                        cells.add(cell);
                    }
                }
            }
            if (selectedCell != null) {
                int index = selectedCell.x + (selectedCell.y * colCount);
                Cell cell = cells.get(index);
                g2d.setColor(Color.GREEN);
                g2d.fill(cell.rect);
            }
            if (!notPassableCells.isEmpty()) {
                for (Cell c : notPassableCells) {
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.fill(c.rect);
                }
            }
            g2d.setColor(Color.GRAY);
            for (Cell cell : cells) {
                g2d.draw(cell.rect);
            }
            g2d.dispose();
        }
    }
}
