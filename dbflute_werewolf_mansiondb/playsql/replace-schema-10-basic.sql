

/* Create Tables */

CREATE TABLE AUTHORITY
(
	AUTHORITY_CODE VARCHAR(10) NOT NULL COMMENT '権限コード',
	AUTHORITY_NAME VARCHAR(20) NOT NULL COMMENT '権限名',
	PRIMARY KEY (AUTHORITY_CODE)
) COMMENT = '権限';


CREATE TABLE CAMP
(
	CAMP_CODE VARCHAR(20) NOT NULL COMMENT '陣営コード',
	CAMP_NAME VARCHAR(20) NOT NULL COMMENT '陣営名',
	PRIMARY KEY (CAMP_CODE)
) COMMENT = '陣営';


CREATE TABLE CHARA
(
	CHARA_ID INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'キャラクターID',
	CHARA_NAME VARCHAR(40) NOT NULL COMMENT 'キャラクター名',
	CHARA_GROUP_ID INT UNSIGNED NOT NULL COMMENT 'キャラクターグループID',
	CHARA_IMG_URL VARCHAR(100) NOT NULL COMMENT 'キャラクター画像URL : コンテキストパス以降',
	PRIMARY KEY (CHARA_ID)
) COMMENT = 'キャラクター';


CREATE TABLE CHARA_GROUP
(
	CHARA_GROUP_ID INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'キャラクターグループID',
	CHARA_NAME VARCHAR(40) NOT NULL COMMENT 'キャラクターグループ名',
	DESIGNER_ID INT UNSIGNED NOT NULL COMMENT 'デザイナーID',
	PRIMARY KEY (CHARA_GROUP_ID)
) COMMENT = 'キャラクターグループ';


CREATE TABLE DESIGNER
(
	DESIGNER_ID INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'デザイナーID',
	DESIGNER_NAME VARCHAR(40) NOT NULL COMMENT 'デザイナー名',
	PRIMARY KEY (DESIGNER_ID)
) COMMENT = 'デザイナー';


CREATE TABLE MESSAGE
(
	MESSAGE_ID INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'メッセージID',
	VILLAGE_ID INT UNSIGNED NOT NULL COMMENT '村ID',
	VILLAGE_PLAYER_ID INT UNSIGNED COMMENT '村参加者ID',
	PLAYER_ID INT UNSIGNED COMMENT 'プレイヤーID',
	TO_VILLAGE_PLAYER_ID INT UNSIGNED COMMENT 'どの村参加者に',
	DAY INT UNSIGNED NOT NULL COMMENT '何日目か',
	MESSAGE_TYPE_CODE VARCHAR(20) NOT NULL COMMENT 'メッセージ種別コード',
	MESSAGE_NUMBER INT UNSIGNED COMMENT 'メッセージ番号',
	MESSAGE_CONTENT VARCHAR(1000) NOT NULL COMMENT 'メッセージ内容',
	MESSAGE_DATETIME DATETIME NOT NULL COMMENT 'メッセージ日時',
	REGISTER_DATETIME DATETIME NOT NULL COMMENT '登録日時',
	REGISTER_TRACE VARCHAR(64) NOT NULL COMMENT '登録トレース',
	UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新日時',
	UPDATE_TRACE VARCHAR(64) NOT NULL COMMENT '更新トレース',
	PRIMARY KEY (MESSAGE_ID)
) COMMENT = 'メッセージ';


CREATE TABLE MESSAGE_TYPE
(
	MESSAGE_TYPE_CODE VARCHAR(20) NOT NULL COMMENT 'メッセージ種別コード',
	MESSAGE_TYPE_NAME VARCHAR(20) NOT NULL COMMENT 'メッセージ種別名',
	PRIMARY KEY (MESSAGE_TYPE_CODE)
) COMMENT = 'メッセージ種別';


CREATE TABLE PLAYER
(
	PLAYER_ID INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'プレイヤーID',
	PLAYER_NAME VARCHAR(12) NOT NULL UNIQUE COMMENT 'プレイヤーID',
	PLAYER_PASSWORD CHAR(60) NOT NULL COMMENT 'プレイヤーパスワード',
	AUTHORITY_CODE VARCHAR(10) NOT NULL COMMENT '権限コード',
	REGISTER_DATETIME DATETIME NOT NULL COMMENT '登録日時',
	REGISTER_TRACE VARCHAR(64) NOT NULL COMMENT '登録トレース',
	UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新日時',
	UPDATE_TRACE VARCHAR(64) NOT NULL COMMENT '更新トレース',
	PRIMARY KEY (PLAYER_ID)
) COMMENT = 'プレイヤー';


CREATE TABLE SKILL
(
	SKILL_CODE VARCHAR(20) NOT NULL COMMENT '役職コード',
	SKILL_NAME VARCHAR(20) NOT NULL COMMENT '役職名',
	 CAMP_CODE VARCHAR(20) NOT NULL COMMENT '陣営コード',
	PRIMARY KEY (SKILL_CODE)
) COMMENT = '役職';


CREATE TABLE VILLAGE
(
	VILALGE_ID INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '村ID',
	VILLAGE_DISPLAY_NAME VARCHAR(40) NOT NULL COMMENT '村表示名',
	VILLAGE_STATUS_CODE VARCHAR(20) NOT NULL COMMENT '村ステータスコード',
	WIN_CAMP_CODE VARCHAR(20) COMMENT '勝利陣営コード',
	REGISTER_DATETIME DATETIME NOT NULL COMMENT '登録日時',
	REGISTER_TRACE VARCHAR(64) NOT NULL COMMENT '登録トレース',
	UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新日時',
	UPDATE_TRACE VARCHAR(64) NOT NULL COMMENT '更新トレース',
	PRIMARY KEY (VILALGE_ID)
) COMMENT = '村';


