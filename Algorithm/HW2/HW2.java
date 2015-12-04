public class HW2 {

    public static int numOfDimsStatic;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String inputFileName = "";
        HW2.numOfDimsStatic = 3;
        String sortOrder = "";
        String outputFileName;
        String sortingAlgorithm = "";

        if (args.length >= 5) {

            inputFileName = args[0];
            HW2.numOfDimsStatic = Integer.parseInt(args[1]);
            sortOrder = args[2];
            outputFileName = args[3];
            sortingAlgorithm = args[4];

            FileProcess fileProcess = new FileProcess();
            FileProcess.FileInfo fileInfo = fileProcess.getFileInfo(inputFileName);
            Row[] rows = fileProcess.getRows(fileInfo.getInfo(), HW2.numOfDimsStatic);

            if (sortingAlgorithm.equals("mergesort")) {
                double sec = System.currentTimeMillis();
                Sorting.mergeSort(rows, 0, rows.length - 1);
                sec = System.currentTimeMillis() - sec;
                System.out.println(sec / 1000.0 + " second elapsed. (" + sec + " milisecond)");
            } else if (sortingAlgorithm.equals("quicksort")) {
                double sec = System.currentTimeMillis();
                Sorting.quickSort(rows, 0, rows.length - 1);
                sec = System.currentTimeMillis() - sec;
                System.out.println(sec / 1000.0 + " second elapsed. (" + sec + " milisecond)");
            }

            if (sortOrder.equals("desc")) {
                rows = Sorting.reverseArray(rows);
            }

            if (!fileProcess.writeOutputToFile(rows, outputFileName)) {
                System.out.println("An error occured, the output could not be written to the file.");
            }

        } else {
            System.out.println("Missing parameters.. length of args is : " + args.length);
        }

    }

}
