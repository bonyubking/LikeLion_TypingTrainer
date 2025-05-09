# 멋쟁이사자처럼 프로젝트 - 🎶 타자톡톡

## 프로젝트 소개

타자톡톡은 타자 타이핑 게임과 노래 맞추기 게임으로 이루어진 게임입니다. 사용자는 채팅과 게시판을 통해 다른 사용자와 게임 팁을 공유할 수 있고 게임을 통해 타자 연습을 할 수 있습니다. 

<br><br>

## 조원 소개
|<img src="https://avatars.githubusercontent.com/u/182395987?v=4" width="120px">|<img src="https://avatars.githubusercontent.com/u/201211576?v=4" width="120px">|<img src="https://avatars.githubusercontent.com/u/62700196?v=4" width="120px">|<img src="https://avatars.githubusercontent.com/u/96824438?v=4" width="120px">|<img src="https://avatars.githubusercontent.com/u/128500043?v=4" width="120px">|
|:--:|:--:|:--:|:--:|:--:|
|[**구본엽**](https://github.com/bonyubking)|[**김채연**](https://github.com/yeon47)|[**김희연**](https://github.com/Happy-Lotus)|[**최은수**]([https://github.com/yeonmorae](https://github.com/RE4LN4ME))|[**박재엽**](https://github.com/hakki93)|
|`게시판 및 댓글`|`타자 연습 게임`|`회원관리 및 실시간 채팅`|`노래 맞추기 게임`|`메인 페이지 UI`|

<br><br>

## 기술 스택
<img src="https://img.shields.io/badge/JAVA-437291?style=for-the-badge&logo=OpenJDK&logoColor=white"/> <br>
<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white"/> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white"/> <br>
<img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=Figma&logoColor=white"/>
<img src="https://img.shields.io/badge/Eclipse IDE-2C2255?style=for-the-badge&logo=EclipseIde&logoColor=white"/> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=GitHub&logoColor=white"/> <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"/> 


<br><br>

## 기능
### 1. 로그인/회원가입

### 🔐 로그인 기능 <br>
- 사용자가 입력한 아이디와 비밀번호를 데이터베이스와 비교하여 인증을 진행합니다.
- 입력한 아이디가 DB에 존재할 경우, 비밀번호 일치 여부를 검사합니다.
- 인증에 성공하면 "로그인 성공" 알림과 함께 메인 화면으로 이동합니다.

### 📝 회원가입 기능 <br>
- 사용자가 입력한 아이디와 닉네임은 중복 여부를 먼저 확인하여 이미 존재하는 아이디와 닉네임으로는 가입할 수 없도록 처리합니다.
- 사용자가 입력한 정보(아이디, 비밀번호 등)를 검증한 후, 데이터베이스에 저장합니다.
- 가입 완료 후 성공 메시지를 표시하고 로그인 페이지로 이동합니다.

---

### 2. 채팅

### 💬 실시간 채팅 (WebSocket) <br>
- WebSocket 프로토콜을 이용해 서버-클라이언트 간 실시간 양방향 통신이 가능합니다.
- 사용자는 로그인하자마자 자동으로 연결되며, 메시지를 주고받을 수 있습니다.
- 서버는 다수의 클라이언트와 동시 통신이 가능합니다.
- 사용자가 메시지를 입력하고 전송 버튼을 누르면, 상대방 화면에 즉시 표시됩니다.


---

### 3. 게시판 및 댓글 
### 🗂 게시판 & 💬 댓글 기능
### 📌 게시판
- 사용자들이 자유롭게 글을 작성하고 조회수, 댓글수에 따라 정렬해서 볼 수 있습니다.
- 게시글 목록은 최신순 정렬 및 페이징 처리로 효율적으로 관리합니다.
- 게시글 상세 페이지에서 내용, 작성자, 작성일 등을 확인할 수 있습니다.

### 💭 댓글
- 각 게시글에 대해 댓글을 작성할 수 있습니다.

---

### 4. 타자 연습 게임

### 🎮 타자 연습 게임
- 난이도, 언어, 종류별 타자 연습 게임을 수행할 수 있습니다.
- 문제당 주어진 시간 안에 주어진 문제를 타이핑합니다.
- 각 모드별 문제는 자동으로 출제되며 채점됩니다. 
- 비로그인 사용자는 게임 기록이 저장되지 않습니다.
- 게임이 완료되면 게임 기록 페이지에서 기록을 확인할 수 있습니다.

---

### 5. 노래 맞추기 게임
### 🎵 노래 맞추기 게임
- 난이도, 노래 종류별 노래 게임을 수행할 수 있습니다.
- 문제당 주어진 시간 안에 노래 가사와 힌트를 조합하여 제목을 입력합니다.
- 비로그인 사용자는 게임 기록이 저장되지 않습니다.
- 게임이 완료되면 게임 기록 페이지에서 기록을 확인할 수 있습니다.

---


### 6. 게임 기록 확인 
### 📊 게임 기록 확인
- 사용자가 플레이한 타자 연습 게임, 노래 맞추기 게임의 기록들을 확인할 수 있습니다.
- 내 기록, 전체 기록, 타자 연습 게임, 노래 맞추기 게임 필터가 존재하여 필터링해서 기록을 확인하실 수 있습니다. 




<br><br>

## 프로젝트 설치 및 실행 

### 1. 프로젝트 클론
```
git clone https://github.com/사용자명/레포명.git
```
### 2. 패키지 설치
- 2-1. Client
  ```
  cd react-typingtrainer
  npm install
  ```

- 2-2. Server


  아래의 JAR 파일들을 다운로드한 후 Build Path 설정합니다.
  ```
  `프로젝트 > 마우스 우클릭 > Build Path > Configure Build Path...`에서 아래와 같이 설정합니다:

  - Modulepath에 추가:
    - `mysql-connector-j-9.1.0.jar`

  - Classpath에 추가:
    - gson-2.12.1.jar
    - Java-WebSocket-1.6.0.jar
    - slf4j-api-2.0.17.jar
  ```

  | 라이브러리 | 용도 | 다운로드 |
  |------------|------|-----------|
  | `mysql-connector-j-9.1.0.jar` | MySQL 연결용 드라이버 | [다운로드 링크](https://downloads.mysql.com/archives/c-j/) |
  | `gson-2.12.1.jar` | JSON 파싱 | [다운로드 링크](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.12.1) |
  | `slf4j-api-2.0.17.jar` | 로그 처리용 | [다운로드 링크](https://mvnrepository.com/artifact/org.slf4j/slf4j-api/2.0.17) |
  | `Java-WebSocket-1.6.0.jar` | WebSocket 통신용 | [다운로드 링크](https://mvnrepository.com/artifact/org.java-websocket/Java-WebSocket/1.6.0) |
  

### 3. Java & React Server Run
```
React 실행
npm start

Server 실행
상단의 실행 버튼을 클릭하여 서버를 실행
```

<br>

### 설정
1. resources 폴더 설정 <br>
   이프로젝트에서는 데이터베이스 연결 정보를 resources 폴더에 위치한 db.properties 파일에서 관리합니다. Eclipse에서 resources 폴더를 설정하는 방법은 다음과 같습니다.
   ```
   1. 프로젝트 내 src 폴더와 같은 위치에 resources 폴더를 생성합니다.
   2. resources 폴더를 마우스 오른쪽 버튼으로 클릭한 후, Build Path > Use as Source Folder를 선택합니다.
   3. 생성한 resources 폴더에 db.properties 파일을 추가하고 아래와 같이 작성합니다.

   예시)
   driver = com.mysql.cj.jdbc.Driver
   url = jdbc:mysql://localhost:3306/스키마명
   user = 
   password = 
   ```

   <br>
   
2. .env 파일 설정 <br>
   클라이언트에서 민감한 정보를 관리하기 위해 .env 파일을 설정해야합니다. 루트 경로에 생성해야합니다. 내용은 다음과 같습니다.
   ```
   REACT_APP_HTTP_URL = http://localhost:8080
   REACT_APP_CHAT_URL = http://localhost:8081
   REACT_APP_WEBSOCKET_URL = ws://localhost:8081
   ```
<br>

3. 데이터베이스 데이터 설정 <br>
  - 데이터는 `DataBase` 폴더의 스크립트를 사용해야 합니다. <br>
  - `DataBase` > `DB_TypingTrainer.sql` 을 이용해 테이블을 생성합니다.
  - `DataBase` > `DB_TESTDATA.sql` 을 이용해 Users, Game_records, Posts, Comments, Chats 테이블의 샘플 데이터를 설정합니다.
  - `DataBase` > `csv_files` 에 있는 csv 파일을 이용하여 sentences, words, songs 테이블의 데이터를 저장합니다. 속성과 csv 파일의 각 열 명은 다음과 같이 매치됩니다. 
    
    - difficulty : 난이도
    -  content : 문장 또는 단어
    - language : 언어
    - lyrics : 가사
    - singer : 가수 또는 작곡가/작사가
    - initial : 제목 초성


<br>

4. Jar 파일 서버 실행할 경우
     ```
     cd .jar파일 저장된 곳
     Java -jar 파일명.jar
     ```

