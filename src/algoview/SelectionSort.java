package algoview;

public class SelectionSort {

    private int[] array;
    private int array_index, compare_index, min_index;

    private boolean findMin;

    public SelectionSort(int[] array) {
        this.array = array;
        array_index = 0;
        compare_index = 1;
        min_index = 0;
        findMin = false;
    }

    public void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public int[] sortOnlyOneItem() {
        if(!findMin){
            min_index = array_index;
            findMin = true;
        }

        if(array[compare_index] < array[min_index]) { //efetuar comparação entre o menor valor e o valor de comparação
            min_index = compare_index;
        }
        compare_index++;

        if(compare_index >= array.length){ //aplicar a troca quando chega ao fim da array
            swap(array, min_index, array_index);
            array_index++;
            compare_index = array_index + 1;
            findMin = false;
        }

        return array;
    }

    public int[] getArray() {
        return array;
    }

    public Boolean getFindMin(){
        return findMin;
    }

    public void setFindMin(Boolean findMin){
        this.findMin = findMin;
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

    public int getMinIndex() {
        return min_index;
    }

    public void setMinIndex(int min_idex) {
        this.min_index = min_idex;
    }
}
