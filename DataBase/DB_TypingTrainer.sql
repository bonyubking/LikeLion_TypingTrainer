CREATE SCHEMA typingtrainer;
USE typingtrainer;

CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    uid VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50) NOT NULL
);

-- 2. 게임 기록 테이블
CREATE TABLE Typing_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    duration TIME,
    correct_count INT,
    typing_speed INT,
    accuracy DECIMAL(5,2),
    played_at DATETIME,
	content_type ENUM('sentence', 'word'),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE Song_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    duration TIME,
    correct_count INT,
    played_at DATETIME,
    genre VARCHAR(50),
    hint_time INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);


-- 3. 노래 테이블
CREATE TABLE Songs (
    song_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    lyrics TEXT,
    singer VARCHAR(50),
    initial VARCHAR(50),
    genre VARCHAR(50)
);

-- 4. 문장 테이블
CREATE TABLE Sentences (
    sentence_id INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    difficulty ENUM('상', '중', '하'),
    language ENUM('한', '영')
);

-- 5. 단어 테이블
CREATE TABLE Words (
    word_id INT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(100) NOT NULL,
    difficulty ENUM('상', '중', '하'),
    language ENUM('한', '영')
);

-- 6. 게시글 테이블
CREATE TABLE Comments (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(100),
    post_id INT NOT NULL,
    content TEXT,
    created_at DATETIME,
    view_count INT DEFAULT 0,
    comment_count INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
	FOREIGN KEY (post_id) REFERENCES Posts(post_id)
);

-- 7. 댓글 테이블
CREATE TABLE Posts (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(100),
    content TEXT,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
    );

-- 8. 채팅 내용 테이블
CREATE TABLE Chats (
    chat_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    content TEXT,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
