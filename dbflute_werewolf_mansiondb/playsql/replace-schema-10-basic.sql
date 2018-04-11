

/* Create Tables */

CREATE TABLE ABILITY
(
	VILLAGE_ID INT UNSIGNED NOT NULL COMMENT '村ID',
	DAY INT UNSIGNED NOT NULL COMMENT '何日目か',
	CHARA_ID INT UNSIGNED NOT NULL COMMENT 'キャラクターID',
	TARGET_CHARA_ID INT UNSIGNED COMMENT '行使対象キャラID',
	ABILITY_TYPE_CODE VARCHAR(20) NOT NULL COMMENT '能力種別コード',
	REGISTER_DATETIME DATETIME NOT NULL COMMENT '登録日時',
	REGISTER_TRACE VARCHAR(64) NOT NULL COMMENT '登録トレース',
	UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新日時',
	UPDATE_TRACE VARCHAR(64) NOT NULL COMMENT '更新トレース',
	PRIMARY KEY (VILLAGE_ID, DAY, CHARA_ID, ABILITY_TYPE_CODE)
) COMMENT = '能力行使';


CREATE TABLE ABILITY_TYPE
(
	ABILITY_TYPE_CODE VARCHAR(20) NOT NULL COMMENT '能力種別コード',
	ABILITY_TYPE_NAME VARCHAR(20) NOT NULL COMMENT '能力種別',
	PRIMARY KEY (ABILITY_TYPE_CODE)
) COMMENT = '能力種別';


CREATE TABLE AUTHORITY
(
	AUTHORITY_CODE VARCHAR(20) NOT NULL COMMENT '権限コード',
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
	CHARA_SHORT_NAME CHAR(1) NOT NULL COMMENT 'キャラクター略称',
	CHARA_GROUP_ID INT UNSIGNED NOT NULL COMMENT 'キャラクターグループID',
	CHARA_IMG_URL VARCHAR(100) NOT NULL COMMENT 'キャラクター画像URL',
	IS_DUMMY BOOLEAN NOT NULL COMMENT 'ダミーか',
	DEFAULT_JOIN_MESSAGE VARCHAR(200) COMMENT '入村時デフォルト発言',
	REGISTER_DATETIME DATETIME NOT NULL COMMENT '登録日時',
	REGISTER_TRACE VARCHAR(64) NOT NULL COMMENT '登録トレース',
	UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新日時',
	UPDATE_TRACE VARCHAR(64) NOT NULL COMMENT '更新トレース',
	PRIMARY KEY (CHARA_ID)
) COMMENT = 'キャラクター';


CREATE TABLE CHARA_GROUP
(
	CHARA_GROUP_ID INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'キャラクターグループID',
	CHARA_GROUP_NAME VARCHAR(40) NOT NULL COMMENT 'キャラクターグループ名',
	DESIGNER_ID INT UNSIGNED NOT NULL COMMENT 'デザイナーID',
	REGISTER_DATETIME DATETIME NOT NULL COMMENT '登録日時',
	REGISTER_TRACE VARCHAR(64) NOT NULL COMMENT '登録トレース',
	UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新日時',
	UPDATE_TRACE VARCHAR(64) NOT NULL COMMENT '更新トレース',
	PRIMARY KEY (CHARA_GROUP_ID)
) COMMENT = 'キャラクターグループ';


CREATE TABLE DEAD_REASON
(
	DEAD_REASON_CODE VARCHAR(20) NOT NULL COMMENT '死亡理由コード',
	DEAD_REASON_NAME VARCHAR(20) NOT NULL COMMENT '死亡理由',
	PRIMARY KEY (DEAD_REASON_CODE)
) COMMENT = '死亡理由';


