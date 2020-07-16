package sudoku_solver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class Sudoku {
  List<List<List<Integer>>> sudoku = new ArrayList<List<List<Integer>>>(9);
  /**
   * create Sudoku from 2D int,
   * where empty fields should be filled with 0s
   * @param initialBoard
   */
  public Sudoku(int[][] initialBoard){
    for(int i = 0; i < 9; i++){
      sudoku.add(new ArrayList<List<Integer>>(9));
      for(int j = 0; j < 9; j++){
        sudoku.get(i).add(new ArrayList<Integer>(9));
        if(initialBoard[i][j] == 0){
          sudoku.get(i).get(j).addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        }
        else{
          sudoku.get(i).get(j).add(initialBoard[i][j]);
        }
      }
    }
    initialDeduction();
  }
  /**
   * create Sudoku from List of List of List of Integers,
   * where the first two lists are row and column and 
   * the 3rd stands for alist of all possible Integers that can be assigned to a field
   * @param initialBoard
   */
  public Sudoku(List<List<List<Integer>>> initialBoard){
    for(int i = 0; i < 9; i++){
      sudoku.add(new ArrayList<List<Integer>>(9));
      for(int j = 0; j < 9; j++){
        sudoku.get(i).add(new ArrayList<Integer>(initialBoard.get(i).get(j)));
      }
    }
    initialDeduction();
  }

  private void setField(Integer field, int row, int column){
    sudoku.get(row).get(column).clear();
    sudoku.get(row).get(column).addAll(Arrays.asList(field));
  }

  public List<List<List<Integer>>> getSudoku(){
    return sudoku;
  }

  /**
   * returns coordinates: row, column
   * of a field with least options,
   * ignores trivial fields, which contain a single integer
   * if there are no branching candidates - all fields contain a single integer,
   * then it returns 0, 0
   */
  private int[] bestBranchingCandidate(){
    int row = 0;
    int column = 0;
    int min = Integer.MAX_VALUE;
    for(int i = 0; i < 9; i++){
      for(int j = 0; j < 9; j++){
        if(sudoku.get(i).get(j).size() < min && sudoku.get(i).get(j).size() > 1){
          min = sudoku.get(i).get(j).size();
          row = i;
          column = j;
        }
      }
    }
    return new int[]{row, column};
  }

  /**
   * given a list of fields, returns true if there are no two fields of size 1 containing the same number
   * @param fields 
   */
  private boolean checkUniqueness(List<List<Integer>> fields){
    Set<List<Integer>> aux = new HashSet<List<Integer>>();
    int counter = 0;

    for(List<Integer> field : fields){
      if(field.size() == 1){
        aux.add(field);
        counter++;
      }
    }
    return aux.size() == counter;
  }

  /**
   * check uniqueness for each row, column and 3x3 region
   */
  private boolean isValid(){
    boolean isValid = true;
    for(List<List<Integer>> row : sudoku){
      isValid = isValid && checkUniqueness(row);
    }
    for(int i = 0; i < 9; i++){
      List<List<Integer>> column = new ArrayList<List<Integer>>();
      for(int j = 0; j < 9; j++){
        column.add(sudoku.get(j).get(i));
      }
      isValid = isValid && checkUniqueness(column);
    }
    for(int i = 0; i < 9; i+=3){
      for(int j = 0; j < 9; j+=3){
        List<List<Integer>> region = new ArrayList<List<Integer>>();
        for(int k = 0; k < 3; k++){
          for(int l = 0; l < 3; l++){
            region.add(sudoku.get(i+k).get(j+l));
          }
        }
        isValid = isValid && checkUniqueness(region);
      }
    }
    return isValid;
  }
  /**
   * checks if there is only one 'integer option' for each field
   */
  public boolean isSolved(){
    if(!this.isValid()){
      return false;
    }
    boolean solved = true;
    for(int i = 0; i < 9; i ++){
      for(int j = 0; j < 9; j++){
        solved = solved && (sudoku.get(i).get(j).size() == 1);
      }
    }
    return solved;
  }
  /**
   * calling runDeduction for each 'single integer field' of sudoku
   */
  private void initialDeduction(){
    for(int i = 0; i < 9; i++){
      for(int j = 0; j < 9; j++){
        if(sudoku.get(i).get(j).size() == 1){
          runDeduction(i, j);
        }
      }
    }
  }

  /**
   * recursively deduces all possible guesses from a single field using sudoku's rule that
   * a number can appear only once in each row, column and region
   * @param row
   * @param column
   */
  private void runDeduction(int row, int column){
    //rows - iterate horizontal
    Integer number = sudoku.get(row).get(column).get(0);
    for(int i = 0; i < 9; i++){
      if(sudoku.get(row).get(i).contains(number) && sudoku.get(row).get(i).size() > 1){
        sudoku.get(row).get(i).remove(number);
        //a new deduction is run recursively if this deduction creates a field with one number
        if(sudoku.get(row).get(i).size() == 1){
          runDeduction(row, i);
        }
      }
    }
    //colums
    for(int i = 0; i < 9; i++){
      if(sudoku.get(i).get(column).contains(number) && sudoku.get(i).get(column).size() > 1){
        sudoku.get(i).get(column).remove(number);
        //a new deduction is run recursively if this deduction creates a field with one number
        if(sudoku.get(i).get(column).size() == 1){
          runDeduction(i, column);
        }
      }
    }
    //regions
    int regionX = region(row, column)[0];
    int regionY = region(row, column)[1];
    for(int i = regionX; i < regionX+3; i++){
      for(int j = regionY; j < regionY+3; j++){
        if(sudoku.get(i).get(j).contains(number) && sudoku.get(i).get(j).size() > 1){
          sudoku.get(i).get(j).remove(number);
          //a new deduction is run recursively if this deduction creates a field with one number
          if(sudoku.get(i).get(j).size() == 1){
            runDeduction(i, j);
          }
        }
      }
    }

  }

  /**
  @param row region's row
  @param column region's column
  @return coordinates of region's top-left corner
  */
  private int[] region(int row, int column){

    return new int[]{(row/3)*3, (column/3)*3};
  }

  public Sudoku solve(){
    if(this.isSolved()){
    return this;
    }
    //find a field with least options and explore each of them
    int[] coordinates = bestBranchingCandidate();
    List<Integer> branchingPoint = sudoku.get(coordinates[0]).get(coordinates[1]);
    //System.out.println(branchingPoint);
    for(Integer option : branchingPoint){
      //System.out.println(option);
      Sudoku branch = new Sudoku(sudoku);
      branch.setField(option, coordinates[0], coordinates[1]);
      branch.runDeduction(coordinates[0], coordinates[1]);
      //if there are no repeated integers then explore this branch
      if(branch.isValid()){
        Sudoku result = branch.solve();
        if(result.isSolved()){
          this.sudoku = result.getSudoku();
          return result;
        }
      }
    }
    //find best branching
    //all possibilities, run deduction for that point, check  uniq
    return this;
  }


  public static void main(String[] args) {
    int[][] test = new int[9][9];
    for(int i = 0; i < 9; i++){
      for(int j = 0; j < 9; j++){
        test[i][j] = 0;
      }
    }
    //sample sudoku puzzle
    /*
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
    test[7][7] = 9;//7,6 case/// 7,7 initial
    
    test[8][0] = 5;
    test[8][3] = 8;
    test[8][8] = 6;
    */

//another sudoku test

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
    
    Sudoku t1 = new Sudoku(test);
    Sudoku t2 = t1.solve();
    System.out.println(t1.isValid());
    System.out.println(t2.getSudoku());
  }
}
