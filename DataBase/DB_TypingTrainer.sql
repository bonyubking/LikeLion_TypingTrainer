USE typingtrainer;

CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50) NOT NULL
);

-- 2. 게임 기록 테이블
CREATE TABLE Game_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    duration TIME,
    correct_count INT,
    typing_speed INT,
    accuracy DECIMAL(5,2),
    played_at DATETIME,
    is_sentence BOOLEAN,         -- TRUE: 문장, FALSE: 단어
    is_korean BOOLEAN,           -- TRUE: 한글, FALSE: 영어
    genre VARCHAR(50),
    game_type ENUM('song', 'typing'),
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- 3. 노래 테이블
CREATE TABLE Songs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    lyrics TEXT,
    genre VARCHAR(50)
);

-- 4. 문장 테이블
CREATE TABLE Sentences (
    id INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    difficulty ENUM('상', '중', '하'),
    language ENUM('한', '영'),
    genre VARCHAR(50)
);

-- 5. 단어 테이블
CREATE TABLE Words (
    id INT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(100) NOT NULL,
    difficulty ENUM('상', '중', '하'),
    language ENUM('한', '영'),
    genre VARCHAR(50)
);

-- 6. 게시글 테이블
CREATE TABLE Comments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(100),
    content TEXT,
    created_at DATETIME,
    view_count INT DEFAULT 0,
    comment_count INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- 7. 댓글 테이블
CREATE TABLE Post (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    comment_id INT NOT NULL,
    content TEXT,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (comment_id) REFERENCES Comments(id)
);

-- 8. 채팅 내용 테이블
CREATE TABLE Chats (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    content TEXT,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);
