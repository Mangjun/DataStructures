package solution;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.jsoup.select.Elements;

public class Index {

    private Map<String, Set<TermCounter>> index = new HashMap<String, Set<TermCounter>>();

    public void add(String term, TermCounter tc) {
        Set<TermCounter> set = get(term);

        // 어떤 검색어를 처음 찾으면 새로운 Set 생성
        if (set == null) {
            set = new HashSet<TermCounter>();
            index.put(term, set);
        }
        // 그렇지 않다면 기존 Set을 변경
        set.add(tc);
    }

    public Set<TermCounter> get(String term) {
        return index.get(term);
    }

    public void printIndex() {
        for (String term: keySet()) {
            System.out.println(term);

            // 단어별 등장하는 페이지와 등장 횟수를 표시
            Set<TermCounter> tcs = get(term);
            for (TermCounter tc: tcs) {
                Integer count = tc.get(term);
                System.out.println("    " + tc.getLabel() + " " + count);
            }
        }
    }

    public Set<String> keySet() {
        return index.keySet();
    }

    // 인덱스를 갱신
    public void indexPage(String url, Elements paragraphs) {
        // TermCounter 객체를 만들고 단락에 있는 단어를 셈
        TermCounter counter = new TermCounter(url.toString());
        counter.processElements(paragraphs);
        
        // TermCounter 에 있는 각 검색어에 대해 TermCounter 객체를 인덱스에 추가
        for(String word : counter.keySet()) {
            add(word, counter);
        }
    }

    public static void main(String[] args) throws IOException {

        WikiFetcher wf = new WikiFetcher();
        Index indexer = new Index();

        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        Elements paragraphs = wf.fetchWikipedia(url);
        indexer.indexPage(url, paragraphs);

        url = "https://en.wikipedia.org/wiki/Programming_language";
        paragraphs = wf.fetchWikipedia(url);
        indexer.indexPage(url, paragraphs);

        indexer.printIndex();
    }
}