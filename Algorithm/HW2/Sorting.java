public class Sorting {

    public static void mergeSort(Row[] array, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(array, p, q);
            mergeSort(array, q + 1, r);
            merge(array, p, q, r);
        }
    }

    public static void merge(Row[] A, int p, int q, int r) {
        System.out.println(r - p + 1 + " elements were merged.");
        int i = 0;
        int j = 0;
        int n1 = q - p + 1;
        int n2 = r - q;

        Row[] L = new Row[n1];
        Row[] R = new Row[n2];

        for (; i < n1; i++) {
            L[i] = A[p + i];
        }
        for (; j < n2; j++) {
            R[j] = A[q + j + 1];
        }
        i = j = 0;
        for (int k = p; k <= r; k++) {

            if (j >= n2 || (i < n1 && L[i].compareTo(R[j]) < 0)) {
                A[k] = L[i++];
            } else {
                A[k] = R[j++];
            }
        }
    }

    public static void quickSort(Row[] array, int p, int r) {
        System.out.println("Pivot: " + p);
        if (p < r) {
            int q = partition(array, p, r);
            quickSort(array, p, q);
            quickSort(array, q + 1, r);
        }
    }

    public static int partition(Row[] array, int p, int r) {

        Row x = array[p];
        Row temp;
        int i = p;
        int j = r;

        while (true) {

            while (i < r && array[i].compareTo(x) < 0) {
                i++;
            }

            while (j > p && array[j].compareTo(x) > 0) {
                j--;
            }

            if (i < j) {
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            } else {
                return j;
            }
        }
    }

    public static Row[] reverseArray(Row[] array) {
        if (array.length <= 1) {
            return array;
        }

        Row[] tempArray = new Row[array.length];
        int index = array.length - 1;
        for (int i = 0; i < array.length; i++) {
            tempArray[index] = array[i];
            index--;
        }
        return tempArray;
    }
}
