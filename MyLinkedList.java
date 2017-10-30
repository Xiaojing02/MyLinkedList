/**
 * Xiaojing Zhang
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * LinkedList class implements a doubly-linked list.
 */
public class MyLinkedList<AnyType> implements Iterable<AnyType>
{
    /**
     * Construct an empty LinkedList.
     */
    public MyLinkedList( )
    {
        doClear( );
    }

    private void clear( )
    {
        doClear( );
    }

    /**
     * Change the size of this collection to zero.
     */
    public void doClear( )
    {
        beginMarker = new Node<>( null, null, null );
        endMarker = new Node<>( null, beginMarker, null );
        beginMarker.next = endMarker;

        theSize = 0;
        modCount++;
    }

    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size( )
    {
        return theSize;
    }

    public boolean isEmpty( )
    {
        return size( ) == 0;
    }

    /**
     * Adds an item to this collection, at the end.
     * @param x any object.
     * @return true.
     */
    public boolean add( AnyType x )
    {
        add( size( ), x );
        return true;
    }

    /**
     * Adds an item to this collection, at specified position.
     * Items at or after that position are slid one position higher.
     * @param x any object.
     * @param idx position to add at.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    public void add( int idx, AnyType x )
    {
        addBefore( getNode( idx, 0, size( ) ), x );
    }

    /**
     * Adds an item to this collection, at specified position p.
     * Items at or after that position are slid one position higher.
     * @param p Node to add before.
     * @param x any object.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    private void addBefore( Node<AnyType> p, AnyType x )
    {
        Node<AnyType> newNode = new Node<>( x, p.prev, p );
        newNode.prev.next = newNode;
        p.prev = newNode;
        theSize++;
        modCount++;
    }


    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType get( int idx )
    {
        return getNode( idx ).data;
    }

    /**
     * Changes the item at position idx.
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType set( int idx, AnyType newVal )
    {
        Node<AnyType> p = getNode( idx );
        AnyType oldVal = p.data;

        p.data = newVal;
        return oldVal;
    }

    /**
     * Gets the Node at position idx, which must range from 0 to size( ) - 1.
     * @param idx index to search at.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1, inclusive.
     */
    private Node<AnyType> getNode( int idx )
    {
        return getNode( idx, 0, size( ) - 1 );
    }

    /**
     * Gets the Node at position idx, which must range from lower to upper.
     * @param idx index to search at.
     * @param lower lowest valid index.
     * @param upper highest valid index.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive.
     */
    private Node<AnyType> getNode( int idx, int lower, int upper )
    {
        Node<AnyType> p;

        if( idx < lower || idx > upper )
            throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );

        if( idx < size( ) / 2 )
        {
            p = beginMarker.next;
            for( int i = 0; i < idx; i++ )
                p = p.next;
        }
        else
        {
            p = endMarker;
            for( int i = size( ); i > idx; i-- )
                p = p.prev;
        }

