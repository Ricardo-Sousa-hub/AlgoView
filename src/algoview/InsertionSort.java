package algoview;

public class InsertionSort {
    private int[] array;
    private int array_index, compare_index, key;

    private boolean startOfIteration = false;

    public InsertionSort(int[] array) {
        this.array = array;
        array_index = 1;
    }

    public int[] sortOnlyOneItem(){
        if(!startOfIteration){
            key = array[array_index];
            compare_index = array_index - 1;
            startOfIteration = true;
        }

        if(compare_index >= 0 && array[compare_index] > key){
            array[compare_index + 1] = array[compare_index];
            compare_index--;
            return array;
        }else{
            array[compare_index+1] = key;
            array_index++;
        }

        startOfIteration = false;
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

    public boolean getStartOfIteration() {
        return startOfIteration;
    }

    public void setStartOfIteration(boolean startOfIteration) {
        this.startOfIteration = startOfIteration;
    }
}
