package solution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jfree.data.xy.XYSeries;
import solution.Profiler.*;


/**
 * ArrayList, MyLinkedList(단일 연결), LinkedList(이중 연결)
 * add(끝) - ArrayList: 1    MyLinkedList: n     LinkedList: 1
 * add(시작) - ArrayList: n    MyLinkedList: 1     LinkedList: 1
 * add(보통) - ArrayList: n    MyLinkedList: n     LinkedList: n
 * get/set - ArrayList: 1    MyLinkedList: n   LinkedList: n
 * indexOf/lastIndexOf - ArrayList: n    MyLinkedList: n     LinkedList: n
 * isEmpty/size - ArrayList: 1   MyLinkedList: 1     LinkedList: 1
 * remove(끝) - ArrayList: 1     MyLinkedList: n     LinkedList: 1
 * remove(시작) - ArrayList: n    MyLinkedList: 1     LinkedList: 1
 * remove(보통) - ArrayList: n    MyLinkedList: n     LinkedList: n
 */

/**
 * 알고리즘 분석에서의 자료구조 선택은 다음 조건일 때만 유효
 * 1. 응용 프로그램의 실행시간이 중요하다
 * 2. 응용 프로그램의 실행시간이 선택한 자료구조에 의존한다
 * 3. 증가 차수에 따라 어느 자료구조가 나은지 실제로 예측할 수 있을 만큼 문제 크기가 충분히 크다
 */
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

            // 상수 시간
            public void timeMe(int n) {
                for (int i=0; i<n; i++) {
                    list.add("a string"); // 상수 시간
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
            
            // 이차
            public void timeMe(int n) {
                for (int i=0; i<n; i++) {
                    list.add(0, "a string"); // 선형
                }
            }
        };
        int startN = 4000; // 시간 측정을 시작하는 n 값
        int endMillis = 10000; // 밀리 초 단위로 임계치를 지정
        runProfiler("ArrayList add begin", timeable, startN, endMillis);
    }
    
    public static void profileLinkedListAddBeginning() {
        Timeable timeable = new Timeable() {
            List<String> list;

            public void setup(int n) {
                list = new LinkedList<String>();
            }

            // 선형
            public void timeMe(int n) {
                for (int i=0; i<n; i++) {
                    list.add(0, "a string"); // 상수 시간
                }
            }
        };
        int startN = 128000; // 시간 측정을 시작하는 n 값
        int endMillis = 2000; // 밀리 초 단위로 임계치를 지정
        runProfiler("LinkedList add begin", timeable, startN, endMillis);
    }

    public static void profileLinkedListAddEnd() {
        Timeable timeable = new Timeable() {
            List<String> list;

            public void setup(int n) {
                list = new LinkedList<String>();
            }

            // 선형
            public void timeMe(int n) {
                for (int i=0; i<n; i++) {
                    list.add("a string");
                }
            }
        };
        int startN = 64000; // 시간 측정을 시작하는 n 값
        int endMillis = 1000; // 밀리 초 단위로 임계치를 지정
        runProfiler("LinkedList add end", timeable, startN, endMillis);
    }
    
    private static void runProfiler(String title, Timeable timeable, int startN, int endMillis) {
        Profiler profiler = new Profiler(title, timeable);
        XYSeries series = profiler.timingLoop(startN, endMillis);
        profiler.plotResults(series);
    }
}