        return p;
    }

    /**
     * Removes an item from this collection.
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public AnyType remove( int idx )
    {
        return remove( getNode( idx ) );
    }

    /**
     * Removes the object contained in Node p.
     * @param p the Node containing the object.
     * @return the item was removed from the collection.
     */
    private AnyType remove( Node<AnyType> p )
    {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;

        return p.data;
    }

    /**
     * @param i, j the indexes of Nodes need to swap.
     * Swap the two nodes by changing the links.
     */

    public void swap(int i, int j) {
        if(i < 0 || j < 0 || i >= size() || j >= size()) {
            System.out.println("The index provided exceeds size and the list is not changed");
            //throw new ArrayIndexOutOfBoundsException();
        } else if(i == j || size() == 1) {
            return;
        } else {
            Node<AnyType> p1, p2;
            if(i <= size() / 2) { // node i at the first half of the list
                p1 = beginMarker.next;
                for(int m = 0; m < i; m++) {
                    p1 = p1.next;
                }
            } else {
                p1 = endMarker.prev;
                for(int m = size() - 1; m > i; m--) {
                    p1 = p1.prev;
                }
            }
            if(j <= size() / 2) {
                p2 = beginMarker.next;
                for(int m = 0; m < j; m++) {
                    p2 = p2.next;
                }
            } else {
                p2 = endMarker.prev;
                for(int m = size() - 1; m > j; m--) {
                    p2 = p2.prev;
                }
            }
            p1.prev.next = p2;
            Node p3 = p2.next;
            Node p4 = p2.prev;
            p2.next = p1.next;
            p2.prev = p1.prev;
            p1.next.prev = p2;
            p4.next = p1;
            p1.prev = p4;
            p1.next = p3;
            p3.prev = p1;
        }
    }

    public void shift(int x) {
        if(size() == 0) return;
        if(x % size() == 0) return;
        if(x > 0) {
            x = x % size();
            Node<AnyType> p = beginMarker.next;
            for(int i = 1; i < x; i++) {
                p = p.next;
            }
            endMarker.prev.next = beginMarker.next;
            beginMarker.next.prev = endMarker.prev;
            beginMarker.next = p.next;
            p.next.prev = beginMarker;
            p.next = endMarker;
            endMarker.prev = p;
        } else {
            long t = 0 - x;
            t = t % size();
            Node<AnyType> p = endMarker.prev;
            for(int i = 1; i < t; i++) {
                p = p.prev;
            }
            beginMarker.next.prev = endMarker.prev;
            endMarker.prev.next = beginMarker.next;
            p.prev.next = endMarker;
            endMarker.prev = p.prev;
            p.prev = beginMarker;
            beginMarker.next = p;
        }
    }

    public void erase(int index, int nums) {
        if(index < 0 || index >= size() || index + nums - 1 >= size() || nums > size() - index || nums <= 0) {
            System.out.println("The index provided or together with the number os elements exceeds size. The original list is not changed.");
            //throw new ArrayIndexOutOfBoundsException();
        } else {
            Node<AnyType> p1 = beginMarker.next;
            for(int i = 0; i < index; i++) {
                p1 = p1.next;
            }
            Node<AnyType> p2 = p1;
            for(int i = 1; i < nums; i++) {
                p2 = p2.next;
            }
            p1.prev.next = p2.next;
            p2.next.prev = p1.prev;
            theSize = size() - nums;
            modCount++;
        }
    }

    public void insertList(MyLinkedList<AnyType> list, int index) {
        if(index < 0 || index >= size()) {
            System.out.println("The index provided exceeds the size. The original list is not changed.");
            //throw new ArrayIndexOutOfBoundsException();
        } else {
            if(list.size() == 0) return;
            Node<AnyType> p = beginMarker;
            for(int i = 0; i < index; i++) {
                p = p.next;
            }
            Node<AnyType> t = p.next;
            Node<AnyType> cur = list.beginMarker.next;
            Node<AnyType> psudoCopy = new Node<AnyType>(null, null, null);
            Node<AnyType> copy = psudoCopy;
            while (cur != list.endMarker) {
                copy.next = new Node<AnyType>(cur.data, copy, null);
                cur = cur.next;
                copy = copy.next;
            }
            p.next = psudoCopy.next;
            p.next.prev = p;
            copy.next = t;
            t.prev = copy;
            theSize = size() + list.size();
            modCount++;
        }
    }


    /**
     * Returns a String representation of this collection.
     */
    public String toString( )
    {
        StringBuilder sb = new StringBuilder( "[ " );

        for( AnyType x : this )
            sb.append( x + " " );
        sb.append( "]" );

        return new String( sb );
    }

    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public java.util.Iterator<AnyType> iterator( )
    {
        return new LinkedListIterator( );
    }

    /**
     * This is the implementation of the LinkedListIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the MyLinkedList.
     */
    private class LinkedListIterator implements java.util.Iterator<AnyType>
    {
        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        public boolean hasNext( )
        {
            return current != endMarker;
        }

        public AnyType next( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !hasNext( ) )
                throw new java.util.NoSuchElementException( );

            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }

        public void remove( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !okToRemove )
                throw new IllegalStateException( );

            MyLinkedList.this.remove( current.prev );
            expectedModCount++;
            okToRemove = false;
        }
    }

    /**
     * This is the doubly-linked list node.
     */
    private static class Node<AnyType>
    {
        public Node( AnyType d, Node<AnyType> p, Node<AnyType> n )
        {
            data = d; prev = p; next = n;
        }

        public AnyType data;
        public Node<AnyType>   prev;
        public Node<AnyType>   next;
    }

    private int theSize;
    private int modCount = 0;
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;
}

