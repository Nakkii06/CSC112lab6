import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Main {
    //Method declarations
    public static void BubbleSort(ArrayList<Iris> a, int size){
        //declare varibales
        Iris i1, i2, temp;
        boolean swapped;

        //sort n^2 times with optimized
        for(int i=0; i<size-1; i++) {
            swapped = false;
            for(int j=0; j<size-1;j++){
                i1 = a.get(j);
                i2 = a.get(j+1);

                if(!i1.isLessThan(i2)){
                    temp = a.get(j);
                    a.set(j, i2);
                    a.set(j+1, temp);
                    swapped = true;
                }
            }
            if(!swapped)
                break;
        }
    }

    public static void mergeSort(ArrayList<Iris> a, ArrayList<Iris> tmp, int left, int right){
        //separate data recursively and sort
        if((left<right) && (right-left)>=1 ){
            int mid = (left + right-1)/2;
            mergeSort(a, tmp, left, mid);
            mergeSort(a, tmp, mid+1, right);

            mergeSortedLists(a, tmp, left , mid, right);
        }
    }

    public static void mergeSortedLists(ArrayList<Iris> a, ArrayList<Iris> tmp, int left, int middle, int right){

        int leftIndex = left;
        int rightIndex = middle+1;

        //compare each data from both side one by one
        while(leftIndex<=middle && rightIndex<=right){
            if(a.get(leftIndex).isLessThan(a.get(rightIndex))){
                tmp.add(a.get(leftIndex));
                leftIndex++;
            } else {
                tmp.add(a.get(rightIndex));
                rightIndex++;
            }
        }

        while(leftIndex<=middle){
            tmp.add(a.get(leftIndex));
            leftIndex++;
        }

        while(rightIndex<=right){
            tmp.add(a.get(rightIndex));
            rightIndex++;
        }

        //change the original arrayList
        int i=0;
        int j=left;
        while (i<tmp.size()) {
            a.set(j, tmp.get(i++));
            j++;
        }

        //clare the tmp workspace
        tmp.removeAll(tmp);
    }

    public static void main(String [] args) throws IOException {

        //declare variables
        ArrayList<Iris> list=new ArrayList<Iris>();   // list to be sorted
        ArrayList<Iris> tmp=new ArrayList<Iris>();   // temporary workspace
        Scanner fileReader = openInputFile();
        FileWriter csvWriter = new FileWriter("src/result.csv"); //for result output

        String[] irisArr;  //split data into String
        String iris;
        String title = fileReader.nextLine(); //skip the first line of the input file

        long startTime, endTime; //for time measurement
        int testSize = 100000;
        int i;

        //fill list
        for(int l=0; l<testSize; l++){
            iris = fileReader.nextLine();
            irisArr = iris.split(",");
            list.add(new Iris(Double.parseDouble(irisArr[0]), Double.parseDouble(irisArr[1]), Double.parseDouble(irisArr[2]), Double.parseDouble(irisArr[3]), irisArr[4]));
        }
        fileReader.close();

        //Create a copy from list for Bubble sort
        ArrayList <Iris> list2=new ArrayList<Iris>();
        for(i=0;i<list.size();i++)
            list2.add(list.get(i));

        // sort list using mergesort
        csvWriter.append("Merge sort\n");
        startTime = System.currentTimeMillis();
        mergeSort(list, tmp, 0, 1000-1);
        endTime = System.currentTimeMillis();
        csvWriter.append(1000 + "," + (endTime - startTime) + "\n");
        for(i=1;i<=10;i++) {
            startTime = System.currentTimeMillis();
            mergeSort(list, tmp, 0, 10000*i-1);
            endTime = System.currentTimeMillis();
            csvWriter.append(10000*i + "," + (endTime - startTime) + "\n");

        }
        csvWriter.append("\n");

        //sort list2 using Bubble sort
        csvWriter.append("Bubble sort\n");
        startTime = System.currentTimeMillis();
        BubbleSort(list2, 1000);
        endTime = System.currentTimeMillis();
        csvWriter.append(1000 + "," + (endTime - startTime) + "\n");

        for(i=1;i<=10;i++) {
            startTime = System.currentTimeMillis();
            BubbleSort(list2, 10000 * i);
            endTime = System.currentTimeMillis();
            csvWriter.append(10000*i + "," + (endTime - startTime) + "\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }

    public static Scanner openInputFile(){
        FileInputStream fileOpener = null;
        try{
            fileOpener = new FileInputStream("fish-iris.csv.txt");
        } catch (FileNotFoundException e){
            System.out.println("Could not open input file - ending program");
            System.exit(1);
        }
        Scanner fileReader = new Scanner(fileOpener);
        return fileReader;
    }
}