CREATE TABLE DESIGNER
(
	DESIGNER_ID INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'デザイナーID',
	DESIGNER_NAME VARCHAR(40) NOT NULL COMMENT 'デザイナー名',
	REGISTER_DATETIME DATETIME NOT NULL COMMENT '登録日時',
	REGISTER_TRACE VARCHAR(64) NOT NULL COMMENT '登録トレース',
	UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新日時',
	UPDATE_TRACE VARCHAR(64) NOT NULL COMMENT '更新トレース',
	PRIMARY KEY (DESIGNER_ID)
) COMMENT = 'デザイナー';


CREATE TABLE FOOTSTEP
(
	VILLAGE_ID INT UNSIGNED NOT NULL COMMENT '村ID',
	DAY INT UNSIGNED NOT NULL COMMENT '何日目か',
	CHARA_ID INT UNSIGNED NOT NULL COMMENT 'キャラクターID',
	FOOTSTEP_ROOM_NUMBERS VARCHAR(100) COMMENT '足音のした部屋 : カンマ区切り',
	REGISTER_DATETIME DATETIME NOT NULL COMMENT '登録日時',
	REGISTER_TRACE VARCHAR(64) NOT NULL COMMENT '登録トレース',
	UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新日時',
	UPDATE_TRACE VARCHAR(64) NOT NULL COMMENT '更新トレース',
	PRIMARY KEY (VILLAGE_ID, DAY, CHARA_ID)
) COMMENT = '足音';


CREATE TABLE MESSAGE
(
	MESSAGE_ID INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'メッセージID',
	VILLAGE_ID INT UNSIGNED NOT NULL COMMENT '村ID',
	VILLAGE_PLAYER_ID INT UNSIGNED COMMENT '村参加者ID',
	PLAYER_ID INT UNSIGNED COMMENT 'プレイヤーID',
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
	PLAYER_NAME VARCHAR(12) NOT NULL UNIQUE COMMENT 'プレイヤー名',
	PLAYER_PASSWORD CHAR(60) NOT NULL COMMENT 'プレイヤーパスワード',
	AUTHORITY_CODE VARCHAR(20) NOT NULL COMMENT '権限コード',
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
	DISP_ORDER INT UNSIGNED NOT NULL COMMENT '並び順',
	PRIMARY KEY (SKILL_CODE)
) COMMENT = '役職';


CREATE TABLE VILLAGE
(
	VILLAGE_ID INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '村ID',
	VILLAGE_DISPLAY_NAME VARCHAR(40) NOT NULL COMMENT '村表示名',
	CREATE_PLAYER_NAME VARCHAR(12) NOT NULL COMMENT '村作成プレイヤー名',
	VILLAGE_STATUS_CODE VARCHAR(20) NOT NULL COMMENT '村ステータスコード',
	ROOM_SIZE_WIDTH INT UNSIGNED COMMENT '部屋サイズ（横）',
	ROOM_SIZE_HEIGHT INT UNSIGNED COMMENT '部屋サイズ（縦）',
	EPILOGUE_DAY INT UNSIGNED COMMENT 'エピローグ日',
	WIN_CAMP_CODE VARCHAR(20) COMMENT '勝利陣営コード',
	REGISTER_DATETIME DATETIME NOT NULL COMMENT '登録日時',
	REGISTER_TRACE VARCHAR(64) NOT NULL COMMENT '登録トレース',
	UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新日時',
	UPDATE_TRACE VARCHAR(64) NOT NULL COMMENT '更新トレース',
	PRIMARY KEY (VILLAGE_ID)
) COMMENT = '村';


CREATE TABLE VILLAGE_DAY
(
	VILLAGE_ID INT UNSIGNED NOT NULL COMMENT '村ID',
	DAY INT UNSIGNED NOT NULL COMMENT '何日目か',
	DAYCHANGE_DATETIME DATETIME NOT NULL COMMENT '日付更新日時',
	REGISTER_DATETIME DATETIME NOT NULL COMMENT '登録日時',
	REGISTER_TRACE VARCHAR(64) NOT NULL COMMENT '登録トレース',
	UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新日時',
	UPDATE_TRACE VARCHAR(64) NOT NULL COMMENT '更新トレース',
	PRIMARY KEY (VILLAGE_ID, DAY)
) COMMENT = '村日付';


