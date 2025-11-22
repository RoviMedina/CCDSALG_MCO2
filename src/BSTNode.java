public class BSTNode {
    String kmerString;
    int count;
    BSTNode leftChild;
    BSTNode rightChild;

    //initializes a new root node
    BSTNode(String kmerString){
        this.kmerString = kmerString;
        this.count = 1;
        this.leftChild = null;
        this.rightChild = null;
    }
}
