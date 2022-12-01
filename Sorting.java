// Written by: Charlie Alpert

public class Sorting {

    private final int size, range; //size being number of element, range being greatest value in array
    private int[] array;

    public Sorting(){
        size = 50;
        range = 100000; //when no range default to 100000
        array = new int[size];
    }

    public Sorting(int s){
        size = s; //get size from object
        range = 100000; //when no range default to 100000
        array = new int[size];
    }

    public Sorting(int s, int r){
        size = s; //get size from object
        range = r; //range from object
        array = new int[size];
    }

    //creates the array of random elements
    public void randomArray(){
        for(int i = 0; i < size; i++){ //for the size of the array
            int insert = (int) (Math.random() * range); //create random value between 0 and range
            array[i] = insert; //put random value in array
        }
    }

    // iterates through array and prints every element
    public void print(){
        for(int element: array){
            System.out.print(element + " ");
        }
        System.out.println();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void insertionSort(){
        int j;
        for(int i = 1; i < size; i++){ //iterates entire array
            int value = array[i]; //temp holder for array at index
            for(j = i; j > 0 && value < array[j-1]; j--){
                array[j] = array[j-1]; //when less than they swap
            }
            array[j] = value; //replace with temp
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //main call for heapSort
    public void heapSort(){
        for(int i = size / 2 - 1; i >= 0; i--) { //starts half way through array
            buildHeap(array, size, i); //builds heap
        }

        for(int i = size - 1; i > 0; i--) {
            int temp = array[0]; //temp to hold value
            array[0] = array[i]; //replace 0 index value
            array[i] = temp; //finish swap store temp

            buildHeap(array, i, 0);
        }
    }

    public void buildHeap(int array[], int size, int i)
    {
        int largest = i;
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;

        //left child > root
        if (leftChild < size && array[leftChild] > array[largest])
            largest = leftChild;

        //right child > largest so far
        if (rightChild < size && array[rightChild] > array[largest])
            largest = rightChild;

        //largest does not equal root
        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;

            //heapify
            buildHeap(array, size, largest);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //main call for mergeSort
    public void mergeSort(){ //calls mergeSort methods
        merge1(array, 0, size - 1); //main merger method (start is 0 and end is at the back of array)
    }

    public void merge1(int arr[], int start, int end){
        if(start < end){ //does not run when start is passed or equal to end
            int mid = (start + end) / 2; //middle value of array
            merge1(arr, start, mid); //recursively calls from start to middle
            merge1(arr, mid + 1, end); //recursively calls from middle to end
            merge2(arr, start, mid, end); //calls merger with entire array
        }
    }

    public void merge2(int arr[], int start, int mid, int end){
        int temp[] = new int[end - start + 1]; //creates temp array
        int i = start; //index for beginning
        int j = mid + 1; //index for middle
        int k = 0;

        //iterate arrays and in each iteration add smaller of both elements in temp
        while(i <= mid && j <= end){
            if(arr[i] <= arr[j]){
                temp[k] = arr[i];
                k++;
                i++;
            }else {
                temp[k] = arr[j];
                k++;
                j++;
            }
        }
        //add elements left in the first interval
        while(i <= mid){
            temp[k] = arr[i];
            k++;
            i++;
        }
        //add elements left in the second interval
        while(j <= end){
            temp[k] = arr[j];
            k++;
            j++;
        }
        //copy temp to original interval
        for(i = start; i <= end; i++){
            arr[i] = temp[i - start];
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //without cutoff
    public void quickSort() {
        quickSort(array, 0, array.length - 1);
    }

    //without cutoff (recursive)
    public void quickSort(int[] array, int left, int right){
        if (left < right) {
            int partition = partitionArray(array, left, right); //index to partition at
            quickSort(array, left, partition - 1); //quick sort first part
            quickSort(array, partition + 1, right); //quick sort second part
        }
    }

    //with cutoff
    public void quickSort(int cutoff) {
        quickSort(array, 0, array.length - 1, cutoff);
    }

    //with cutoff (recursive)
    public void quickSort(int[] array, int left, int right, int cutoffSize){
        if ((right - left + 1) <= cutoffSize) {
            insertionSort(array, left, right);
        }else {
            int median = medianOf3(array, left, right);
            int partition = partitionArray(array, left, right, median); //index to partition at
            quickSort(array, left, partition - 1, cutoffSize); //quick sort first part
            quickSort(array, partition + 1, right, cutoffSize); //quick sort second part
        }
    }

    public int medianOf3(int[] array, int left, int right){
        int center = (left + right) / 2; //value between left and right
        if(array[left] > array[center]){
            swap(array, left, center); //swaps left and center if center is less than left
        }
        if(array[left] > array[right]) {
            swap(array, left, right); //swaps left and right if right is less than left
        }
        if(array[center] > array[right]) {
            swap(array, center, right); //swaps right and center if right is less than center
        }

        swap(array, center, right - 1);
        return array[right - 1];

    }

    public void swap(int[] array, int index1, int index2){
        int temp = array[index1]; //saves element at index 1
        array[index1] = array[index2]; //replaces element at index 1 with element at index 2
        array[index2] = temp; //saves index 1 element at index 2
    }

    // partition without cutoff
    public int partitionArray(int[] array, int left, int right) {
        int pivot = array[right]; //pivot is element at right
        int i = (left - 1); //index of smaller element
        for (int j = left; j <= right - 1; j++) {
            if (array[j] < pivot) { //if current element is smaller than pivot
                i++; //increment index of smaller element
                swap(array, i, j);
            }
        }
        swap(array, i + 1, right);
        return (i + 1);
    }

    // partition with cutoff
    public int partitionArray(int[] array, int left, int right, int pivot){
        int leftPtr = left;
        int rightPtr = right - 1;
        while(true){
            while(array[++leftPtr] < pivot); //increments left index until it is greater or equal to pivot
            while(array[--rightPtr] > pivot); //decrements right index until it is less than or equal to pivot
            if(leftPtr >= rightPtr){
                break;
            }
            else {
                swap(array, leftPtr, rightPtr);
            }
        }
        swap(array, leftPtr, right - 1);
        return leftPtr;
    }

    private void insertionSort(int[] array, int left, int right) {
        for(int a = left + 1; a <= right; a++){ //iterates from left to right
            for(int b = a - 1; b >= left; b--){
                if(array[b] <= array[b + 1]){  //exits when values are in correct order
                    break;
                }else { //swap when they are not in correct order
                    int tempValue = array[b];
                    array[b] = array[b + 1];
                    array[b + 1] = tempValue;
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        Sorting sort = new Sorting(50, 10000); //change size for 50, 500, 1000, 2000, 5000, and 10000

        //insertion sort
        sort.randomArray(); //create the random array
        long startTime = System.nanoTime(); //tracks start time of sort
        sort.insertionSort();
        long endTime = System.nanoTime(); //records end time of sort
        long duration = (endTime - startTime); //calculates total sort time
        sort.print();
        System.out.println("Insertion Sort: " + duration + " nanoseconds\n\n");


        //heap sort
        sort.randomArray(); //create the random array
        startTime = System.nanoTime(); //tracks start time of sort
        sort.heapSort();
        endTime = System.nanoTime(); //records end time of sort
        duration = (endTime - startTime); //calculates total sort time
        sort.print();
        System.out.println("Heap Sort: " + duration + " nanoseconds\n\n");


        //merge sort
        sort.randomArray(); //create the random array
        startTime = System.nanoTime(); //tracks start time of sort
        sort.mergeSort();
        endTime = System.nanoTime(); //records end time of sort
        duration = (endTime - startTime); //calculates total sort time
        sort.print();
        System.out.println("Merge Sort: " + duration + " nanoseconds\n\n");


        //quick sort (no cutoff)
        sort.randomArray(); //create the random array
        startTime = System.nanoTime(); //tracks start time of sort
        sort.quickSort(); //call without cutoff
        endTime = System.nanoTime(); //records end time of sort
        duration = (endTime - startTime); //calculates total sort time
        sort.print();
        System.out.println("Quick Sort (w/o cutoff): " + duration + " nanoseconds\n\n");


        //quick sort (cutoff: 10)
        sort.randomArray(); //create the random array
        startTime = System.nanoTime(); //tracks start time of sort
        sort.quickSort(10); //call with cutoff = 10
        endTime = System.nanoTime(); //records end time of sort
        duration = (endTime - startTime); //calculates total sort time
        sort.print();
        System.out.println("Quick Sort (cutoff: 10): " + duration + " nanoseconds\n\n");


        //quick sort (cutoff: 50)
        sort.randomArray(); //create the random array
        startTime = System.nanoTime(); //tracks start time of sort
        sort.quickSort(50); //call with cutoff = 50
        endTime = System.nanoTime(); //records end time of sort
        duration = (endTime - startTime); //calculates total sort time
        sort.print();
        System.out.println("Quick Sort (cutoff: 50): " + duration + " nanoseconds\n\n");


        //quick sort (cutoff: 200)
        sort.randomArray(); //create the random array
        startTime = System.nanoTime(); //tracks start time of sort
        sort.quickSort(200); //call with cutoff = 200
        endTime = System.nanoTime(); //records end time of sort
        duration = (endTime - startTime); //calculates total sort time
        sort.print();
        System.out.println("Quick Sort (cutoff: 200): " + duration + " nanoseconds\n\n");
    }
}
