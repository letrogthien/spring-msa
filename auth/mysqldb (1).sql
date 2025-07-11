CREATE
DATABASE  IF NOT EXISTS `app_db`;
USE `app_db`;


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`                 binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
    `username`           varchar(50)  NOT NULL,
    `email`              varchar(255) NOT NULL,
    `password_hash`      varchar(255) NOT NULL,
    `status`             VARCHAR(50)  NOT NULL,
    `two_factor_enabled` tinyint(1) DEFAULT '0',
    `is_kyc`             tinyint(1) DEFAULT '0',
    `two_factor_secret`  varchar(100) DEFAULT NULL,
    `created_at`         timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`         timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `last_login_at`      timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`),
    UNIQUE KEY `email` (`email`),
    KEY                  `idx_users_email` (`email`),
    KEY                  `idx_users_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



DROP TABLE IF EXISTS `audit_logs`;
CREATE TABLE `audit_logs`
(
    `id`          binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
    `user_id`     binary(16) DEFAULT NULL,
    `action`      varchar(100) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    `created_at`  timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY           `idx_audit_logs_user_id` (`user_id`),
    CONSTRAINT `audit_logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK
TABLES `audit_logs` WRITE;
UNLOCK
TABLES;



DROP TABLE IF EXISTS `device_manager`;

CREATE TABLE `device_manager`
(
    `id`            binary(16) NOT NULL,
    `created_at`    datetime(6) NOT NULL,
    `device_name`   varchar(100) NOT NULL,
    `device_type`   varchar(50)  NOT NULL,
    `last_login_at` datetime(6) DEFAULT NULL,
    `user_id`       binary(16) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK
TABLES `device_manager` WRITE;
UNLOCK
TABLES;


DROP TABLE IF EXISTS `login_history`;
CREATE TABLE `login_history`
(
    `id`          binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
    `user_id`     binary(16) NOT NULL,
    `login_at`    timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `ip_address`  varchar(45) DEFAULT NULL,
    `device_info` text,
    `success`     tinyint(1) NOT NULL,
    PRIMARY KEY (`id`),
    KEY           `idx_login_history_user_id` (`user_id`),
    CONSTRAINT `login_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK
TABLES `login_history` WRITE;
UNLOCK
TABLES;



DROP TABLE IF EXISTS `password_history`;

CREATE TABLE `password_history`
(
    `id`            binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
    `user_id`       binary(16) NOT NULL,
    `password_hash` varchar(255) NOT NULL,
    `created_at`    timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `current_index` int          NOT NULL,
    PRIMARY KEY (`id`),
    KEY             `user_id` (`user_id`),
    CONSTRAINT `password_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK
TABLES `password_history` WRITE;
UNLOCK
TABLES;



DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`
(
    `id`          binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
    `name`        varchar(50) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `roles` (`id`, `name`, `description`)
VALUES (uuid_to_bin(uuid()), 'ROLE_USER', 'Standard user role'),
       (uuid_to_bin(uuid()), 'ROLE_ADMIN', 'Administrator role'),
       (uuid_to_bin(uuid()), 'ROLE_MODERATOR', 'Moderator role'),
       (uuid_to_bin(uuid()), 'ROLE_SUPER_ADMIN', 'Super administrator role'),
       (uuid_to_bin(uuid()), 'ROLE_GUEST', 'Guest role'),
       (uuid_to_bin(uuid()), 'ROLE_MEMBER', 'Member role'),
       (uuid_to_bin(uuid()), 'ROLE_EDITOR', 'Editor role'),
       (uuid_to_bin(uuid()), 'ROLE_VIEWER', 'Viewer role'),
       (uuid_to_bin(uuid()), 'ROLE_CONTRIBUTOR', 'Contributor role'),
       (uuid_to_bin(uuid()), 'ROLE_MANAGER', 'Manager role'),
       (uuid_to_bin(uuid()), 'ROLE_OWNER', 'Owner role'),
       (uuid_to_bin(uuid()), 'ROLE_SUPPORT', 'Support role'),
       (uuid_to_bin(uuid()), 'ROLE_DEVELOPER', 'Developer role'),
       (uuid_to_bin(uuid()), 'ROLE_TESTER', 'Tester role'),
       (uuid_to_bin(uuid()), 'ROLE_ANALYST', 'Analyst role'),
         (uuid_to_bin(uuid()), 'ROLE_SELLER', 'Seller role'),
       (uuid_to_bin(uuid()), 'ROLE_AUDITOR', 'Auditor role');


DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles`
(
    `user_id` binary(16) NOT NULL,
    `role_id` binary(16) NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    KEY       `role_id` (`role_id`),
    CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `send_message_error`
(
    `id`         binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
    `topic`       varchar(255) NOT NULL,
    `message` text NOT NULL,
    `status` varchar(50) NOT NULL,
    `created_at`  timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
)


