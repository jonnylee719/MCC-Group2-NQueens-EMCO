package cs.ut.ee.algorithm;

import android.util.Log;

/**
 * NQueens algorithm implementation as found in: http://www.java.achchuthan.org/2012/02/n-queens-problem-in-java.html
 */
public class MecoNQueens {
    private int mSize;
    private int[] mQueens;
    private boolean mLogOutput;

    public MecoNQueens(int size, boolean logOutput) {
        mSize = size;
        mQueens = new int[size];
        mLogOutput = logOutput;
    }

    public void localesolve() {
        /**
          * Solve the NQueens problem locally
          *
        **/
        placeNqueens(0, mQueens.length);
    }

    private boolean canPlaceQueen(int row, int column) {
        /**
         * Returns TRUE if a queen can be placed in row r and column c.
         * Otherwise it returns FALSE. x[] is a global array whose first (r-1)
         * values have been set.
         */
        for (int i = 0; i < row; i++) {
            if (mQueens[i] == column || (i - row) == (mQueens[i] - column) ||(i - row) == (column - mQueens[i]))
            {
                return false;
            }
        }
        return true;
    }

    private void placeNqueens(int r, int n) {
        /**
         * Using backtracking this method prints all possible placements of n
         * queens on an n x n chessboard so that they are non-attacking.
         */
        for (int c = 0; c < n; c++) {
            if (canPlaceQueen(r, c)) {
                mQueens[r] = c;
                if (r == n - 1 && mLogOutput) {
                    printQueens();
                } else {
                    placeNqueens(r + 1, n);
                }
            }
        }
    }

    private void printQueens() {
        String solution = "";
        for (int column : mQueens) solution = solution + column + " ";
        Log.i("NQueens", solution);
    }

    public void solve() {
      /**
        * Offload solve method
        *
      **/

      Method toExecute;
      Class<?>[] paramTypes = null;
      Object[] paramValues = null;

      try {
        // Method to be offloaded
        // the solve method does not have any arguments,
        // therefore paramTypes and paramValues are both null
        toExecute = this.getClass().getDeclaredMethod("localesolve", paramTypes);
        Vector results = getCloudController().execute(toExecute,paramValues,this,this.getClass());
        if(results != null){
          copyState(results.get(1));
        } else {
          // default to solving the NQueens problem locally
          solve();
        }
      } catch (SecurityException se) {

      } catch (NoSuchMethodException ns){
      } catch (Throwable th){}
    }

    void copyState(Object state){
    	MecoNQueens localstate = (MecoNQueens) state;
    	this.mSize = localstate.mSize;
      this.mQueens = localstate.mQueens;
      this.mLogOutput = localstate.mLogOutput;
    }
}
