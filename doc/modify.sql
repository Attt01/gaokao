update tb_volunteer set `name` = "北京大学",
                        `is_985` = 1,
                        `is_junior_school` = 1,
                        `is_211` = 1,
                        `is_public` = 1,
                        `is_private` = 1,
                        `is_undergraduate_school` = 1,
                        `subject_restriction_type` = 0,
                        `subject_restriction_detail` = "[0]",
                        `volunteer_section` = 1;


update `tb_volunteer` set `name` = "北京大学",
                          `university_code` = "10001",
                          `province` = "北京",
                          `city` = "北京",
                          `address` = "北京市海淀区颐和园五号",
                          `is_undergraduate_school` = 1,
                          `is_junior_school` = 1,
                          `is_985` = 1,
                          `is_211` = 1,
                          `is_public` = 1,
                          `is_private` = 1,
                          `category` = "综合",
                          `pic_link` = "https://static-data.eol.cn/upload/logo/31.jpg",
                          `employment_rate` = 98,
                          `abroad_rate` = 30,
                          `further_rate` = 44,
                          `volunteer_section` = 1,
                          `lowest_score` = 687,
                          `lowest_position` = 225,
                          `professional_name` = '工商管理类',
                          `subject_restriction_type` = 0,
                          `subject_restriction_detail` = '[0]',
                          `score` = 697,
                          `position` = 45,
                          `enrollment` = 3,
                          `time` = 4,
                          `fee` = 5000,
                          `double_first_class_subject_number` = 41,
                          `country_specific_subject_number` = 41,
                          `master_point` = 50,
                          `doctor_point` = 49;

DELETE FROM tb_volunteer WHERE id < 22313;

UPDATE `tb_volunteer` set `hire_province` = "山东" WHERE id < 22313;

ALTER TABLE tb_volunteer ADD COLUMN `hire_province` VARCHAR(10) NOT NULL  DEFAULT '北京' COMMENT '招生省份' AFTER `university_code`;


/*更新tb_form_volunteer表*/

DELIMITER $
create procedure addTestData()
BEGIN
DECLARE i INT DEFAULT 1;
DECLARE j int default 1;
WHILE i<=10000 DO
    set j=1;
    WHILE j <= 96 DO
        INSERT INTO tb_form_volunteer(id, form_id, volunteer_section, volunteer_position, volunteer_id)
                            VALUES  (i * 96 + j, i, 1, j, j);
        SET j=j+1;
    END WHILE;
SET i=i+1;
END WHILE;
END $
DELIMITER ;
CALL addTestData();