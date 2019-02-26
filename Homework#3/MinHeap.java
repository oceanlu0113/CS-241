package homework.pkg3;

public class MinHeap<T extends Comparable<? super T>> implements MinHeapInterface<T> {

    private T[] heap;
    private int lastIndex;
    private boolean initialized = false;

    public MinHeap() {
        this(25);
    }

    public MinHeap(int initialCapacity) {
        heap = (T[]) new Comparable[initialCapacity + 1];
        lastIndex = 0;
    }

    public MinHeap(T[] entries) {
        this(entries.length);
        lastIndex = entries.length;
        for (int index = 0; index < entries.length; index++) {
            heap[index + 1] = entries[index];
        }
        for (int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--) {
            reheap(rootIndex);
        }
    }

    public void getKthSmallest(T[] entries, int k) {
        System.out.println(k + "th smallest integer(s): ");
        for (int i = 1; i < k; i++) {
           removeMin();
        }
        System.out.println(getMin());
    }

    @Override
    public void add(T newEntry) {
        int newIndex = lastIndex + 1;
        int parentIndex = newIndex / 2;
        while ((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) < 0) {
            heap[newIndex] = heap[parentIndex];
            newIndex = parentIndex;
            parentIndex = newIndex / 2;
        }
        heap[newIndex] = newEntry;
        lastIndex++;
    }

    public void reheap(int rootIndex) {
        int smallest = rootIndex;
        int l = 2 * rootIndex;
        int r = 2 * rootIndex + 1;
        if (l < lastIndex && heap[l].compareTo(heap[smallest]) < 0) {
            smallest = l;
        }
        if (r < lastIndex && heap[r].compareTo(heap[smallest]) < 0) {
            smallest = r;
        }
        if (smallest != rootIndex) {
            T temp = heap[rootIndex];
            heap[rootIndex] = heap[smallest];
            heap[smallest] = temp;
            reheap(smallest);
        }
    }

    @Override
    public T removeMin() {
        T root = null;
        if (!isEmpty()) {
            root = heap[1];
            heap[1] = heap[lastIndex];
            lastIndex--;
            reheap(1);
        }
        return root;
    }

    @Override
    public T getMin() {
        T root = null;
        if (!isEmpty()) {
            root = heap[1];
        }
        return root;
    }

    @Override
    public boolean isEmpty() {
        return lastIndex < 1;
    }

    @Override
    public int getSize() {
        return lastIndex;
    }

    @Override
    public void clear() {
        while (lastIndex > -1) {
            heap[lastIndex] = null;
            lastIndex--;
        }
    }
}
