
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileProcess {

    public FileInfo getFileInfo(String fileName) {
        FileInfo fileInfo = new FileInfo();
        File file = new File(fileName);
        Scanner scanFile = null;
        String temp = "";
        String[] tempArray = null;
        try {
            scanFile = new Scanner(file);
            if (scanFile.hasNextLine()) {
                temp = scanFile.nextLine();
                tempArray = temp.split(" ");
                fileInfo.setLineNumber(Integer.parseInt(tempArray[0]));
                fileInfo.setFeatureNumber(Integer.parseInt(tempArray[1]));
                String[] feat = new String[tempArray.length - 2];

                for (int i = 0; i < feat.length; i++) {
                    feat[i] = tempArray[i + 2];
                }
                fileInfo.setFeautureTypes(feat);
            }
            tempArray = new String[fileInfo.getLineNumber()];
            int count = 0;
            while (scanFile.hasNextLine()) {
                if (count < tempArray.length) {
                    tempArray[count++] = scanFile.nextLine();
                }
            }
            fileInfo.setInfo(tempArray);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (Exception ex) {
            System.out.println("Text file is not in the expected form.");
        }
        return fileInfo;
    }

    public Row[] getRows(String[] info, int numOfDims) {

        Row[] rows = new Row[info.length];
        for (int i = 0; i < info.length; i++) {
            rows[i] = new Row(info[i]);
            rows[i].setNumOfDims(HW2.numOfDimsStatic);
        }
        return rows;
    }

    public boolean writeOutputToFile(Row[] rows, String outputFileName) {
        File file = new File(outputFileName);
        try {
            PrintWriter pw = new PrintWriter(file);
            for (int i = 0; i < rows.length; i++) {
                pw.println(rows[i]);
            }

            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileProcess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    public class FileInfo {

        private int lineNumber;
        private int featureNumber;
        private String[] feautureTypes;
        private String[] info;

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public int getFeatureNumber() {
            return featureNumber;
        }

        public void setFeatureNumber(int featureNumber) {
            this.featureNumber = featureNumber;
        }

        public String[] getFeautureTypes() {
            return feautureTypes;
        }

        public void setFeautureTypes(String[] feautureTypes) {
            this.feautureTypes = feautureTypes;
        }

        public String[] getInfo() {
            return info;
        }

        public void setInfo(String[] info) {
            this.info = info;
        }

    }
}
