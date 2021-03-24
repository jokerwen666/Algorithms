import symboltable.SeparateChainingHashST;
import org.junit.Test;

public class HashTest {

    @Test
    public void SeparateChainingHashTest() {
        SeparateChainingHashST<Integer,Integer> st = new SeparateChainingHashST<Integer, Integer>();
        st.put(1,1);
        st.put(2,3);
        st.put(3,4);
        st.put(998,1);
        st.put(999,3);
        st.put(1000,4);
        st.remove(998);
        st.remove(2);
    }
}
