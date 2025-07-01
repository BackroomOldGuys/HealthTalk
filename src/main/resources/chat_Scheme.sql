-- ==================================================
-- 1. 운동 숙련도 레벨
-- ==================================================
CREATE TABLE workout_levels (
  id   INT          NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 2. 운동 목표
-- ==================================================
CREATE TABLE workout_goals (
  id   INT          NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 3. 신체 불편 부위
-- ==================================================
CREATE TABLE body_discomforts (
  id   INT          NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 4. 사용자 테이블
-- ==================================================
CREATE TABLE users (
  id                 BIGINT          NOT NULL AUTO_INCREMENT,
  email              VARCHAR(255)    NOT NULL,
  password           VARCHAR(255)    NOT NULL,
  nickname           VARCHAR(100)    NOT NULL,
  gender             VARCHAR(10)     NULL,
  age                INT             NULL,
  height_cm          FLOAT           NULL,
  weight_kg          FLOAT           NULL,
  profile_image_url  VARCHAR(255)    NULL,
  bio                TEXT            NULL,
  workout_level_id   INT             NULL,
  workout_goal_id    INT             NULL,
  created_at         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY ux_users_email (email),
  CONSTRAINT fk_users_level FOREIGN KEY (workout_level_id) REFERENCES workout_levels(id) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT fk_users_goal  FOREIGN KEY (workout_goal_id)  REFERENCES workout_goals(id)  ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 5. 사용자-불편 조인 테이블
-- ==================================================
CREATE TABLE user_discomforts (
  user_id       BIGINT NOT NULL,
  discomfort_id INT    NOT NULL,
  PRIMARY KEY (user_id, discomfort_id),
  CONSTRAINT fk_ud_user        FOREIGN KEY (user_id)       REFERENCES users(id)             ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_ud_discomfort  FOREIGN KEY (discomfort_id) REFERENCES body_discomforts(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 6. 사용자 팔로우
-- ==================================================
CREATE TABLE user_follows (
  follower_id  BIGINT   NOT NULL,
  following_id BIGINT   NOT NULL,
  created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (follower_id, following_id),
  CONSTRAINT fk_uf_follower  FOREIGN KEY (follower_id)  REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_uf_following FOREIGN KEY (following_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 7. 리프레시 토큰
-- ==================================================
CREATE TABLE refresh_tokens (
  user_id       BIGINT   NOT NULL,
  refresh_token TEXT     NOT NULL,
  expires_at    DATETIME NOT NULL,
  PRIMARY KEY (user_id),
  CONSTRAINT fk_rt_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 8. 클럽 테이블
-- ==================================================
CREATE TABLE clubs (
  id            BIGINT       NOT NULL AUTO_INCREMENT,
  name          VARCHAR(100) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  description   VARCHAR(200) NULL,
  owner_id      BIGINT       NOT NULL,
  created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT fk_clubs_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 9. 클럽 멤버
-- ==================================================
CREATE TABLE club_members (
  club_id   BIGINT       NOT NULL,
  user_id   BIGINT       NOT NULL,
  role      VARCHAR(20)  NOT NULL DEFAULT 'member',
  joined_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (club_id, user_id),
  CONSTRAINT fk_cm_club FOREIGN KEY (club_id) REFERENCES clubs(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_cm_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 10. 클럽 체크리스트
-- ==================================================
CREATE TABLE club_checklists (
  club_id      BIGINT   NOT NULL,
  user_id      BIGINT   NOT NULL,
  date         DATE     NOT NULL,
  completed    BOOLEAN  NOT NULL DEFAULT FALSE,
  completed_at DATETIME NULL,
  PRIMARY KEY (club_id, user_id, date),
  CONSTRAINT fk_cc_club FOREIGN KEY (club_id) REFERENCES clubs(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_cc_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 11. 피드
-- ==================================================
CREATE TABLE feeds (
  id         BIGINT       NOT NULL AUTO_INCREMENT,
  user_id    BIGINT       NOT NULL,
  club_id    BIGINT       NULL,
  content    TEXT          NULL,
  image_url  VARCHAR(255)  NULL,
  created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT fk_feeds_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_feeds_club FOREIGN KEY (club_id) REFERENCES clubs(id) ON DELETE SET NULL    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 12. 운동 정의
-- ==================================================
CREATE TABLE exercise_definitions (
  id   INT          NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 13. 피드-운동 세트
-- ==================================================
CREATE TABLE feed_exercises (
  id                     BIGINT NOT NULL AUTO_INCREMENT,
  feed_id                BIGINT NOT NULL,
  exercise_definition_id INT    NOT NULL,
  sets                   INT    NULL,
  reps                   INT    NULL,
  weight_kg              INT    NULL,
  duration_min           INT    NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_fe_ex_feed FOREIGN KEY (feed_id)                REFERENCES feeds(id)               ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_fe_ex_def  FOREIGN KEY (exercise_definition_id) REFERENCES exercise_definitions(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 14. 피드 좋아요
-- ==================================================
CREATE TABLE feed_likes (
  user_id    BIGINT   NOT NULL,
  feed_id    BIGINT   NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, feed_id),
  CONSTRAINT fk_fl_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_fl_feed FOREIGN KEY (feed_id) REFERENCES feeds(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 15. 피드 댓글
-- ==================================================
CREATE TABLE feed_comments (
  id          BIGINT   NOT NULL AUTO_INCREMENT,
  feed_id     BIGINT   NOT NULL,
  user_id     BIGINT   NOT NULL,
  comment_text TEXT    NULL,
  created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT fk_fc_feed FOREIGN KEY (feed_id) REFERENCES feeds(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_fc_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 16. 태그 정의
-- ==================================================
CREATE TABLE tag_definitions (
  id   INT          NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 17. 피드-태그 매핑
-- ==================================================
CREATE TABLE feed_tags (
  feed_id BIGINT NOT NULL,
  tag_id  INT    NOT NULL,
  PRIMARY KEY (feed_id, tag_id),
  CONSTRAINT fk_ft_feed FOREIGN KEY (feed_id) REFERENCES feeds(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_ft_tag  FOREIGN KEY (tag_id)  REFERENCES tag_definitions(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 18. 채팅방
-- ==================================================
CREATE TABLE chat_rooms (
  id              BIGINT       NOT NULL AUTO_INCREMENT,
  room_type       VARCHAR(20)  NOT NULL,
  club_id         BIGINT       NULL,
  conversation_id VARCHAR(100) UNIQUE,
  PRIMARY KEY (id),
  CONSTRAINT fk_cr_club FOREIGN KEY (club_id) REFERENCES clubs(id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 19. 채팅 참가자
-- ==================================================
CREATE TABLE chat_participants (
  room_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  PRIMARY KEY (room_id, user_id),
  CONSTRAINT fk_cp_room FOREIGN KEY (room_id) REFERENCES chat_rooms(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_cp_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 20. 채팅 메시지
-- ==================================================
CREATE TABLE chat_messages (
  id         BIGINT   NOT NULL AUTO_INCREMENT,
  room_id    BIGINT   NOT NULL,
  sender_id  BIGINT   NULL,
  content    TEXT     NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY idx_cm_room_created (room_id, created_at),
  CONSTRAINT fk_cm_room   FOREIGN KEY (room_id)   REFERENCES chat_rooms(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_cm_sender FOREIGN KEY (sender_id) REFERENCES users(id)      ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================================================
-- 21. 알림
-- ==================================================
CREATE TABLE notifications (
  id            BIGINT       NOT NULL AUTO_INCREMENT,
  user_id       BIGINT       NOT NULL,
  type          VARCHAR(50)  NOT NULL,
  content       TEXT         NULL,
  reference_url VARCHAR(255) NULL,
  is_read       BOOLEAN      NOT NULL DEFAULT FALSE,
  created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT fk_nt_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
