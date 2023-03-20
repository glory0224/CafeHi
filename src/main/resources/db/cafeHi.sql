-- Oracle SQL

-- 테이블 컬럼 조회

select * from tab;
select * from cafehi_member;
select * from cafehi_member_auth;
select * from cafehi_qna;
select * from cafehi_membership;
select * from cafehi_menu;
select * from cafehi_cart;
select * from cafehi_order;
select * from cafehi_order_menu;
select * from cafehi_coupon;
select * from cafehi_mem_coupon;

commit;


-- 테이블 컬럼 타입 조회

desc cafehi_member;
desc cafehi_member_auth;
desc cafehi_qna;
desc cafehi_menu;
desc cafehi_cart;
desc cafehi_membership;
desc cafehi_order;
desc cafehi_order_menu;
desc cafehi_coupon;
desc cafehi_mem_coupon;

-- 테이블 컬럼 제거

delete cafehi_member_auth;
delete cafehi_member;
delete cafehi_membership;
delete cafehi_admin;
delete cafehi_qna;
delete cafehi_menu;
delete cafehi_cart;
delete cafehi_order;
delete cafehi_order_menu;

commit;
rollback;

update cafehi_membership set membership_point = 0 where member_code = 55;

-- 삭제
drop table cafehi_mem_coupon;
drop table cafehi_coupon;
drop table cafehi_order_menu;
drop table cafehi_order;
drop table cafehi_cart;
drop table cafehi_menu;
drop table cafehi_membership;
drop table cafehi_qna;
drop table cafehi_member_auth;
drop table cafehi_member;


------------------------------------ 테이블 시퀀스 생성 시작 -------------------------------------

-- 시퀀스의 마지막 값과 20씩 차이나는 증가 이상 현상 발견
-- 검색해보니 시퀀스는 기본적으로 캐싱을 하고 이 옵션이 켜져 있기 때문이라는 결론
-- https://bae9086.tistory.com/297 블로그 글을 참고하여 시퀀스 옵션 변경

-- 사용자 권한 시퀀스
create sequence seq_member_auth
    start with 1
    minvalue 1
    nomaxvalue
increment by 1
nocycle
nocache
order;

SELECT * FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'SEQ_MEMBER_AUTH'; -- 시퀀스 정보 조회

SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'SEQ_MEMBER_AUTH'; -- 마지막 시퀀스 조회

drop sequence seq_member_auth;

-- 사용자 시퀀스
create sequence seq_member
    start with 1
    minvalue 1
    nomaxvalue
increment by 1
nocycle
nocache
order;

SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'SEQ_MEMBER';

drop sequence seq_member;

-- 멤버쉽 시퀀스
create sequence seq_membership
    start with 1
    minvalue 1
    nomaxvalue
increment by 1
nocycle
nocache
order;

SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'SEQ_MEMBERSHIP';

drop sequence seq_membership;


-- 게시판 시퀀스
create sequence seq_qna
    start with 1
    minvalue 1
    nomaxvalue
increment by 1
nocycle
nocache
order;

SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'SEQ_QNA';

drop sequence seq_qna;


-- 메뉴 시퀀스
create sequence seq_menu
    start with 1
    minvalue 1
    nomaxvalue
increment by 1
nocycle
nocache
order;

SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'SEQ_MENU';

drop sequence seq_menu;


-- 장바구니 시퀀스 생성
create sequence seq_cart
    start with 1
    minvalue 1
    nomaxvalue
increment by 1
nocycle
nocache
order;

SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'SEQ_CART';

drop sequence seq_cart;


-- 주문 시퀀스
create sequence seq_order
    start with 1
    minvalue 1
    nomaxvalue
increment by 1
nocycle
nocache
order;

SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'SEQ_ORDER';

drop sequence seq_order;

-- 주문 메뉴 시퀀스

create sequence seq_order_menu
    start with 1
    minvalue 1
    nomaxvalue
increment by 1
nocycle
nocache
order;

SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'SEQ_ORDER_MENU';

drop sequence seq_order_menu;



------------------------- 시퀀스 생성 끝 --------------------------------------



--------------------------- 테이블 생성 시작 ------------------------------------