CREATE TABLE VILLAGE_PLAYER
(
	VILLAGE_PLAYER_ID INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '村参加者ID',
	VILLAGE_ID INT UNSIGNED NOT NULL COMMENT '村ID',
	PLAYER_ID INT UNSIGNED NOT NULL COMMENT 'プレイヤーID',
	CHARA_ID INT UNSIGNED NOT NULL COMMENT 'キャラクターID',
	SKILL_CODE VARCHAR(20) COMMENT '役職コード',
	REQUEST_SKILL_CODE VARCHAR(20) COMMENT '希望役職コード',
	ROOM_NUMBER INT UNSIGNED COMMENT '部屋番号',
	IS_DEAD BOOLEAN NOT NULL COMMENT '死亡しているか',
	IS_SPECTATOR BOOLEAN NOT NULL COMMENT '見学者か',
	DEAD_REASON_CODE VARCHAR(20) COMMENT '死亡理由コード',
	DEAD_DAY INT UNSIGNED COMMENT '何日目に死亡したか',
	IS_GONE BOOLEAN NOT NULL COMMENT '退村済みか',
	REGISTER_DATETIME DATETIME NOT NULL COMMENT '登録日時',
	REGISTER_TRACE VARCHAR(64) NOT NULL COMMENT '登録トレース',
	UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新日時',
	UPDATE_TRACE VARCHAR(64) NOT NULL COMMENT '更新トレース',
	PRIMARY KEY (VILLAGE_PLAYER_ID)
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
	IS_AVAILABLE_SPECTATE BOOLEAN NOT NULL COMMENT '見学可能か',
	CHARACTER_GROUP_ID INT UNSIGNED NOT NULL COMMENT 'キャラクターグループID',
	JOIN_PASSWORD VARCHAR(12) COMMENT '入村パスワード',
	REGISTER_DATETIME DATETIME NOT NULL COMMENT '登録日時',
	REGISTER_TRACE VARCHAR(64) NOT NULL COMMENT '登録トレース',
	UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新日時',
	UPDATE_TRACE VARCHAR(64) NOT NULL COMMENT '更新トレース',
	PRIMARY KEY (VILLAGE_ID)
) COMMENT = '村設定';


CREATE TABLE VILLAGE_STATUS
(
	VILLAGE_STATUS_CODE VARCHAR(20) NOT NULL COMMENT '村ステータスコード',
	VILLAGE_STATUS_NAME VARCHAR(20) NOT NULL COMMENT '村ステータス名',
	PRIMARY KEY (VILLAGE_STATUS_CODE)
) COMMENT = '村ステータス';


CREATE TABLE VOTE
(
	VILLAGE_ID INT UNSIGNED NOT NULL COMMENT '村ID',
	DAY INT UNSIGNED NOT NULL COMMENT '何日目か',
	CHARA_ID INT UNSIGNED NOT NULL COMMENT 'キャラクターID',
	VOTE_CHARA_ID INT UNSIGNED NOT NULL COMMENT '投票先キャラクターID',
	REGISTER_DATETIME DATETIME NOT NULL COMMENT '登録日時',
	REGISTER_TRACE VARCHAR(64) NOT NULL COMMENT '登録トレース',
	UPDATE_DATETIME DATETIME NOT NULL COMMENT '更新日時',
	UPDATE_TRACE VARCHAR(64) NOT NULL COMMENT '更新トレース',
	PRIMARY KEY (VILLAGE_ID, DAY, CHARA_ID)
) COMMENT = '投票';



/* Create Foreign Keys */

