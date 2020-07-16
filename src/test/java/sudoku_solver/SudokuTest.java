package sudoku_solver;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuTest {
  @Test
  void emptySudokuShouldBeSolved() {
    int[][] test = new int[9][9];
      for(int i = 0; i < 9; i++){
        for(int j = 0; j < 9; j++){
          test[i][j] = 0;
        }
    }
    Sudoku sudoku = new Sudoku(test);
    sudoku.solve();
    
    assertEquals(sudoku.isSolved(), true);
  }
  
  @Test
  void solvableSudokuShouldBeSolved1() {
    int[][] test = new int[9][9];
      for(int i = 0; i < 9; i++){
        for(int j = 0; j < 9; j++){
          test[i][j] = 0;
        }
    }
    //solvable sudoku
    test[0][0] = 7;
    test[0][5] = 4;
    test[0][8] = 8;

    test[1][1] = 3;
    test[1][4] = 8;
    test[1][7] = 2;

    test[2][4] = 5;
    test[2][6] = 6;
    test[2][8] = 9;

    test[3][2] = 8;
    test[3][4] = 6;
    test[3][8] = 3;

    test[4][1] = 2;
    test[4][3] = 7;
    test[4][4] = 1;
    test[4][5] = 3;
    test[4][7] = 5;

    test[5][0] = 3;
    test[5][4] = 2;
    test[5][6] = 9;

    test[6][0] = 6;
    test[6][2] = 3;
    test[6][4] = 7;

    test[7][1] = 8;
    test[7][4] = 4;
    test[7][7] = 9;
    
    test[8][0] = 5;
    test[8][3] = 8;
    test[8][8] = 6;
    Sudoku sudoku = new Sudoku(test);
    sudoku.solve();
    
    assertEquals(sudoku.isSolved(), true);
  }

  @Test
  void solvableSudokuShouldBeSolved2() {
    int[][] test = new int[9][9];
      for(int i = 0; i < 9; i++){
        for(int j = 0; j < 9; j++){
          test[i][j] = 0;
        }
    }
    //another solvable sudoku
    test[0][0] = 8;
    test[1][2] = 3;
    test[1][3] = 6;

    test[2][1] = 7;
    test[2][4] = 9;
    test[2][6] = 2;
    
    test[3][1] = 5;
    test[3][5] = 7;

    test[4][4] = 4;
    test[4][5] = 5;
    test[4][6] = 7;

    test[5][3] = 1;
    test[5][7] = 3;

    test[6][2] = 1;
    test[6][7] = 6;
    test[6][8] = 8;

    test[7][2] = 8;
    test[7][3] = 5;
    test[7][7] = 1;

    test[8][1] = 9;
    test[8][6] = 4;
    
    Sudoku sudoku = new Sudoku(test);
    sudoku.solve();
    
    assertEquals(sudoku.isSolved(), true);
  }

  @Test
  void unsolvableSudokuShouldNotBeSolved() {
    int[][] test = new int[9][9];
      for(int i = 0; i < 9; i++){
        for(int j = 0; j < 9; j++){
          test[i][j] = 0;
        }
    }
    //unsolvable sudoku
    test[0][0] = 7;
    test[0][5] = 4;
    test[0][8] = 8;

    test[1][1] = 3;
    test[1][4] = 8;
    test[1][7] = 2;

    test[2][4] = 5;
    test[2][6] = 6;
    test[2][8] = 9;

    test[3][2] = 8;
    test[3][4] = 6;
    test[3][8] = 3;

    test[4][1] = 2;
    test[4][3] = 7;
    test[4][4] = 1;
    test[4][5] = 3;
    test[4][7] = 5;

    test[5][0] = 3;
    test[5][4] = 2;
    test[5][6] = 9;

    test[6][0] = 6;
    test[6][2] = 3;
    test[6][4] = 7;

    test[7][1] = 8;
    test[7][4] = 4;
    test[7][6] = 9;
    
    test[8][0] = 5;
    test[8][3] = 8;
    test[8][8] = 6;
    Sudoku sudoku = new Sudoku(test);
    sudoku.solve();
    
    assertEquals(sudoku.isSolved(), false);
  }
  @Test
  void unsolvableSudokuShouldNotBeSolved2() {
    int[][] test = new int[9][9];
      for(int i = 0; i < 9; i++){
        for(int j = 0; j < 9; j++){
          test[i][j] = 0;
        }
    }
    //another unsolvable sudoku
    test[0][0] = 1;
    test[0][1] = 1;
    
    Sudoku sudoku = new Sudoku(test);
    sudoku.solve();
    
    assertEquals(sudoku.isSolved(), false);
  }
}

    