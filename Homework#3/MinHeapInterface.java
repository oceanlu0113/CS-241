package homework.pkg3;

public interface MinHeapInterface<T extends Comparable<? super T>> {

    /**
     * Adds a new entry to this heap
     *
     * @param newEntry An object to be added
     */
    public void add(T newEntry);

    /**
     * Removes and returns the largest item in this heap.
     *
     * @return Either the smallest object in the heap or null (if heap is empty)
     */
    public T removeMin();

    /**
     * Retrieves the largest item in this heap
     *
     * @return Either the smallest object in the heap or null (if heap is empty)
     */
    public T getMin();

    /**
     * Detects whether this heap is empty
     *
     * @return True if the heap is empty, false otherwise
     */
    public boolean isEmpty();

    /**
     * Gets the size of this heap
     *
     * @return The number of entries currently in the heap
     */
    public int getSize();

    // Removes all entries from this heap
    public void clear();
}
