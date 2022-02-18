# 단어 분석

## 소개
단일스레드와 멀티스레드를 사용해서 텍스트 파일의 단어 분석 시 두 방법의 성능 비교하는 프로그램

## 목적
* 단일스레드와 멀티스레드의 성능을 비교해서 멀티스레드의 장단점을 파악한다.
* 멀티스레드 사용 시 공유 자원 때문에 임계구역에서 발생할 수 있는 경쟁 조건을 이해한다.

## 개발 환경
* IDE
  * Eclipse 4.22.0
* Language
  * JavaSE-17
* OS
  * Windows 10 64-bit

## 제작 기간
2022.02.14 - 2022.02.18

## 개발 인원
1명

## 기능
* 주어진 텍스트 파일에서 가장 긴 단어, 가장 많이 나오는 단어, 단어의 개수, 단어의 평균 길이를 표시
* 단일스레드와 멀티스레드의 처리 속도를 비교 표시

## 사진
단일스레드

![단일스레드](https://user-images.githubusercontent.com/79137839/154618605-8a2de561-e471-4057-a72c-8b44f7140f37.PNG)

멀티스레드

![멀티스레드](https://user-images.githubusercontent.com/79137839/154618603-346f7526-556f-48c9-b879-8dee8b957d0d.PNG)


## 파일에 저장된 책(Project Gutenberg)
1. Frankenstein; Or, The Modern Prometheus by Mary Wollstonecraft Shelley
2. Pride and Prejudice by Jane Austen 
3. Alice's Adventures in Wonderland by Lewis Carroll
4. The Scarlet Letter by Nathaniel Hawthorne
5. The Great Gatsby by F. Scott Fitzgerald
6. A Modest Proposal by Jonathan Swift
7. The Adventures of Sherlock Holmes by Arthur Conan Doyle
8. The Yellow Wallpaper by Charlotte Perkins Gilman
9. A Tale of Two Cities by Charles Dickens
10. A Doll's House : a play by Henrik Ibsen


## 회고
* 프로젝트 시작 전 멀티스레드가 작업의 분할시키기 때문에 성능이 더 좋을 것이라 가정했다.
* 하지만 가정과 반대로 멀티스레드가 더 느렸다. 
* 멀티스레드가 단일스레드 보다 느린 이유
   1. 멀티스레드가 항상 속도를 향상시키지 않는다. 일반적으로 스레드가 병렬적으로 사용될 수 있는 하드웨어 자원에 접근할 경우 속도가 향상된다.
   2. 동기화가 많을수록 병렬성은 떨어지기 때문에 성능에 악영향을 준다.
   3. 스레드 사이에 발생하는 문맥교환에 따른 오버헤드
   4. 스레드 생성 시 발생하는 오버헤드



## 출처
Stack Overflow, "multithread runs slower than single threadin", https://stackoverflow.com/questions/46759930/multithreading-slower-than-single-threading, 2022-02-18

Stack Overflow, "multithread runs slower than single threadin", https://stackoverflow.com/questions/36684832/is-multithreading-faster-than-single-thread, 2022-02-18

Stack Overflow, "multithread runs slower than single threadin", https://stackoverflow.com/questions/34589623/multithread-runs-slower-than-single-process, 2022-02-18

Project GutenBerg, https://www.gutenberg.org/browse/scores/top