-- 사용자 정보 테이블
create table cafehi_member(
                              member_code number primary key, -- 사용자 코드
                              member_id varchar2(50) not null, -- 사용자 아이디
                              member_pw varchar2(100) not null, -- 사용자 비밀번호
                              member_name varchar2(50) not null, -- 사용자 이름
                              member_contact varchar2(15) not null, -- 사용자 연락처
                              member_email varchar2(50) not null, -- 사용자 계정 인증용 이메일
                              member_road_address varchar2(100), -- 사용자 도로명 주소
                              member_jibun_address varchar2(100), -- 사용자 지번 주소
                              member_detail_address varchar2(100), -- 사용자 상세 주소
                              enabled char(1) default '1', -- 스프링 시큐리티 권한 사용 여부
                              member_writetime timestamp not null, -- 사용자 등록일
                              member_updatetime timestamp not null -- 사용자 수정일
);

-- 사용자 정보 권한 테이블
create table cafehi_member_auth(
                                   member_auth_code number primary key, -- 사용자 정보 권한 코드
                                   member_code number not null, -- 사용자 정보 코드
                                   member_auth varchar2(50) not null, -- 스프링 시큐리티 사용자 권한 : ROLE_USER, ROLE_ADMIN ...
                                   member_auth_writetime timestamp not null, -- 사용자 권한 등록일
                                   member_auth_updatetime timestamp not null, -- 사용자 권한 수정일
                                   constraint fk_member_auth foreign key(member_code) references cafehi_member(member_code) on delete cascade
);




--  QnA 게시판
create table cafehi_qna(
                           qna_num number primary key, -- QnA 게시글 번호
                           qna_title varchar2(200) not null, -- QnA 게시글 제목
                           qna_title_classification varchar2(50), -- 사용자 권한별 QnA 제목 분류
                           qna_content varchar2(500) not null, -- QnA 게시글 내용
                           qna_writetime timestamp not null, -- QnA 게시글 작성일
                           qna_updatetime timestamp not null, -- QnA 게시글 수정일
                           qna_hit number not null, -- QnA 게시글 조회수
                           upload_path varchar2(500), -- QnA 게시글 업로드 경로
                           store_file_name varchar2(300), -- QnA 게시글 서버 저장 파일명
                           upload_file_name varchar2(100), -- QnA 게시글 클라이언트 저장 파일명
                           member_code number not null, -- 사용자 코드
                           constraint fk_qna_member_code foreign key(member_code) references cafehi_member (member_code) on delete cascade
);


-- 카페하이 멤버쉽
create table cafehi_membership(
                                  membership_code number primary key, -- 멤버쉽 코드
                                  member_code  number not null, -- 멤버 코드
                                  constraint fk_my_membership_member_code foreign key(member_code) references cafehi_member (member_code) on delete cascade,
                                  membership_grade varchar2(30) not null, -- 멤버쉽 등급
                                  membership_point number default 0 not null, -- 멤버쉽 포인트
                                  membership_writetime timestamp not null, -- 멤버쉽 등록일
                                  membership_updatetime timestamp not null -- 멤버쉽 수정일
);


-- 메뉴
create table cafehi_menu(
                            menu_code number primary key, -- 메뉴 코드
                            menu_price number not null, -- 메뉴 가격
                            menu_type varchar2(50) not null, -- 메뉴 종류
                            menu_name varchar2(50) not null, -- 메뉴 이름
                            menu_explain varchar2(100) not null, -- 메뉴 설명
                            menu_img_path varchar2(200) not null, -- 메뉴 이미지 경로
                            menu_stock_quantity number default 1000 not null, -- 메뉴 재고 수량
                            menu_writetime timestamp not null, -- 메뉴 등록일
                            menu_updatetime timestamp not null -- 메뉴 수정일
);

insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(01, 3000,'coffee','아메리카노', '카페하이 아메리카노', '/cafeHi/img/menu/coffee/IceAmericano.jpg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(02, 3000,'coffee','에스프레소', '카페하이 에스프레소', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(03, 4500,'coffee','유자메리카노', '카페하이 유자메리카노', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(04, 500,'coffee','샷추가', '카페하이 샷추가', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(05, 1000,'coffee','리필', '카페하이 리필', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(06, 4500,'coldbrew','콜드브루', '카페하이 콜드브루', '/cafeHi/img/menu/coffee/ColdBrew.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(07, 5000,'coldbrew','스페셜티(싱글원두)', '카페하이 스페셜티(싱글원두)', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(08, 5000,'coldbrew','콜드브루 라떼', '카페하이 콜드브루 라떼', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(09, 5500,'coldbrew','아인슈페너', '카페하이 아인슈페너', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(10, 15000,'coldbrew','콜드브루(병)', '카페하이 콜드 브루(병)', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(11, 5000,'latte','바닐라 라떼', '카페하이 바닐라 라떼', '/cafeHi/img/menu/latte/IceVinilaLatte.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(12, 6000,'latte','아이스크림 라떼', '카페하이 아이스크림 라떼', '/cafeHi/img/menu/latte/IceCreamLatte.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(13, 4700,'latte','카페모카' ,'카페하이 카페모카', '/cafeHi/img/menu/latte/IceCaffeMoca.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(14, 4500,'latte','헤이즐넛 라떼', '카페하이 헤이즐넛 라떼', '/cafeHi/img/menu/latte/HazelnutLatte.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(15, 4700,'latte','돌체 라떼', '카페하이 돌체 라떼', '/cafeHi/img/menu/latte/DolceLatte.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(16, 5000,'latte','유자비앙코', '카페하이 유자비앙코', '/cafeHi/img/menu/latte/CitronBianco.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(17, 4700,'latte','카라멜 마끼아또', '카페하이 카라멜 마끼아또', '/cafeHi/img/menu/latte/CaramelMacchiato.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(18, 4800,'smoothie','모카 프라푸치노', '카페하이 모카 프라푸치노', '/cafeHi/img/menu/smoothie/MocaFrappuccino.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(19, 4800,'smoothie','카라멜 프라푸치노', '카페하이 카라멜 프라푸치노', '/cafeHi/img/menu/smoothie/CaramelFrappuccino.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(20, 4500,'smoothie','그린티 프라푸치노', '카페하이 그린티 프라푸치노', '/cafeHi/img/menu/smoothie/greenteaFrappuccino.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(21, 4500,'smoothie','요거트 스무디', '카페하이 요거트 스무디', '/cafeHi/img/menu/smoothie/yogurtSmoothie.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(22, 5000,'smoothie','구름 요거트 스무디', '카페하이 구름 요거트 스무디', '/cafeHi/img/menu/smoothie/cloudSmoothie.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(23, 5500,'smoothie','딸기 요거트 스무디', '카페하이 딸기 요거트 스무디', '/cafeHi/img/menu/smoothie/strawberryYogurtSmoothie.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(24, 3000,'side','크로와상', '카페하이 크로와상', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(25, 5000,'side','생크림 크로플', '카페하이 생크림 크로플', '/cafeHi/img/menu/side/WhippedCreamCrople.jpg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(26, 6000,'side','브라운 치즈 크로플', '카페하이 브라운 치즈 크로플', '/cafeHi/img/menu/side/BrownCheeseCrople.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(27, 7000,'side','아이스크림 크로플', '카페하이 아이스크림 크로플', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(28, 4500,'beverage','그린티 라떼', '카페하이 그린티 라떼', '/cafeHi/img/menu/beverage/GreenTeaLatte.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(29, 4500,'beverage','초코 라떼', '카페하이 초코 라떼', '/cafeHi/img/menu/beverage/ChocolateLatte.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(30, 5000,'beverage','딸기 라떼', '카페하이 딸기 라떼', '/cafeHi/img/menu/beverage/StrawberryLatte.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(31, 5000,'beverage','베리베리 라떼', '카페하이 베리베리 라떼', '/cafeHi/img/menu/beverage/BerryBerryLatte.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(32, 4700,'beverage','패션 후르츠 에이드', '카페하이 패션 후르츠 에이드', '/cafeHi/img/menu/beverage/PassionFruitAde.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(33, 5000,'beverage','레몬에이드', '카페하이 레몬에이드', '/cafeHi/img/menu/beverage/Lemonade.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(34, 5000,'beverage','청귤에이드', '카페하이 청귤에이드', '/cafeHi/img/menu/beverage/GreenTangerineAde.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(35, 5500,'beverage','자몽에이드', '카페하이 자몽에이드', '/cafeHi/img/menu/beverage/GrapefruitAde.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(36, 5500,'beverage','블루레몬에이드', '카페하이 블루레몬에이드', '/cafeHi/img/menu/beverage/BlueLemonAde.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(37, 5000,'fruitJuice','수박 쥬스', '카페하이 수박 쥬스', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(38, 5000,'fruitJuice','토마토 쥬스', '카페하이 토마토 쥬스', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(39, 5000,'fruitJuice','바나나 쥬스', '카페하이 바나나 쥬스', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(40, 3500,'tea','카모마일 차', '카페하이 카모마일 차', '/cafeHi/img/menu/tea/chamomileTea.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(41, 3500,'tea','페퍼민트 차', '카페하이 페퍼민트 차', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(42, 3500,'tea','로즈마리 차', '카페하이 로즈마리 차', '/cafeHi/img/menu/tea/RosemaryTea.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(43, 3500,'tea','로즈플라워 차', '카페하이 로즈플라워 차', '/cafeHi/img/menu/tea/RoseFlowerTea.JPG', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(44, 3500,'tea','우롱 차', '카페하이 우롱 차', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(45, 3500,'tea','허니자몽블랙티', '카페하이 허니자몽블랙티', '/cafeHi/img/menu/tea/HoneyGrapefruitBlackTea.jpg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(46, 4500,'tea','이슬차(수국)', '카페하이 이슬차(수국)', '/cafeHi/img/menu/tea/DewTea.jpg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(47, 3500,'tea','아이스티', '카페하이 아이스티', '/cafeHi/img/menu/tea/IceTea.jpg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(48, 4500,'tea','유자차', '카페하이 유자차', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(49, 4500,'tea','오렌지자몽차', '카페하이 오렌지자몽차', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(50, 4500,'tea','생강차', '카페하이 생강차', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(51, 4500,'tea','레몬차', '카페하이 레몬차', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(52, 4500,'tea','베리차', '카페하이 베리차', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(53, 4500,'tea','청귤차', '카페하이 청귤차', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);
insert into cafehi_menu(menu_code, menu_price, menu_type, menu_name, menu_explain, menu_img_path, menu_writetime, menu_updatetime) values(54, 4500,'tea','자몽차', '카페하이 자몽차', '/cafeHi/img/cafehi_logo.jpeg', systimestamp, systimestamp);

commit;

-- 장바구니 테이블

create table cafehi_cart(
                            cart_code number not null primary key, -- 장바구니 코드
                            member_code number not null, -- 사용자 코드
                            constraint fk_cart_member_code foreign key(member_code) references cafehi_member(member_code),
                            menu_code number not null, -- 메뉴 코드
                            cart_amount number default 0, -- 장바구니 양
                            cart_writetime timestamp, -- 장바구니 등록일
                            cart_updatetime timestamp -- 장바구니 수정일
);


-- 주문
create table cafehi_order(
                             order_code number not null primary key, -- 주문코드
                             member_code number not null, -- 사용자코드
                             constraint fk_order_membercode foreign key(member_code) references cafehi_member(member_code) on delete cascade,
                             order_status varchar2(30), -- 주문 상태
                             order_writetime timestamp, -- 주문일자
                             order_updatetime timestamp, -- 수정일자
                             include_delivery char(1) -- 배송비 포함 여부
);

-- 주문 메뉴
create table cafehi_order_menu(
                                  order_menu_code number primary key, -- 주문메뉴 코드
                                  order_code number not null, -- 주문 코드
                                  constraint fk_order_menu_ordercode foreign key(order_code) references cafehi_order(order_code) on delete cascade,
                                  menu_code number not null, -- 메뉴 코드
                                  constraint fk_order_menu_menucode foreign key(menu_code) references cafehi_menu(menu_code),
                                  total_order_count number not null, -- 주문 메뉴 개수
                                  total_order_price number not null, -- 주문 가격
                                  total_order_price_point number not null, -- 주문 가격에 따른 포인트
                                  order_menu_status char(1) not null, -- 주문 메뉴 상태 (주문, 취소)
                                  order_menu_writetime timestamp, -- 주문 메뉴 등록일
                                  order_menu_updatetime timestamp -- 주문 메뉴 수정일
);
