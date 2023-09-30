package solution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jfree.data.xy.XYSeries;
import solution.Profiler.*;


public class ProfileListAdd {

    public static void main(String[] args) {
        //profileArrayListAddEnd();
        //profileArrayListAddBeginning();
        //profileLinkedListAddBeginning();
        profileLinkedListAddEnd();
    }

    public static void profileArrayListAddEnd() {
        Timeable timeable = new Timeable() {
            List<String> list;

            public void setup(int n) {
                list = new ArrayList<String>();
            }

            public void timeMe(int n) {
                for (int i=0; i<n; i++) {
                    list.add("a string");
                }
            }
        };
        int startN = 4000; // 시간 측정을 시작하는 n 값
        int endMillis = 1000; // 밀리 초 단위로 임계치를 지정
        runProfiler("ArrayList add end", timeable, startN, endMillis);
    }

    public static void profileArrayListAddBeginning() {
        Timeable timeable = new Timeable() {
            List<String> list;

            public void setup(int n) {
                list = new ArrayList<String>();
            }

            public void timeMe(int n) {
                for (int i=0; i<n; i++) {
                    list.add(0, "a string");
                }
            }
        };
        int startN = 4000; // 시간 측정을 시작하는 n 값
        int endMillis = 1000; // 밀리 초 단위로 임계치를 지정
        runProfiler("ArrayList add begin", timeable, startN, endMillis);
    }

    public static void profileLinkedListAddBeginning() {
        Timeable timeable = new Timeable() {
            List<String> list;

            public void setup(int n) {
                list = new LinkedList<String>();
            }

            public void timeMe(int n) {
                for (int i=0; i<n; i++) {
                    list.add(0, "a string");
                }
            }
        };
        int startN = 4000; // 시간 측정을 시작하는 n 값
        int endMillis = 1000; // 밀리 초 단위로 임계치를 지정
        runProfiler("LinkedList add begin", timeable, startN, endMillis);
    }

    public static void profileLinkedListAddEnd() {
        Timeable timeable = new Timeable() {
            List<String> list;

            public void setup(int n) {
                list = new LinkedList<String>();
            }

            public void timeMe(int n) {
                for (int i=0; i<n; i++) {
                    list.add("a string");
                }
            }
        };
        int startN = 4000; // 시간 측정을 시작하는 n 값
        int endMillis = 1000; // 밀리 초 단위로 임계치를 지정
        runProfiler("LinkedList add end", timeable, startN, endMillis);
    }
    
    private static void runProfiler(String title, Timeable timeable, int startN, int endMillis) {
        Profiler profiler = new Profiler(title, timeable);
        XYSeries series = profiler.timingLoop(startN, endMillis);
        profiler.plotResults(series);
    }
}