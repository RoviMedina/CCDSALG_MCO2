import java.util.*;

public class mco2 {
    static Scanner sc = new Scanner(System.in);

    //Class for Hash Table
    public static class kmerEntry{
        String kmerString;
        int count;

        kmerEntry(String kmerString, int count){
            this.kmerString = kmerString;
            this.count = count;
        }
    }


    //----------------------------------------------------------------------------------------------------------------------------------------
    //#1 RANDOM DNA GENERATOR
    //----------------------------------------------------------------------------------------------------------------------------------------
    public static String generateDNA(int length){
        char[] base = {'a', 'c', 'g', 't'};
        Random random = new Random();

        StringBuilder randomDNA = new StringBuilder();

        for(int i = 0; i < length; i++){
            randomDNA.append(base[random.nextInt(4)]);
        }

        return randomDNA.toString();
    }


    //----------------------------------------------------------------------------------------------------------------------------------------
    //#1 HASH FUNCTION ALGORITHM
    //----------------------------------------------------------------------------------------------------------------------------------------
    public static void hashFuncAlgo(String S, int k, int mode){

        int n = S.length();
        int collisionCount = 0;
        LinkedList<kmerEntry>[] hashTable = new LinkedList[n];
        LinkedList<String> orderedHashTable = new LinkedList<>(); //this is to preserve the order of discovery of the substrings

        long startTime = System.nanoTime();
        long endTime;
        double durationTime;
        for(int i = 0; i < n; i++){
            hashTable[i] = new LinkedList<>();
        }

        switch(mode){
            case 1: //Rolling Polynomial
                //generates all the substrings of length k based from the given k-mer and assigns them their respective 1 digit hash
                for(int i = 0; i <= n - k; i++){
                    String kmerCurr = S.substring(i, i + k);

                    int h = kmerCurr.hashCode();
                    int index = Math.floorMod(h, n);

                    //for substrings that were already found previously
                    boolean found = false;
                    for(kmerEntry entry : hashTable[index]){
                        if(entry.kmerString.equals(kmerCurr)){
                            entry.count++;
                            found = true;
                            break;
                        }
                    }

                    //for new substrings
                    if(!found){
                        if(!hashTable[index].isEmpty()){
                            collisionCount++;
                        }
                        hashTable[index].add(new kmerEntry(kmerCurr, 1));
                        orderedHashTable.add(kmerCurr);
                    }
                }
                endTime = System.nanoTime();

                //for loop for displaying the kmer distribution
                System.out.println("K-mer Distribution Using Hash");
                for(String kmer : orderedHashTable){
                    int h = kmer.hashCode();
                    int index = Math.floorMod(h, n);

                    for(kmerEntry entry : hashTable[index]){
                        if(entry.kmerString.equals(kmer)){
                            System.out.println(kmer + " -> " + entry.count);
                        }
                    }
                }
                System.out.println("Number of collisions: " + collisionCount);
                durationTime = (endTime - startTime) / 1000000.0;
                System.out.println("==============================");
                System.out.println("Runtime: " + durationTime + " ms");
                System.out.println("==============================");
                break;
            case 2: //FNV-1A
                //generates all the substrings of length k based from the given k-mer and assigns them their respective 1 digit hash
                for(int i = 0; i <= n - k; i++){
                    String kmerCurr = S.substring(i, i + k);

                    int h = fnv1aAlgo(kmerCurr);
                    int index = Math.floorMod(h, n);

                    //for substrings that were already found previously
                    boolean found = false;
                    for(kmerEntry entry : hashTable[index]){
                        if(entry.kmerString.equals(kmerCurr)){
                            entry.count++;
                            found = true;
                            break;
                        }
                    }

                    //for new substrings
                    if(!found){
                        if(!hashTable[index].isEmpty()){
                            collisionCount++;
                        }
                        hashTable[index].add(new kmerEntry(kmerCurr, 1));
                        orderedHashTable.add(kmerCurr);
                    }
                }
                endTime = System.nanoTime();

                //for loop for displaying the kmer distribution
                System.out.println("K-mer Distribution Using Hash");
                for(String kmer : orderedHashTable){
                    int h = fnv1aAlgo(kmer);
                    int index = Math.floorMod(h, n);

                    for(kmerEntry entry : hashTable[index]){
                        if(entry.kmerString.equals(kmer)){
                            System.out.println(kmer + " -> " + entry.count);
                        }
                    }
                }
                System.out.println("Number of collisions: " + collisionCount);
                durationTime = (endTime - startTime) / 1000000.0;
                System.out.println("==============================");
                System.out.println("Runtime: " + durationTime + " ms");
                System.out.println("==============================");
                break;
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------
    //#1.b FNV-1A HASH
    //----------------------------------------------------------------------------------------------------------------------------------------
    public static int fnv1aAlgo(String input){
        final int FNV_PRIME = 0x01000193;
        final int FNV_OFFSET_BASIS = 0x811C9DC5;

        int hash = FNV_OFFSET_BASIS;

        for(int i = 0; i < input.length(); i++){
            hash ^= input.charAt(i);
            hash *= FNV_PRIME;
        }
        return hash;
    }


    //----------------------------------------------------------------------------------------------------------------------------------------
    //#2 BINARY SEARCH TREE ALGORITHM
    //----------------------------------------------------------------------------------------------------------------------------------------
    public static BSTNode bstAlgo(String S, int kmerLength){
        int length = S.length();
        BSTNode root = KmerBST.create();

        for(int startIndex = 0; startIndex <= length - kmerLength; startIndex++){
            String kmerCurr = S.substring(startIndex, startIndex + kmerLength);

            BSTNode foundNode = KmerBST.search(root, kmerCurr);

            if(foundNode != null)
                foundNode.count++;
            else
                root = KmerBST.insert(root, kmerCurr);
        }

        return root;
    }


    //----------------------------------------------------------------------------------------------------------------------------------------
    //MAIN MENU
    //----------------------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {

        String S = null;
        int k = 9;


        String input10to4 = generateDNA(10000);
        String input10to5 = generateDNA(100000);
        String input10to6 = generateDNA(1000000);
        /*
        //-----------------------------File Handling--------------------------------
        try {
            BufferedReader readInput = new BufferedReader(new FileReader("input.txt")); //MAKE SURE INPUT.TXT IN SAME FOLDER
            input10to4 = readInput.readLine();
            readInput.readLine();
            input10to5 = readInput.readLine();
            readInput.readLine();
            input10to6 = readInput.readLine();
            readInput.readLine();
            readInput.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        //-----------------------------Main Menu--------------------------------
        int choice1 = 0;
        int choice2 = 0;


        System.out.println("==============================");
        System.out.println("Select K-mer length");
        System.out.println("==============================");
        do {
            System.out.println("1. 10^4");
            System.out.println("2. 10^5");
            System.out.println("3. 10^6");
            System.out.println("4. Exit");
            choice1 = sc.nextInt();
            while (choice1 < 0 || choice1 > 4) {
                System.out.println("INVALID CHOICE! Please chose again");
                choice1 = sc.nextInt();
            }

            switch(choice1){
                case 1: S = input10to4;
                    break;
                case 2: S = input10to5;
                    break;
                case 3: S = input10to6;
                    break;
            }

            if(choice1 != 0){
                System.out.println("==============================");
                System.out.println("Value of k (5,6,7):");
                System.out.println("0. Back");
                k = sc.nextInt();
                while((k < 5 || k > 7) && k != 0){
                    System.out.println("INVALID CHOICE! Please chose again");
                    k = sc.nextInt();
                }
                System.out.println("==============================");
            }
            if(k != 0){
                System.out.println("==============================");
                System.out.println("Chose Algorithm:");
                System.out.println("1. Hash Table");
                System.out.println("2. Binary Search Tree");
                System.out.println("0. Back");
                choice2 = sc.nextInt();
                while((choice2 < 0 || choice2 > 2)){
                    System.out.println("INVALID CHOICE! Please chose again");
                    choice2 = sc.nextInt();
                }
                System.out.println("==============================");
            }
            switch(choice2){
                case 1:
                    System.out.println("==============================");
                    System.out.println("Chose Hash Function Algorithm");
                    System.out.println("1. Rolling Polynomial");
                    System.out.println("2. FNV-1A");
                    System.out.println("0. Back");
                    int mode = sc.nextInt();
                    while((mode < 0 || mode > 2) && mode != 0){
                        System.out.println("INVALID CHOICE! Please chose again");
                        mode = sc.nextInt();
                    }
                    switch(mode){
                        case 1:
                            System.out.println("==============================");
                            System.out.println("Rolling Polynomial Algorithm:");
                            System.out.println("==============================");
                            hashFuncAlgo(S, k, mode);
                            break;
                        case 2:
                            System.out.println("==============================");
                            System.out.println("FNV-1A Algorithm:");
                            System.out.println("==============================");
                            hashFuncAlgo(S, k, mode);
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Binary Search Tree Algorithm:");
                    System.out.println("==============================");
                    long startTime = System.nanoTime();
                    BSTNode bstResult = bstAlgo(S, k);
                    long endTime = System.nanoTime();
                    KmerBST.printBST(bstResult);
                    double durationTime = (endTime - startTime) / 1000000.0;
                    System.out.println("==============================");
                    System.out.println("Runtime: " + durationTime + " ms");
                    System.out.println("==============================");
                    break;
            }
        } while(choice1 != 0);
    }
}
