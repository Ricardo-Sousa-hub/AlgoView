package algoview;

public class QuickSort {

    private int[] array, stack;
    private int low, high, sp, array_index, compare_index, x, partition;
    private boolean isPartioning = false;

    public QuickSort(int[] array) {
        this.array = array;
        stack = new int[array.length];
        low = 0;
        high = array.length-1;
        System.out.println(high);
        array_index = 0;
        compare_index = 0;
        x = 0;
        partition = -1;
        sp = -1;
        stack[++sp] = 0;
        stack[++sp] = array.length-1;
    }

    public void swap(int arr[], int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public int[] sortOnlyOneItem() {

        // keep popping elements until stack is not empty
        if (sp >= 0) {

            if (!isPartioning) {
                high = stack[sp--];
                low = stack[sp];
                array_index = low - 1;
                x = array[high];
                compare_index = low;
                isPartioning = true;
            }

            // set pivot element at it's proper position

            if (compare_index <= high - 1) {

                if (array[compare_index] <= x) {
                    array_index++;
                    swap(array, array_index, compare_index);
                }

                compare_index++;
                return array;
            }

            swap(array, array_index + 1, high);

            partition = array_index + 1;

            sp--;

            // If there are elements on left side of pivot,
            // then push left side to stack
            if (partition - 1 > low) {
                stack[++sp] = low;
                stack[++sp] = partition - 1;
            }

            // If there are elements on right side of pivot,
            // then push right side to stack
            if (partition + 1 < high) {
                stack[++sp] = partition + 1;
                stack[++sp] = high;
            }

            isPartioning = false;
        }

        return array;
    }

    public void push(int val) {
        sp++;
        stack[sp] = val;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
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

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public int getSP() {
        return sp;
    }

    public void setSP(int sp) {
        this.sp = sp;
    }

    public boolean getIsPartitioning() {
        return isPartioning;
    }

    public void setIsPartioning(boolean isPartioning) {
        this.isPartioning = isPartioning;
    }

}
