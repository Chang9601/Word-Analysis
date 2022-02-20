package project5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class NonThreadedWordAnalysis {
	
	// 알파벳으로만 구성된 문자열로 처리
	public static void processWords(String word, Map<String, Integer> wordFrequency) {
		word = word.replaceAll("[^a-zA-Z]", ""); // 알파벳이 아니면 제거
		
		if(!word.equals("")) { // 알파벳으로만 구성된 문자열일 경우 
			int wordCount = 0;
			if(wordFrequency.get(word) != null) wordCount = wordFrequency.get(word); // Integer가 null일 경우 예외처리, 해시에 없는 단어일 경우
			wordFrequency.put(word, wordCount + 1); 		
		}
	}
	
	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		
		String path = "." + File.separator + "books.txt"; // 파일 위치
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8")); // 문자 깨짐 방지
		
		// 단어 분석 변수
		String line = null;	// 파일의 줄
		String longestWord, mostCommonWord;
		int longestWordLen, mostCommonWordCount, totalWordCount, totalWordLen;
		Map<String, Integer> wordFrequency = new HashMap<String, Integer>(); // 단어의 개수를 저장하는 해시
		int numlines = 0;
		
		while((line = br.readLine()) != null) {
			numlines++;
			line = line.strip(); // 양쪽 공백 제거
			String[] words = line.split(" "); // 공백 단위 파싱
			
			for(String word : words) {
				// 단어가 '-', '.', '/', '—'로 연결되면 추가 파싱 필요
				String[] wordWords = word.split("[-./—]"); // 정규식을 매개변수
				
				// if-else 분리
				if(wordWords != null) // 위의 문자가 단어에 존재 
					for(String w : wordWords)
						processWords(w, wordFrequency);
				else	processWords(word, wordFrequency);
			}
		}
		
		// 변수 초기화
		longestWord = mostCommonWord = null;
		totalWordCount = totalWordLen = 0;
		longestWordLen = mostCommonWordCount = Integer.MIN_VALUE;
		
		Set<Entry<String, Integer>> entrySet = wordFrequency.entrySet();		
		for(Entry<String, Integer> entry : entrySet) {
			String word = entry.getKey(); // 단어 
			int wordCount = entry.getValue(); // 단어의 개수
			int wordLen = word.length(); // 단어의 길이
			
			// 총 단어의 개수
			totalWordCount += wordCount;
			
			// 총 단어의 길이
			totalWordLen += wordLen * wordCount;
			
			// 빈도가 가장 높은 단어
			if(wordCount > mostCommonWordCount) {
				mostCommonWordCount = wordCount;
				mostCommonWord = word;
			}
			
			// 가장 긴 단어
			if(wordLen > longestWordLen) {
				longestWordLen = wordLen;
				longestWord = word;
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // 밀리초
		double timeSpent = 1.0 * duration / 1000; // 초
		
		System.out.println("줄의 개수: " + numlines);
		System.out.println("해시의 크기: " + wordFrequency.size());
		System.out.println("작업 시간: " + timeSpent + "초");
		System.out.println("총 단어의 개수: " + totalWordCount);
		System.out.println("총 단어의 길이: " + totalWordLen);
		System.out.println("단어 길이의 평균: " + 1.0 *  totalWordLen / totalWordCount);
		System.out.println("최빈도 단어: " + mostCommonWord + ", 횟수: " + mostCommonWordCount);
		System.out.println("가장 긴 단어: " + longestWord + ", 길이: " + longestWord.length());
		
		br.close();
	}
}