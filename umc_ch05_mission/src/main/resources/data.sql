-- 재시작 시 중복 삽입 방지를 위해 INSERT IGNORE 사용

INSERT IGNORE INTO region (id, name) VALUES
    (1, '안암동'),
    (2, '신촌'),
    (3, '홍대');

INSERT IGNORE INTO store (id, name, address, score, region_id) VALUES
    (1, '맛있는 치킨집', '안암동 123-4', 4.5, 1),
    (2, '신촌 파스타',   '신촌동 456-7', 4.2, 2),
    (3, '홍대 카페',     '서교동 789-0', 4.8, 3);

INSERT IGNORE INTO member (id, name, email, password, phone_number, points, gender, social_type, region_id) VALUES
    (1, '김수빈', 'test@test.com', 'password123', '010-1234-5678', 300, 'FEMALE', 'NONE', 1),
    (2, '이철수', 'lee@test.com',  'password456', '010-9876-5432', 150, 'MALE',   'NONE', 2);

INSERT IGNORE INTO mission (id, reward, min_price, mission_spec, store_id) VALUES
    (1, 500,  15000, '15,000원 이상 주문하기',    1),
    (2, 300,  10000, '포장 주문으로 10,000원 이상', 1),
    (3, 400,  12000, '파스타 2인 세트 주문하기',   2);

INSERT IGNORE INTO member_mission (id, status, member_id, mission_id) VALUES
    (1, 'ONGOING',  1, 1),
    (2, 'COMPLETE', 1, 2),
    (3, 'ONGOING',  2, 3);
