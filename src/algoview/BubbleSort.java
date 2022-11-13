package algoview;

public class BubbleSort {

    private int[] array;
    private int array_index, compare_index;

    public BubbleSort(int[] array) {
        this.array = array;
        array_index = 0;
        compare_index = Integer.MAX_VALUE;
    }

    public int[] sortOnlyOneItem() {

        if (array[compare_index] > array[compare_index+1]) {
            int temp = array[compare_index];
            array[compare_index] = array[compare_index+1];
            array[compare_index+1] = temp;
        }

        if ((compare_index+1) >= (array.length - array_index - 1)) {
            array_index++;
            compare_index = 0;
        }

        else compare_index++;

        return array;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public int getArrayIndex() {
        return array_index;
    }

    public void setArrayIndex(int array_index) {
        this.array_index = array_index;
    }

    public int getCompareIndex() {
        return compare_index;
    }

    public void setCompareIndex(int compare_index) {
        this.compare_index = compare_index;
    }
}
