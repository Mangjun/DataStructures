package solution;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Elements는 문단을 저장할 때 사용
 * Element는 요소를 가리킴
 * tagName()은 요소의 태그 이름을 가져옴
 * attr()은 속성의 값을 가져옴
 */
public class WikiParser {

    private Elements paragraphs; // 문단(HTML 요소)을 저장

    private Deque<String> parenthesisStack; // 괄호 검사 스택

    public WikiParser(Elements paragraphs) {
        this.paragraphs = paragraphs;
        this.parenthesisStack = new ArrayDeque<String>();
    }

    // 문단에서 첫 번째 유효한 링크를 찾음
    public Element findFirstLink() {
        for (Element paragraph: paragraphs) {
            Element firstLink = findFirstLinkPara(paragraph);
            if (firstLink != null) {
                return firstLink;
            }
            if (!parenthesisStack.isEmpty()) {
                System.err.println("Warning: unbalanced parentheses.");
            }
        }
        return null;
    }

    // 특정 문단에서 첫 번째 유효한 링크를 찾음
    private Element findFirstLinkPara(Node root) {
        Iterable<Node> nt = new WikiNodeIterable(root);

        for (Node node: nt) {
            // 텍스트 노드인 경우 processTextNode로 처리
            if (node instanceof TextNode) {
                processTextNode((TextNode) node);
            }
            // HTML 요소인 경우 processElement로 처리
            if (node instanceof Element) {
                Element firstLink = processElement((Element) node);
                // 유효한 링크를 찾았다면
                if (firstLink != null) {
                    return firstLink;
                }
            }
        }
        return null; // 유효한 링크를 못찾은 경우
    }

    // HTML 요소를 처리하고 유효한 링크인지 확인
    private Element processElement(Element elt) {
        //System.out.println(elt.tagName());
        if (validLink(elt)) {
            return elt;
        }
        return null;
    }

    // 요소가 유효한 링크인지 확인
    private boolean validLink(Element elt) {
        // a 태그인지 확인
        if (!elt.tagName().equals("a")) {
            return false;
        }
        // i, em 태그인지 확인
        if (isItalic(elt)) {
            return false;
        }
        // 괄호 안에 있는지 확인
        if (isInParens(elt)) {
            return false;
        }
        // #으로 시작하는지 확인
        if (startsWith(elt, "#")) {
            return false;
        }
        // 도움말 페이지인지 확인
        if (startsWith(elt, "/wiki/Help:")) {
            return false;
        }
        return true;
    }

    // 링크가 특정 문자열로 시작하는지 확인
    private boolean startsWith(Element elt, String s) {
        //System.out.println(elt.attr("href"));
        return (elt.attr("href").startsWith(s));
    }

    // 요소에 괄호가 있는지 확인
    private boolean isInParens(Element elt) {
        return !parenthesisStack.isEmpty();
    }

    // i, em 태그가 있는지 확인
    private boolean isItalic(Element start) {
        for (Element elt=start; elt != null; elt = elt.parent()) {
            if (elt.tagName().equals("i") || elt.tagName().equals("em")) {
                return true;
            }
        }
        return false;
    }

    // 텍스트 노드를 처리하고 괄호의 균형을 확인
    private void processTextNode(TextNode node) {
        StringTokenizer st = new StringTokenizer(node.text(), " ()", true); // 내용을 공백과 괄호를 기준으로 토큰을 나눔
        while (st.hasMoreTokens()) {
            String token = st.nextToken(); // 토큰을 하나씩 받아옴
            // System.out.print(token);
            if (token.equals("(")) {
                parenthesisStack.push(token);
            }
            if (token.equals(")")) {
                if (parenthesisStack.isEmpty()) {
                    System.err.println("Warning: unbalanced parentheses.");
                }
                parenthesisStack.pop();
            }
        }
    }
}