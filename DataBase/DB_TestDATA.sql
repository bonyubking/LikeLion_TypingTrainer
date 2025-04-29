-- Users
INSERT INTO Users (uid, password, nickname) VALUES 
('user01', 'pass1234', '홍길동'),
('user02', 'pass5678', '김영희'),
('user03', 'pass9999', '이철수');

-- Game_records
INSERT INTO Game_records (user_id, duration, correct_count, typing_speed, accuracy, played_at, game_type, content_type) VALUES 
(1, '00:01:30', 45, 300, 95.5, NOW(), 'typing', 'word'),
(2, '00:02:10', 50, 280, 90.0, NOW(), 'typing', 'sentence'),
(1, '00:01:00', 40, 320, 98.0, NOW(), 'song', 'song');

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