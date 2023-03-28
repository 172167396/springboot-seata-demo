create database if not exists seata_order;

use seata_order;

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `product_id` varchar(32) NOT NULL COMMENT '产品id',
  `count` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '数量',
  `money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单状态：0：创建中；1：已完结',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='AT transaction mode undo table';

