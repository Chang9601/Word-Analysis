package project5;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class WordThread implements Runnable {

	private ConcurrentHashMap<String, Integer> wordFrequency;
	private Vector<String> lineList;

	public WordThread(ConcurrentHashMap<String, Integer> wordFrequency, Vector<String> lineList) {
		this.wordFrequency = wordFrequency;
		this.lineList = lineList;
	}

	private void processWords(String word, ConcurrentHashMap<String, Integer> wordFrequency) {
		// 조회와 삽입을 동시에 하기 때문에 스레드 1개만 실행, 따라서 동기화 필요
		synchronized (wordFrequency) {

			word = word.replaceAll("[^a-zA-Z]", "");

			if (!word.equals("")) {
				int wordCount = 0;
				if (wordFrequency.get(word) != null)
					wordCount = wordFrequency.get(word); // Integer가 null일 경우 예외처리
				wordFrequency.put(word, wordCount + 1);
			}
		}
	}

	@Override
	public void run() {
		for (String line : lineList) {
			line = line.strip(); // 양쪽 공백 제거
			String[] words = line.split(" "); // 공백 단위로 구분

			for (String word : words) {
				// 단어가 '-', '.', '/', '—'로 연결되면 추가 파싱 필요
				String[] wordWords = word.split("[-./—]"); // 정규식을 매개변수

				// if-else 분리
				if (wordWords != null) // 위의 문자가 단어에 존재
					for (String w : wordWords)
						processWords(w, wordFrequency);
				else
					processWords(word, wordFrequency);
			}
		}
	}
}
