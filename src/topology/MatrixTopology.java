package topology;


import java.util.ArrayList;
import java.util.TreeMap;


public class MatrixTopology {
    private int colNumber, rowNumber;
    private int radius = 0;
    public MatrixTopology(int row, int col) {
        this.rowNumber = row;
        this.colNumber = col;
    }

    public MatrixTopology(int row, int col, int radius) {
        this(row, col);
        this.radius = radius;
    }
    public String toString() {
        ArrayList tempList = new ArrayList();
        String    conn     = "";

        for (int i = 1; i < colNumber * rowNumber + 1; i++) {
            tempList = getConnectedNeurons(i);
            conn += "Neuron number " + i + " is connected with: " + tempList + "\n";
        }
        return conn;
    }

    public int getColNumber() {
        return this.colNumber;
    }
    public ArrayList getConnectedNeurons(int neuronNumber) {
        ArrayList connectedNeurons = new ArrayList();

        if ((neuronNumber < colNumber * rowNumber) && (neuronNumber > 0)){
            if (neuronNumber - colNumber > 0) {
                connectedNeurons.add(neuronNumber - colNumber);
            }

            if ((neuronNumber - 1 > 0) && ((neuronNumber % colNumber) != 1)) {
                connectedNeurons.add(neuronNumber - 1);
            }

            if ((neuronNumber + 1 <= colNumber * rowNumber)
                    && ((neuronNumber % colNumber) != 0)) {
                connectedNeurons.add(neuronNumber + 1);
            }

            if (neuronNumber + colNumber <= colNumber * rowNumber) {
                connectedNeurons.add(neuronNumber + colNumber);
            }
        }
      return connectedNeurons;   
    }

    
    private ArrayList getN(ArrayList tempConnection) {
        ArrayList neighborgoodConn = new ArrayList();
        ArrayList tempConn         = new ArrayList();

        for (int j = 0; j < tempConnection.size(); j++) {
            tempConn = getConnectedNeurons((java.lang.Integer)tempConnection.get(j));
            for (int i = 0; i < tempConn.size(); i++) {
                neighborgoodConn.add(tempConn.get(i));
            }
        }
        return neighborgoodConn;
    }

    
    public TreeMap getNeighbourhood(int neuronNumber) {
        TreeMap<java.lang.Integer, java.lang.Integer> neighbornhood =
            new TreeMap<java.lang.Integer, java.lang.Integer>();
        ArrayList tempConnection   = new ArrayList();
        ArrayList neighborgoodConn = new ArrayList();

        tempConnection.add(neuronNumber);

        int[] temp = null;
        int   key;

        for (int i = 0; i < radius; i++) {
            neighborgoodConn = getN(tempConnection);

            for (int k = 0; k < neighborgoodConn.size(); k++) {
                key = (java.lang.Integer) neighborgoodConn.get(k);

                if (!neighbornhood.containsKey(key) && (key != neuronNumber)) {
                    neighbornhood.put(key, i + 1);
                }
            }

            tempConnection = (java.util.ArrayList) neighborgoodConn.clone();
        }

        return neighbornhood;
    }

    public Coords getNeuronCoordinate(int neuronNumber) {
        int x = ((neuronNumber - 1) / colNumber) + 1;
        int y = neuronNumber - ((x - 1) * colNumber);

        return new Coords(x, y);
    }

    public int getNeuronNumber(Coords coords) {
        if ((coords.x < rowNumber) && (coords.y < colNumber)) {
            return (coords.x - 1) * colNumber + coords.y;
        }

        return -1;
    }

    public int getNumbersOfNeurons() {
        return colNumber * rowNumber;
    }

    public int getRadius() {
        return radius;
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}