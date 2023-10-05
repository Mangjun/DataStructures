package solution;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**
 * 크롤링: 웹 페이지를 다운로드하고 파싱하고 텍스트와 다른 페이지로의 링크를 추출하는 프로그램
 * 인덱싱: 검색어를 조회하고 해당 검색어를 포함한 페이지를 찾는 데 필요한 자료구조
 * 검색: 인덱스에서 결과를 수집하고 검색어와 가장 관련된 페이지를 식별하는 방법
 */

/**
 * 모든 경우에서 리스트를 사용하지 않는 이유
 * 1. 메서드의 개수를 작게 유지하면 코드는 가독성이 높아지고 오류 발생 가능성도 줄어듦
 * 2. 자료구조에서 작은 API를 제공하면 효율적으로 구현하기 더 쉬움
 */

/**
 * 자바로 스택을 구현하는 세 가지 방법
 * 1. ArrayList 클래스나 LinkedList 클래스를 사용
 * 2. Stack 클래스를 사용 -> 오래된 버전이라 신 버전인 JCF와 일치하지 않음
 * 3. ArrayDeque 클래스나 Deque 인터페이스를 구현한 클래스를 사용
 */
public class WikiNodeExample {

    public static void main(String[] args) throws IOException {
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        
        // 문서를 다운로드하고 파싱하기
        Connection conn = Jsoup.connect(url);
        Document doc = conn.get();

        // 내용을 선택하고 단락 추출하기
        Element content = doc.getElementById("mw-content-text");
        Elements paras = content.select("p");
        Element firstPara = paras.get(0);

        recursiveDFS(firstPara);
        System.out.println();

        iterativeDFS(firstPara);
        System.out.println();

        Iterable<Node> iter = new WikiNodeIterable(firstPara);
        for (Node node: iter) {
            if (node instanceof TextNode) {
                System.out.print(node);
            }
        }
    }

    /**
     * DFS: 깊이 우선 탐색
     * 1. 트리의 루트에서 시작하여 첫 번째 자식 노드를 선택
     * 2. 자식이 자식을 가지고 있다면 첫 번째 자식을 다시 선택
     * 3. 자식이 없는 노드에 도착하면 부모 노드로 거슬러 올라감
     * 4. 부모 노드에 다음 자식이 있다면 그쪽으로 이동
     * 5. 다음 자식이 없다면 거슬러 올라감
     * 6. 루트의 마지막 노드까지 탐색하면 종료
     *
     * DFS를 구현하는 방법: 재귀적 방법 -> 호출 스택을 사용, 반복적 방법 -> 자료구조 스택을 사용
     */

    // 반복적 방법
    // 장점: 자바 Iterator로 구현하기 쉬움
    private static void iterativeDFS(Node root) {
        Deque<Node> stack = new ArrayDeque<Node>();
        stack.push(root);

        while (!stack.isEmpty()) {

            Node node = stack.pop();
            if (node instanceof TextNode) {
                System.out.print(node);
            }

            List<Node> nodes = new ArrayList<Node>(node.childNodes());
            Collections.reverse(nodes);

            for (Node child: nodes) {
                stack.push(child);
            }
        }
    }

    // 재귀적 방법(전위 순회)
    private static void recursiveDFS(Node node) {
        if (node instanceof TextNode) {
            System.out.print(node);
        }
        for (Node child: node.childNodes()) {
            recursiveDFS(child);
        }
    }
}