CREATE TABLE VILLAGE_PLAYER
(
	VILLAGE_PLAYER_ID INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '村参加者ID',
	VILLAGE_ID INT UNSIGNED NOT NULL COMMENT '村ID',
	PLAYER_ID INT UNSIGNED NOT NULL COMMENT 'プレイヤーID',
	CHARA_ID INT UNSIGNED NOT NULL COMMENT 'キャラクターID',
	SKILL_CODE VARCHAR(20) COMMENT '役職コード',
	PRIMARY KEY (VILLAGE_PLAYER_ID),
	CONSTRAINT UQ_village_player_id UNIQUE (VILLAGE_ID, PLAYER_ID)
) COMMENT = '村参加者';


CREATE TABLE VILLAGE_SETTINGS
(
	VILLAGE_ID INT UNSIGNED NOT NULL COMMENT '村ID',
	START_PERSON_MIN_NUM INT UNSIGNED COMMENT '最低開始人数',
	PERSON_MAX_NUM INT UNSIGNED COMMENT '定員',
	START_DATETIME DATETIME COMMENT '開始日時',
	DAY_CHANGE_INTERVAL_SECONDS INT UNSIGNED NOT NULL COMMENT '更新間隔（秒）',
	IS_OPEN_VOTE BOOLEAN NOT NULL COMMENT '記名投票か',
	IS_POSSIBLE_SKILL_REQUEST BOOLEAN NOT NULL COMMENT '役職希望有効か',
	PRIMARY KEY (VILLAGE_ID)
) COMMENT = '村設定';


CREATE TABLE VILLAGE_STATUS
(
	VILLAGE_STATUS_CODE VARCHAR(20) NOT NULL COMMENT '村ステータスコード',
	VILLAGE_STATUS_NAME VARCHAR(20) NOT NULL COMMENT '村ステータス名',
	PRIMARY KEY (VILLAGE_STATUS_CODE)
) COMMENT = '村ステータス';



/* Create Foreign Keys */

ALTER TABLE PLAYER
	ADD CONSTRAINT FK_PLAYER_AUTHORITY FOREIGN KEY (AUTHORITY_CODE)
	REFERENCES AUTHORITY (AUTHORITY_CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE SKILL
	ADD CONSTRAINT FK_SKILL_CAMP FOREIGN KEY ( CAMP_CODE)
	REFERENCES CAMP (CAMP_CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VILLAGE
	ADD CONSTRAINT FK_VILLAGE_CAMP FOREIGN KEY (WIN_CAMP_CODE)
	REFERENCES CAMP (CAMP_CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VILLAGE_PLAYER
	ADD CONSTRAINT FK_VILLAGE_PLAYER_CHARA FOREIGN KEY (CHARA_ID)
	REFERENCES CHARA (CHARA_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CHARA
	ADD CONSTRAINT FK_CHARA_CHARA_GROUP FOREIGN KEY (CHARA_GROUP_ID)
	REFERENCES CHARA_GROUP (CHARA_GROUP_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CHARA_GROUP
	ADD CONSTRAINT FK_CHARA_GROUP_DESIGNER FOREIGN KEY (DESIGNER_ID)
	REFERENCES DESIGNER (DESIGNER_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE MESSAGE
	ADD CONSTRAINT FK_MESSAGE_MESSAGE_TYPE FOREIGN KEY (MESSAGE_TYPE_CODE)
	REFERENCES MESSAGE_TYPE (MESSAGE_TYPE_CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE MESSAGE
	ADD CONSTRAINT FK_MESSAGE_PLAYER FOREIGN KEY (PLAYER_ID)
	REFERENCES PLAYER (PLAYER_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VILLAGE_PLAYER
	ADD CONSTRAINT FK_VILLAGE_PLAYER_PLAYER FOREIGN KEY (PLAYER_ID)
	REFERENCES PLAYER (PLAYER_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VILLAGE_PLAYER
	ADD CONSTRAINT FK_VILLAGE_PLAYER_SKILL FOREIGN KEY (SKILL_CODE)
	REFERENCES SKILL (SKILL_CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE MESSAGE
	ADD CONSTRAINT FK_MESSAGE_VILLAGE FOREIGN KEY (VILLAGE_ID)
	REFERENCES VILLAGE (VILALGE_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VILLAGE_PLAYER
	ADD CONSTRAINT FK_VILLAGE_PLAYER_VILLAGE FOREIGN KEY (VILLAGE_ID)
	REFERENCES VILLAGE (VILALGE_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VILLAGE_SETTINGS
	ADD CONSTRAINT FK_VILLAGE_SETTINGS_VILLAGE FOREIGN KEY (VILLAGE_ID)
	REFERENCES VILLAGE (VILALGE_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE MESSAGE
	ADD CONSTRAINT FK_MESSAGE_VILLAGE_PLAYER FOREIGN KEY (VILLAGE_PLAYER_ID)
	REFERENCES VILLAGE_PLAYER (VILLAGE_PLAYER_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE MESSAGE
	ADD CONSTRAINT FK_MESSAGE_VILLAGE_PLAYER_TO FOREIGN KEY (TO_VILLAGE_PLAYER_ID)
	REFERENCES VILLAGE_PLAYER (VILLAGE_PLAYER_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VILLAGE
	ADD CONSTRAINT FK_VILLAGE_VILLAGE_STATUS FOREIGN KEY (VILLAGE_STATUS_CODE)
	REFERENCES VILLAGE_STATUS (VILLAGE_STATUS_CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



