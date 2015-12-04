
public class Row implements Comparable<Row> {

    private String[] rowElements;
    private int numOfDims;

    public Row(String rowString) {
        if (rowString != null) {
            setRowElements(rowString.split(" "));
        } else {
            System.out.println("Null Reference in Row(String) constructor.");
        }
    }

    public Row(Row row) {
        if (row != null && row.rowElements != null) {
            String[] temp = new String[row.rowElements.length];
            for (int i = 0; i < row.rowElements.length; i++) {
                temp[i] = new String(row.rowElements[i]);
            }
            setRowElements(temp);
        } else {
            System.out.println("Null Reference in Row(Row) constructor.");
        }
    }

    public String[] getRowElements() {
        return rowElements;
    }

    public void setRowElements(String[] rowElements) {
        this.rowElements = rowElements;
    }

    public int getNumOfDims() {
        return numOfDims;
    }

    public void setNumOfDims(int numOfDims) {
        this.numOfDims = numOfDims;
    }

    @Override
    public int compareTo(Row row) {
        for (int i = 0; i < HW2.numOfDimsStatic; i++) {
            if (Double.compare(getAsDouble(rowElements[i]), getAsDouble(row.getRowElements()[i])) > 0) {
                return 1;
            } else if (Double.compare(getAsDouble(rowElements[i]), getAsDouble(row.getRowElements()[i])) < 0) {
                return -1;
            }
        }
        return 0;

    }

    public double getAsDouble(String str) {
        return Double.parseDouble(str);
    }

    @Override
    public String toString() {
        String temp = "";
        if (rowElements != null) {
            for (int i = 0; i < this.rowElements.length; i++) {
                temp += rowElements[i] + " ";
            }
        }
        temp += "\n";
        return temp;
    }
}