class TestLinkedList
{
    public static void main( String [ ] args )
    {
        int[] size = {0, 20};
        int round = 1;
        for(int s : size) {
            System.out.println("******************************************************************");
            System.out.println("Round " + round + ": ");
            round++;
            MyLinkedList<Integer> lst = new MyLinkedList<>( );
            createList(lst, s);
            System.out.println("The original list is: ");
            System.out.println(lst.toString());
            System.out.println("The size of original list is: " + lst.size());
            System.out.println("\n\n\n");


            // swap test
            int[] indexPool = {-1, 0, 5, 12, lst.size() - 1, lst.size()};
            for(int index1 : indexPool) {
                for(int index2 : indexPool) {
                    testSwap(lst, index1, index2);
                    createList(lst, s); // change back to original list
                }
            }



            // shift test
            int[] shiftNum = {0, -1, 1, 5, -5, -15, 15, 20, -20, 25, -25};
            for(int i : shiftNum) {
                testShift(lst, i);
                createList(lst, s);    // change back to original list
            }



            // erase test case
            int[] eraseNum = {-1, 0, 1, 3, 15, lst.size()};
            for(int index : indexPool) {
                for(int nums : eraseNum) {
                    testErase(lst, index, nums);
                    createList(lst, s);    // change back to original list
                }
            }


            // insertList test
            List<MyLinkedList<Integer>> listPool = new ArrayList<>();
            MyLinkedList<Integer> list2 = new MyLinkedList<>();
            for(int i = 30; i < 33; i++) {
                list2.add(i);
            }
            listPool.add(new MyLinkedList<>());
            listPool.add(list2);
            for(int index : indexPool) {
                for(MyLinkedList<Integer> list : listPool) {
                    testInsertList(lst, index, list);
                    createList(lst, s);    // change back to original list
                }
            }
            System.out.println("\n\n\n\n\n");
        }
    }

    public static void createList(MyLinkedList<Integer> lst, int size) {
        lst.doClear();
        for(int i = 0; i < size; i++) {
            lst.add(i);
        }
    }

    public static void testSwap(MyLinkedList<Integer> lst, int index1, int index2) {
        System.out.println("Swap the element at: " + index1 + " and " + index2);
        lst.swap(index1, index2);
        System.out.println("The new list after swap is: ");
        System.out.println(lst.toString());
        System.out.println("The size of the new list is: " + lst.size());
        System.out.println("\n\n");
    }

    public static void testShift(MyLinkedList<Integer> lst, int num) {
        lst.shift(num);
        System.out.println("Shift " + num + " : ");
        System.out.println(lst.toString());
        System.out.println("The size of the new list is: " + lst.size());
        System.out.println("\n\n");
    }

    public static void testErase(MyLinkedList<Integer> lst, int index, int nums) {
        System.out.println("Erase elements : index = " + index + ", nums = " + nums);
        lst.erase(index, nums);
        System.out.println(lst.toString());
        System.out.println("The size of the new list is: " + lst.size());
        System.out.println("\n\n");
    }

    public static void testInsertList(MyLinkedList<Integer> lst, int index, MyLinkedList<Integer> list) {
        System.out.println("Insert list : index = " + index + ", list = " + list.toString());
        lst.insertList(list, index);
        System.out.println(lst.toString());
        System.out.println("The size of the new list is: " + lst.size());
        System.out.println("\n\n");
    }
}
