package solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class TermCounter {

    private Map<String, Integer> map; // String: 검색어, Integer: 등장 횟수
    private String label; // URL 저장

    public TermCounter(String label) {
        this.label = label;
        this.map = new HashMap<String, Integer>();
    }

    public String getLabel() {
        return label;
    }

    public int size() {
        ArrayList<Integer> list = new ArrayList<>(map.values());
        int size = 0;

        for(int i : list) {
            size += i;
        }

        return size;
    }

    /**
     * 문단 전체를 인자로 받음
     * 문단 각각에 대해 processTree 호출 
     */
    public void processElements(Elements paragraphs) {
        for (Node node: paragraphs) {
            processTree(node);
        }
    }

    /**
     * DOM 트리의 root를 인자로 받음
     * 텍스트를 포함한 노드를 찾은 후 텍스트를 추출하여 processText로 전달
     */
    public void processTree(Node root) {
        for (Node node: new WikiNodeIterable(root)) {
            if (node instanceof TextNode) {
                processText(((TextNode) node).text());
            }
        }
    }

    /**
     * 단어와 공백, 구두점을 포함한 문자열을 인자로 받음
     * replaceAll, split은 regex를 인자로 받음
     * 구두점은 공백으로 대체, 나머지는 소문자로 변환, 단어로 나눔
     * 각 단어별로 등장 횟수를 1 증가 시킴
     */
    public void processText(String text) {
        String[] array = text.replaceAll("\\pP", " ").
                toLowerCase().
                split("\\s+");

        for (int i=0; i<array.length; i++) {
            String term = array[i];
            incrementTermCount(term);
        }
    }

    // 등장 횟수를 1씩 증가
    public void incrementTermCount(String term) {
        // System.out.println(term);
        put(term, get(term) + 1);
    }

    public void put(String term, int count) {
        map.put(term, count);
    }

    public Integer get(String term) {
        Integer count = map.get(term);
        return count == null ? 0 : count;
    }

    public Set<String> keySet() {
        return map.keySet();
    }

    public void printCounts() {
        for (String key: keySet()) {
            Integer count = get(key);
            System.out.println(key + ", " + count);
        }
        System.out.println("Total of all counts = " + size());
    }

    public static void main(String[] args) throws IOException {
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        WikiFetcher wf = new WikiFetcher();
        Elements paragraphs = wf.fetchWikipedia(url);

        TermCounter counter = new TermCounter(url.toString());
        counter.processElements(paragraphs);
        counter.printCounts();
    }
}