ALTER TABLE ABILITY
	ADD CONSTRAINT FK_ABILITY_ABILITY_TYPE FOREIGN KEY (ABILITY_TYPE_CODE)
	REFERENCES ABILITY_TYPE (ABILITY_TYPE_CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


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


ALTER TABLE ABILITY
	ADD CONSTRAINT FK_ABILITY_CHARA FOREIGN KEY (CHARA_ID)
	REFERENCES CHARA (CHARA_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE ABILITY
	ADD CONSTRAINT FK_ABILITY_CHARA_TARGET FOREIGN KEY (TARGET_CHARA_ID)
	REFERENCES CHARA (CHARA_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE FOOTSTEP
	ADD CONSTRAINT FK_FOOTSTEP_CHARA FOREIGN KEY (CHARA_ID)
	REFERENCES CHARA (CHARA_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VILLAGE_PLAYER
	ADD CONSTRAINT FK_VILLAGE_PLAYER_CHARA FOREIGN KEY (CHARA_ID)
	REFERENCES CHARA (CHARA_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VOTE
	ADD CONSTRAINT FK_VOTE_CHARA FOREIGN KEY (CHARA_ID)
	REFERENCES CHARA (CHARA_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VOTE
	ADD CONSTRAINT FK_VOTE_CHARA_TO FOREIGN KEY (VOTE_CHARA_ID)
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


ALTER TABLE VILLAGE_SETTINGS
	ADD CONSTRAINT FK_VILLAGE_SETTINGS_CHARA_GROUP FOREIGN KEY (CHARACTER_GROUP_ID)
	REFERENCES CHARA_GROUP (CHARA_GROUP_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VILLAGE_PLAYER
	ADD CONSTRAINT FK_VILLAGE_PLAYER_DEAD_REASON FOREIGN KEY (DEAD_REASON_CODE)
	REFERENCES DEAD_REASON (DEAD_REASON_CODE)
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


ALTER TABLE VILLAGE_PLAYER
	ADD CONSTRAINT FK_VILLAGE_PLAYER_SKILL_REQUEST FOREIGN KEY (REQUEST_SKILL_CODE)
	REFERENCES SKILL (SKILL_CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VILLAGE_DAY
	ADD CONSTRAINT FK_VILLAGE_DAY_VILLAGE FOREIGN KEY (VILLAGE_ID)
	REFERENCES VILLAGE (VILLAGE_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VILLAGE_PLAYER
	ADD CONSTRAINT FK_VILLAGE_PLAYER_VILLAGE FOREIGN KEY (VILLAGE_ID)
	REFERENCES VILLAGE (VILLAGE_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VILLAGE_SETTINGS
	ADD CONSTRAINT FK_VILLAGE_SETTINGS_VILLAGE FOREIGN KEY (VILLAGE_ID)
	REFERENCES VILLAGE (VILLAGE_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE ABILITY
	ADD CONSTRAINT FK_ABILITY_VILLAGE_DAY FOREIGN KEY (VILLAGE_ID, DAY)
	REFERENCES VILLAGE_DAY (VILLAGE_ID, DAY)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE FOOTSTEP
	ADD CONSTRAINT FK_FOOTSTEP_VILLAGE_DAY FOREIGN KEY (VILLAGE_ID, DAY)
	REFERENCES VILLAGE_DAY (VILLAGE_ID, DAY)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE MESSAGE
	ADD CONSTRAINT FK_MESSAGE_VILLAGE_DAY FOREIGN KEY (VILLAGE_ID, DAY)
	REFERENCES VILLAGE_DAY (VILLAGE_ID, DAY)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE VOTE
	ADD CONSTRAINT FK_VOTE_VILLAGE_DAY FOREIGN KEY (VILLAGE_ID, DAY)
	REFERENCES VILLAGE_DAY (VILLAGE_ID, DAY)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE MESSAGE
	ADD CONSTRAINT FK_MESSAGE_VILLAGE_PLAYER FOREIGN KEY (VILLAGE_PLAYER_ID)
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



