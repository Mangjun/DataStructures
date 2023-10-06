package solution;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiFetcher {
    private long lastRequestTime = -1; // 마지막 요청 시간을 저장
    private long minInterval = 1000; // 다음 요청까지 기다려야 하는 최소 시간 간격을 설정

    // 주어진 URL을 사용하여 페이지에서 정보를 가져옴
    public Elements fetchWikipedia(String url) throws IOException {
        sleepIfNeeded();

        Connection conn = Jsoup.connect(url); // 웹 페이지에 연결
        Document doc = conn.get(); // 페이지의 내용을 가져옴

        Element content = doc.getElementById("mw-content-text"); // id가 mw-content-text 인 HTML 요소를 찾음

        Elements paras = content.select("p"); // <p> 태그를 가진 요소들을 선택
        return paras;
    }

    // URL을 받아서 페이지의 정보를 가져옴
    public Elements readWikipedia(String url) throws IOException {
        URL realURL = new URL(url); // 주어진 URL을 이용하여 실제 URL 객체를 생성

        String filename = realURL.getHost() + realURL.getPath(); // URL에서 호스트와 경로를 추출하여 파일 이름을 생성

        InputStream stream = WikiFetcher.class.getClassLoader().getResourceAsStream(filename); // 클래스의 리소스로부터 해당 파일을 읽어옴
        Document doc = Jsoup.parse(stream, "UTF-8", filename); // HTML 문서를 파싱하고, 파일 이름을 UTF-8로 인코딩

        Element content = doc.getElementById("mw-content-text"); // HTML 문서에서 mw-content-text 라는 id를 가진 요소를 찾음 
        Elements paras = content.select("p"); // <p> 태그를 가진 요소들을 선택
        return paras;
    }

    // 요청 간격을 조절하기 위해 사용
    private void sleepIfNeeded() {
        if (lastRequestTime != -1) {
            long currentTime = System.currentTimeMillis(); // 현재 시간을 가져옴
            long nextRequestTime = lastRequestTime + minInterval; // 다음 요청 시간을 계산
            if (currentTime < nextRequestTime) {
                try {
                    //System.out.println("Sleeping until " + nextRequestTime);
                    Thread.sleep(nextRequestTime - currentTime); // 다음 요청 시간까지 대기
                } catch (InterruptedException e) {
                    System.err.println("Warning: sleep interrupted in fetchWikipedia.");
                }
            }
        }
        lastRequestTime = System.currentTimeMillis(); // 현재 시간을 마지막 요청 시간으로 업데이트
    }

    public static void main(String[] args) throws IOException {
        WikiFetcher wf = new WikiFetcher();
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        Elements paragraphs = wf.readWikipedia(url);

        for (Element paragraph: paragraphs) {
            System.out.println(paragraph);
        }
    }
}