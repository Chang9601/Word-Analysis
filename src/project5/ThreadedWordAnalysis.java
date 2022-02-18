package project5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadedWordAnalysis {
	
	public static final int SIZE = 8000000; // 스레드의 개수를 구하기 위한 크기
	
	public static void main(String[] args) throws IOException, InterruptedException {
		long startTime = System.nanoTime();
		
		String path = "C:" + File.separator + "javawork" + File.separator + "books.txt"; // 파일 위치
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8")); // 문자 깨짐 방지
		
		// 단어 분석 변수
		String line = null;	// 파일의 줄
		Vector<String> tmpLineList = new Vector<String>(); // 파일의 줄을 저장하는 임시 배열
		Vector<String> lineList; // 파일의 줄을 저장하는 배열
		List<WordThread> threadList = new ArrayList<WordThread>(); // 스레드를 저장하는 배열
		Thread[] threads = null;
		ConcurrentHashMap<String, Integer> wordFrequency = new ConcurrentHashMap<String, Integer>(); // 스레드에 안전한 해시, 단어의 개수 저장
		String longestWord, mostCommonWord;
		int longestWordLen, mostCommonWordCount, totalWordCount, totalWordLen;
		int tot = 0;
		
		while((line = br.readLine()) != null) {
			// 줄의 개수가 크기보다 작을 경우
			if(tmpLineList.size() < SIZE) { 
				tmpLineList.add(line);
				continue;
			}
			
			// 배열 깊은 복사
			lineList = new Vector<String>();
			for(String element : tmpLineList) lineList.add(element);
			tot += lineList.size();
			
			// 스레드를 배열에 저장
			threadList.add(new WordThread(wordFrequency, lineList));
			
			// 임시 배열 초기화
			tmpLineList.clear();
			
			// 경계 값 삽입
			tmpLineList.add(line);
		}
		
		// 아직 처리해야 할 줄이 남아있음
		if(tmpLineList.size() > 0) {
			lineList = new Vector<String>();
			for(String element : tmpLineList) lineList.add(element);		
			tot += lineList.size();

			threadList.add(new WordThread(wordFrequency, lineList));
		}
		
		// 스레드 실행
		threads = new Thread[threadList.size()];
		for(int i = 0; i < threadList.size(); i++) {
			threads[i] = new Thread(threadList.get(i));
			threads[i].start();
		}
		
		// 스레드 순서
		for(int i = 0; i < threadList.size(); i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
		System.out.println("스레드의 개수: " + threads.length);		
		System.out.println("줄의 개수: " + tot);
		System.out.println("해시의 크기: " + wordFrequency.size());
		
		// 변수 초기화
		longestWord = mostCommonWord = null;
		totalWordCount = totalWordLen = 0;
		longestWordLen = mostCommonWordCount = Integer.MIN_VALUE;		

		Set<Entry<String, Integer>> entrySet = wordFrequency.entrySet();
		for(Entry<String, Integer> entry : entrySet) {
			String word = entry.getKey();
			int wordCount = entry.getValue();
			int wordLen = word.length();
			
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
		
		System.out.println("작업 시간: " + timeSpent + "초");
		System.out.println("총 단어의 개수: " + totalWordCount);
		System.out.println("총 단어의 길이: " + totalWordLen);
		System.out.println("단어 길이의 평균: " + 1.0 *  totalWordLen / totalWordCount);
		System.out.println("최빈도 단어: " + mostCommonWord + ", 횟수: " + mostCommonWordCount);
		System.out.println("가장 긴 단어: " + longestWord + ", 길이: " + longestWord.length());		
		
		br.close();
	}
}