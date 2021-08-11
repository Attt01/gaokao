drop table if exists `tb_score_rank`;
CREATE TABLE `tb_score_rank`
(
    id                  int         auto_increment not null comment '主键',
    score               int         not null default 0 comment '分数段',
    nums                int         not null default 0 comment '此分数段人数',
    total_nums          int         not null default 0 comment '累计人数',
    has_physics_nums    int                  default 0 comment '选考物理的本段人数',
    has_physics_total   int                  default 0 comment '选考物理的累计人数',
    has_chemistry_nums    int                  default 0 comment '选考化学的本段人数',
    has_chemistry_total   int                  default 0 comment '选考化学的累计人数',
    has_biology_nums    int                  default 0 comment '选考生物的本段人数',
    has_biology_total   int                  default 0 comment '选考生物的累计人数',
    has_politics_nums    int                  default 0 comment '选考政治的本段人数',
    has_politics_total   int                  default 0 comment '选考政治的累计人数',
    has_history_nums    int                  default 0 comment '选考历史的本段人数',
    has_history_total   int                  default 0 comment '选考历史的累计人数',
    has_geography_nums    int                  default 0 comment '选考地理的本段人数',
    has_geography_total   int                  default 0 comment '选考地理的累计人数',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '一分一段表';