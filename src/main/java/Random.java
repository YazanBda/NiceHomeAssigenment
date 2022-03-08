import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Random {
    // to save the number of all numbers to be printed
    static int counter = 0;

    // these two functions just to make the code more clear/readable.
    static void print(ArrayList<Integer> arr) {
        System.out.println(arr.toString());
    }
    static void swap(ArrayList<Integer> arr, int i, int index) {
        int tmp = arr.get(i);
        arr.set(i, arr.get(index));
        arr.set(index, tmp);
    }


    /**
     * Get a random integer in range
     *
     * @param min - the min number in the range
     * @param max - the max number in the range
     * @return - a random integer number between the min number and max number inclusive.
     */
    public static int randomInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * print all numbers in range [min , max]
     * via additional array which stores the unique number picked until now
     * from the randomInRange function..
     * used hashMap just to check if the given number already picked . (constant time ).
     *
     * that is , 1) let rand = randomInRange ,
     *           2) if !H.contains rand then:
     *                  array <- rand
     *                  H <- rand
     *           3) return to 1
     * Time Complexity : O(n*?) ,.. not efficient , depend on the picked random number ,
     *                              O(n) best case
     * Space Complexity : O(2 * n) such that n = the number of numbers in range [min , max]
     *
     * @param min - the min number in the range
     * @param max - the max number in the range
     */
    static void Classical_Way(int min, int max) {
        if(min > max)
        {
            System.out.println("invalid input");
            return;
        }
        HashMap<Integer, Integer> H = new HashMap<>();
        ArrayList<Integer> arr = new ArrayList<>();
        int size = (max - min) + 1;
        while (arr.size() < size) {
            int rand = randomInRange(min, max);
            if (H.get(rand) == null) {
                H.put(rand, 1);
                arr.add(rand);
            }
        }
        print(arr);
    }

    /**
     * print all numbers in range [min , max].
     * based on the classical way above , without using additional memory ,
     * we want each time to generate a new unique random number
     * that is ,
     *              1) let rand = RandomInRange(min , max) :
     *              divide the main range to 3 subRanges , :
     *              I   - [min , rand -1 ] ,
     *              II  - [rand] ,
     *              III - [rand+1 , max]
     *
     *              2) print rand
     *              3) Recursively ,
     *                   call the main function with new range (I) .
     *                   call the main function with new range (III) .
     * base cases :
     *          1) if counter equals to zero ,
     *              means that we printed all the possible numbers.
     *          2) if min > max ,
     *             means that rand from the past iteration was equal to min or  max
     *             so skip this call.
     *
     *          * note *
     *           if min == max , we still need to print rand
     * Time Complexity : O(n) subject to : n = | [min , max] + 1 |
     * Space Complexity : O(n) subject to : n = | [min , max] + 1 | (the number of recursive calls!!)
     *
     * @param min - the min number in the range
     * @param max - the max number in the range
     */
    static void Efficient_classic_way(int min, int max) {
        if(min > max)
        {
            System.out.println("invalid input");
            return;
        }
        // initialize a static global counter
        // with the total number of numbers to be printed
        counter = (max - min) + 1;

        //call the main function
        System.out.print("[");
        RecursiveWay(min, max);
    }

    private static void RecursiveWay(int min, int max) {
        // base case 2
        if (min > max) {
            return;
        }
        // base case 1 (described above).
        if (counter == 0) {
            return;
        }
        // get a rand number , print it ,
        // reduce the amount of unique numbers by one
        int rand = randomInRange(min, max);
        System.out.print(counter == 1 ? rand + "]" : rand + ", ");
        counter--;

        // to avoid calling left range always ,
        // so generate random direction and call it!
        int direc = randomInRange(0, 1);
        if(direc == 0)
        {
            RecursiveWay(min, rand - 1 );
            RecursiveWay(rand + 1, max );
        }
        else
        {
            RecursiveWay(rand + 1, max );
            RecursiveWay(min, rand - 1 );
        }

    }


    /**
     * print all numbers in range [min , max]
     * based on Fisher–Yates Algorithem:
     * via additional array , append all the numbers in it
     * pick randomly index
     * swap it with the current index
     * that is , 1) let rand_index = randomInRange , i = current_index
     *           2) swap(&rand_index , &i )
     *           3) return to 1
     * <p>
     * Time Complexity : O(n) such that n = length of [min , max]
     * Space Complexity : O(n)
     *
     * @param min - the min number in the range
     * @param max - the max number in the range
     */
    static void shuffle(int min, int max) {
        if(min > max)
        {
            System.out.println("invalid input");
            return;
        }
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            arr.add(i);
        }
        int n = arr.size();
        for (int i = n - 1; i > 0; i--) {
            int index = randomInRange(0, i);
            swap(arr, i, index);
        }
        print(arr);

    }

    public static void main(String[] args) {

         System.out.println("classical_way : ");
         Classical_Way(-5,5);
         System.out.println("shuffle_way : ");
         shuffle(-5, 5);
         System.out.println("recursive_way : ");
         Efficient_classic_way(-5,5);
    }
}
