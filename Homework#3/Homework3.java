package homework.pkg3;
import java.util.Random;
import java.lang.Integer;
public class Homework3 {
    public static void main(String[] args) {
        Integer[] arr = new Integer [50];
        Random random = new Random();
        System.out.println("List of Integers Generated: ");
        for (int i = 0; i < 50; i++) {
            arr[i] = random.nextInt(101);
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        MinHeap heap = new MinHeap(arr);
        heap.getKthSmallest(arr, 5);
    }
}
