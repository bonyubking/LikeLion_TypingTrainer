-- Users
INSERT INTO users (uid, password, nickname) VALUES 
('user01', 'pass1234', '홍길동'),
('user02', 'pass5678', '김영희'),
('user03', 'pass9999', '이철수');

-- 1) Typing_records 에 테스트 데이터 3개
INSERT INTO Typing_records
  (user_id, duration, correct_count, typing_speed, accuracy, played_at, content_type, difficulty, language)
VALUES
  (1, '00:01:00',  60, 200, 95.50, '2025-05-05 10:00:00', 'sentence','상','영'),
  (2, '00:02:00',  120, 180, 90.00, '2025-05-05 11:00:00', 'word','중','한'),
  (3, '00:05:00',  300, 250, 97.00, '2025-05-05 12:00:00', 'sentence','하','영'),
    (2, '00:02:00',  120, 180, 90.00, '2025-05-05 11:00:00', 'word','중','한'),
      (2, '00:02:00',  120, 180, 90.00, '2025-05-05 11:00:00', 'word','상','한'),
        (2, '00:02:00',  120, 180, 90.00, '2025-05-05 11:00:00', 'word','상','한'),
          (2, '00:02:00',  120, 180, 90.00, '2025-05-05 11:00:00', 'word','중','한'),
            (2, '00:02:00',  120, 180, 90.00, '2025-05-05 11:00:00', 'word','하','영'),
(3, '00:05:00',  300, 250, 97.00, '2025-05-05 12:00:00', 'sentence','하','영');


-- 2) Song_records 에 테스트 데이터 3개
INSERT INTO Song_records
  (user_id, duration, correct_count, played_at, genre, hint_time)
VALUES
  (1, '00:01:00', 60, '2025-05-05 10:30:00', 'POP', 15),
  (2, '00:02:00', 120, '2025-05-05 11:15:00', 'KPOP', 30),
  (3, '00:05:00', 300, '2025-05-05 12:20:00', 'KKIDS', 45);

-- Posts
INSERT INTO Posts (user_id, title, content, created_at) VALUES 
(1, '첫 게시글입니다', '안녕하세요. 타자 연습 앱 재미있네요!', NOW()),
(2, '질문 있어요', '게임 기록은 어디서 확인하나요?', NOW()),
(3, '버그 제보', '단어 모드에서 오타가 발생합니다.', NOW());

-- Comments
INSERT INTO Comments (user_id, post_id, content, created_at) VALUES 
(2, 1, '저도 그렇게 생각해요!', NOW()),
(1, 2, '내 기록은 마이페이지에서 확인할 수 있어요.', NOW()),
(3, 3, '저도 같은 버그 겪었어요.', NOW());

-- Chats
INSERT INTO Chats (user_id, content, created_at) VALUES 
(1, '안녕하세요~', NOW()),
(2, '오늘도 연습 중이에요!', NOW()),
(3, '다들 몇 점 나오세요?', NOW());