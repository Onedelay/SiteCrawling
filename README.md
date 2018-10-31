## Site Crawling Example

<br>

### 1. 이미지 목록 불러오기 (MainActivity)

웹사이트에 있는 이미지들을 크롤링하여 앱의 리사이클러뷰에 띄워준다.

Image source : http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx

<br>

#### 스크린샷

![](/screenshots_main.jpg) 

<br>

#### 파싱한 방법

`Jsoup` 라이브러리를 활용하여 웹페이지의 `html`을 파싱한다.

- gradle에 `implementation 'org.jsoup:jsoup:1.11.2'` 추가하기

- AsyncTask 클래스 정의하기 (Jsoup 파싱은 worker thread 에서만 동작)

  - 주의사항 : html 에서 클래스명에 **공백(whitespace)이 포함되어있을 경우 . 로 교체**

  ```kotlin
  val doc = Jsoup.connect(url).get()
  val elements = doc.select("div.gallery-item-group.exitemrepeater")
  for (element in elements) {
      data.add(ImageItem(
          element.select("div.gallery-item-caption").text().trim(), 	// 이미지 제목
          element.select("img").attr("abs:src").trim()))              // 이미지 썸네일
  ```

<br>

#### To do list

- ~~inner class 로 선언한 AsyncTask 클래스 warning 제거~~
- gradle 정리
- 가능하다면 페이징 구현해보기(왠지 불가능할 것 같긴 하다.)

<br>

<br>

### 2. 네이버 뉴스 (NewsActivity)

네이버 뉴스 페이지 왼쪽 상단에 있는 가장 많이 본 뉴스 리스트를 가져와 보여준다.

클릭시 해당 뉴스기사를 볼 수 있다.

Data source : https://news.naver.com/

<br>

#### 스크린샷

![](/screenshots_news.jpg) 

<br>
#### To do list

- fragment 생성 방식 바꾸기
- NewsActivity static 멤버 제거
- 다음 뉴스 추가하기
