public class KmerBST {


    //-------------------------------
    //Create
    //-------------------------------
    public static BSTNode create(){
        return null;
    }
    //-------------------------------
    //Search
    //-------------------------------
    public static BSTNode search(BSTNode root, String kmerKey){
        if(root == null)
            return null;

        int comparison = kmerKey.compareTo(root.kmerString);
        if(comparison == 0)
            return root;
        else if(comparison < 0)
            return search(root.leftChild, kmerKey);
        else
            return search(root.rightChild, kmerKey);
    }
    //-------------------------------
    //Insert
    //-------------------------------
    public static BSTNode insert(BSTNode root, String kmerKey){
        if(root == null){
            return new BSTNode(kmerKey);
        }
        int comparison = kmerKey.compareTo(root.kmerString);

        if(comparison == 0)
            root.count++;
        else if(comparison < 0)
            root.leftChild = insert(root.leftChild, kmerKey);
        else
            root.rightChild = insert(root.rightChild, kmerKey);

        return root;
    }
    //-------------------------------
    //Print
    //-------------------------------
    public static void printBST(BSTNode root){
        if(root != null){
            printBST(root.leftChild);
            System.out.println(root.kmerString + " -> " + root.count);
            printBST(root.rightChild);
        }

    }
}
