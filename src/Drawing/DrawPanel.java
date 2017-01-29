package Drawing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Konstantin on 28.01.2017.
 */
class DrawPanel extends JPanel {
    final int CELL_SIZE, V_CELLS_COUNT, H_CELLS_COUNT;
    final int V_SIZE, H_SIZE;
    final char INIT_CELL_VAL;
    final int INIT_CELL_X,INIT_CELL_Y;
    int crnt_row;
    char cellAtm[][];
    boolean WhiteScreen;

    public DrawPanel(int cellSize, int vCellsCount, int hCellsCount) {
        this.CELL_SIZE = cellSize;
        this.V_CELLS_COUNT = vCellsCount;
        this.H_CELLS_COUNT = hCellsCount;
        this.V_SIZE = CELL_SIZE * V_CELLS_COUNT;
        this.H_SIZE = CELL_SIZE * H_CELLS_COUNT;
        this.INIT_CELL_VAL= (char)3;
        this.INIT_CELL_X = H_CELLS_COUNT/2;
        this.INIT_CELL_Y = V_CELLS_COUNT/2;
        this.crnt_row = INIT_CELL_Y;
        this.WhiteScreen = false;
        cellAtm = new char[V_CELLS_COUNT][];
        for(int i=0; i<V_CELLS_COUNT; i++){
            cellAtm[i] = new char[H_CELLS_COUNT];
            for(int j=0; j<H_CELLS_COUNT; j++){
                cellAtm[i][j] = INIT_CELL_VAL;
            }
        }
        cellAtm[INIT_CELL_Y][INIT_CELL_X] = (char)(INIT_CELL_VAL+1);
    }

    @Override
    public void paintComponent(Graphics g) {
//            super.paintComponents(g);
//        g.fillRect(20,20,100,count);
            DrawCellAtm(g);
            this.DrawGrid(g);
    }

    void DrawCellAtm(Graphics g){
        for(int i=0; i<V_CELLS_COUNT; i++){
            for (int j=0; j<H_CELLS_COUNT; j++){
                this.DrawCell(g, cellAtm[i][j], j, i);
            }
        }
    }

    public void Increment(){
        if(++this.crnt_row >= V_CELLS_COUNT) crnt_row = 0;
        this.RecalculateCrntRow();
    }

    private void RecalculateCrntRow(){
        char parentCombo;
        for(int j=0; j<H_CELLS_COUNT; j++){
            parentCombo = CalculateParentCombo(j, this.crnt_row);
            switch (parentCombo){
                case 0:
                    cellAtm[crnt_row][j]++;
                    break;
                case 1:
                    cellAtm[crnt_row][j]++;
                    break;
                case 2:
                    cellAtm[crnt_row][j]--;
                    break;
                case 3:
                    cellAtm[crnt_row][j]++;
                    break;
                case 4:
                    cellAtm[crnt_row][j]--;
                    break;
                case 5:
                    cellAtm[crnt_row][j]++;
                    break;
                case 6:
                    cellAtm[crnt_row][j]++;
                    break;
                case 7:
                    cellAtm[crnt_row][j]--;
                    break;
                default:
                    break;
            }
        }
    }

    private char CalculateParentCombo(int x, int y){
        char result = 0;
        int leftIndex = (x>0)? (x-1): H_CELLS_COUNT-1;
        int rightIndex = (x<H_CELLS_COUNT-1)? (x+1): 0;
        int yIndex = (y==0) ? V_CELLS_COUNT-1 : y-1;
        if(cellAtm[yIndex][rightIndex] > cellAtm[y][x]) result += 1;
        if(cellAtm[yIndex][x] > cellAtm[y][x]) result += 2;
        if(cellAtm[yIndex][leftIndex] > cellAtm[y][x]) result += 4;
        return result;
    }

    private void DrawGrid(Graphics g){
        int offset = 1;
        int varCoord = 0;
        g.setColor(new Color(0x000000));
        for(int i=1; i<V_CELLS_COUNT; i++){
            varCoord = offset++*CELL_SIZE;
            g.drawLine(0, varCoord, H_SIZE, varCoord);
        }
        offset = 1;
        for(int i=1; i<H_CELLS_COUNT; i++){
            varCoord = offset++*CELL_SIZE;
            g.drawLine(varCoord, 0, varCoord, V_SIZE);
        }
    }

    private void DrawCell(Graphics g, char value, int x, int y){
        g.setColor(ConvertValueToColor(value));
        g.fillRect(x++*CELL_SIZE, y++*CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    private Color ConvertValueToColor(char value){
        switch (value){
            case 0:
                return new Color(0x0202EC);
            case 1:
                return new Color(0xA34BEC);
            case 2:
                return new Color(0x0FE9EC);
            case 3:
                return new Color(0xE0E3EC);
            case 4:
                return new Color(0xECE70D);
            case 5:
                return new Color(0x1EEC26);
            case 6:
                return new Color(0xEC1C12);
            default:
                return new Color(1);
        }
    }

    public void SetWhiteScreen(){
        this.WhiteScreen = true;
    }

}
