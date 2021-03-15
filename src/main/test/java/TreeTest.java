import LLRBTree.LLRBTree;
import org.junit.Test;

public class TreeTest {
    @Test
    public void LLBRTreeTest() {
        LLRBTree<Integer,Integer> rb = new LLRBTree<Integer, Integer>();
        rb.put(5,5);
        rb.put(4,4);
        rb.put(3,3);
        rb.put(4,400);
        rb.put(3,300);
    }
}
