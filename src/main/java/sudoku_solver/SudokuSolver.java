package sudoku_solver;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.util.Arrays;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class SudokuSolver extends JFrame {

  JPanel contentPane;
  JPanel sudokuBoard;
  JPanel buttons;

  private class SolveListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      Sudoku sudoku = new Sudoku(getInput());
      sudoku.solve();
      if(sudoku.isSolved()){
        displaySudoku(sudoku.getSudoku());
      }
      else{
        showMessageDialog(null, "There is no solution");
      }
    }
  }
  
  private class ClearListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      for(Component region: sudokuBoard.getComponents()){
        for(Component field :(((JPanel)region).getComponents())){
          ((JTextField)field).setText("");
        }
      }
    }
	}


	public SudokuSolver() {
		setTitle("Sudoku Solver");
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
    contentPane = (JPanel) getContentPane();
    contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		
    //sudoku board containing 9 regions
    sudokuBoard = new JPanel();
    sudokuBoard.setLayout(new GridLayout(3,3,5,5));
    //add fields grouped into regions
    for(int i = 0; i < 9; i++){
      JPanel region = new JPanel();
      region.setLayout(new GridLayout(3,3));
      for(int j = 0; j < 9; j++){
        JTextField textField = new JTextField();
        //JTextField that stores to more than 1 digit  
        textField.setDocument(new JTextFieldLimit(1, Arrays.asList('1','2','3','4','5','6','7','8','9')));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        //textField.setText(Integer.toString(j+1));
        region.add(textField);
      }
      region.setBorder(BorderFactory.createLineBorder(Color.black, 2));
      sudokuBoard.add(region);
    }
    contentPane.add(sudokuBoard);

    //adding buttons
    buttons = new JPanel();
    buttons.setLayout(new FlowLayout());
    buttons.setBorder(new EmptyBorder(20, 20, 20, 20));

    JButton solve = new JButton("Solve");
    solve.addActionListener(new SolveListener());
    buttons.add(solve);

    JButton clear = new JButton("Clear");
    clear.addActionListener(new ClearListener());
    buttons.add(clear);  

    contentPane.add(buttons, BorderLayout.SOUTH);

		setVisible(true);
  }
  
  int[][] getInput(){
    int[][] input = new int[9][9];
    Component[] regions = sudokuBoard.getComponents();
    for(int i = 0; i < 9; i++){
      for(int j = 0; j < 3; j++){
        for(int k = 0; k < 3; k++){
          String textFieldInput = (((JTextField) ((JPanel)regions[i]).getComponent(3*j+k)).getText());
          if(textFieldInput.isEmpty() || textFieldInput == null){
            input[3*(i/3)+j][3*(i%3)+k] = 0;
            //System.out.println(Integer.toString(3*(i/3)+j)+ Integer.toString(3*(i%3)+k));
          }
          else{
            input[3*(i/3)+j][3*(i%3)+k] = Integer.parseInt(textFieldInput);
          }
        }
      }
    }
    return input;
  }

  void displaySudoku(List<List<List<Integer>>> result){
    Component[] regions = sudokuBoard.getComponents();
    for(int i = 0; i < 9; i++){
      for(int j = 0; j < 3; j++){
        for(int k = 0; k < 3; k++){
          (((JTextField) ((JPanel)regions[i]).getComponent(3*j+k))).setText(Integer.toString(result.get(3*(i/3)+j).get(3*(i%3)+k).get(0)));
        }
      }
    }
  }

	public static void main(String[] args) {
    SudokuSolver test = new SudokuSolver();
	}
}