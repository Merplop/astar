import java.awt.*;
import javax.swing.*;
public class Cell {
    public enum CellStatus {selected, passable, notPassable}
    Rectangle rect;             // I'm leaving these without access modifiers because I don't wanna write a bunch of boilerplate >:(
    CellStatus cellStatus;
    public Cell(CellStatus c, Rectangle r) {
        this.cellStatus = c;
        this.rect = r;
    }
}
