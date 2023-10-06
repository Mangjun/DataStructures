package solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiPhilosophy {

    final static List<String> visited = new ArrayList<String>(); // 이미 방문한 페이지의 URL을 저장하는 리스트
    final static WikiFetcher wf = new WikiFetcher(); // 웹 페이지를 가져오는 역할

    public static void main(String[] args) throws IOException {
        String destination = "https://en.wikipedia.org/wiki/Philosophy";
        String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        testConjecture(destination, source, 10);
    }

    public static void testConjecture(String destination, String source, int limit) throws IOException {
        String url = source;
        for (int i=0; i<limit; i++) {
            // 이미 방문한 페이지인지 확인
            if (visited.contains(url)) {
                // 방문했다면 종료
                System.err.println("We're in a loop, exiting.");
                return;
            } else {
                // 아니라면 방문한 페이지를 기록
                visited.add(url);
            }
            Element elt = getFirstValidLink(url); // 현재 페이지에서 첫 번째 유효한 링크를 찾음
            // 유효한 링크를 못 찾은 경우 종료
            if (elt == null) {
                System.err.println("Got to a page with no valid links.");
                return;
            }

            System.out.println("**" + elt.text() + "**"); // 찾은 유효한 링크 출력
            url = elt.attr("abs:href"); // 다음 페이지 URL을 설정, abs:href는 상대 URL을 절대 URL로 변환

            // 목적지를 찾은 경우
            if (url.equals(destination)) {
                System.out.println("Eureka!");
                break;
            }
        }
    }

    // 주어진 URL에서 페이지를 가져오고, 첫 번째 유효한 링크를 찾는 역할
    public static Element getFirstValidLink(String url) throws IOException {
        print("Fetching %s...", url); // 현재 위치 출력
        Elements paragraphs = wf.fetchWikipedia(url); // 주어진 URL에서 페이지를 가져옴
        WikiParser wp = new WikiParser(paragraphs);
        Element elt = wp.findFirstLink(); // 첫 번째 유효한 링크를 찾음
        return elt;
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
}