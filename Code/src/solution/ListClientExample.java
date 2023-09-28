package solution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListClientExample {

    @SuppressWarnings("rawtypes")
    private List list;

    @SuppressWarnings("rawtypes")
    public ListClientExample() {
        list = new LinkedList();
    }

    @SuppressWarnings("rawtypes")
    public List getList() {
        return list;
    }

}
