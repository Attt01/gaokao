# 开发规范及建议

## 前提：尽量减少IDEA的警告

1. 类字段尽量声明为`final`，包括`static`字段及普通字段；
2. 禁止`List`/`Set`/`Map`的raw使用；
3. `@Autowired`注解优先使用构造方法注入；
4. 使用`@GetMapping`代替`@RequestMapping(method = RequestMethod.GET)`，
   `@PostMapping`代替`@RequestMapping(method = RequestMethod.POST)`；
5. 若一个Controller中多个Mapping拥有相同的前缀，则前缀应抽离到类上；若多个Controller拥有相同的前缀，则提取共有前缀到父类；
6. 对代码的修改，Git commit信息应以`type: msg`的格式进行提交（推荐使用插件Git Commit Template），`type`有如下几个选项：

- feat: 代表新增特性；
- refactor: 代表重构；
- docs: 文档改变；
- style: 代码格式改变；
- perf: 性能优化；
- test : 增加测试；
- build : 改变了build工具 如 grunt换成了npm；
- revert : 撤销上一次的 commit；
- chore : 构建过程或辅助工具的变动；
- fix: bug修复

7. Git commit最小化，不要将多次commit合并为一次提交；
8. 避免魔数的出现，多次出现的字符串或者数字抽取为常量；前端使用的Key强制抽取为常量；
9. Logger打印日志使用字符串模板，禁止使用字符串拼接；
10. 禁止新增使用标记为`@Deprecated`的方法；

## 技术选型

- 核心框架：Spring Boot
- 数据库层：Spring data jpa
- 安全框架：Spring Security
- 数据库连接池：Druid
- 前端：Vue.js
- 数据库：mysql8以上

## 快速开始

- IDEA安装lombok插件
- 创建mysql数据库

```sql
CREATE DATABASE IF NOT EXISTS Gaokao DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
CREATE USER 'Gaokao'@'%' IDENTIFIED BY 'Gaokao522';
GRANT ALL privileges ON Gaokao.* TO 'Gaokao'@'%';
flush privileges;
```



## 数据初始化


### 权限初始化

```sql
INSERT INTO `tb_sys_perm` VALUES (10, 0, 'dashboard', '主页管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (20, 0, 'system', '系统管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (30, 0, 'data', '运营管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (1010, 10, 'home', '首页', 1621557900812, 'system', 1621769107001, 'system');
INSERT INTO `tb_sys_perm` VALUES (1020, 10, 'admin', '超管首页', 1621557900812, 'system', 1621769107001, 'system');
INSERT INTO `tb_sys_perm` VALUES (2010, 20, 'perm', '权限管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (2020, 20, 'user', '用户管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (2030, 20, 'role', '角色管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (2040, 20, 'task', '任务管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (3010, 30, 'category', '分类管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (3020, 30, 'group', '分组管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (3030, 30, 'goods', '服务管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (3040, 30, 'attributes', '规格属性管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (3050, 30, 'shop', '商铺管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (3055, 30, 'settled', '入驻管理', 1625097854786, 'admin', 1625097892785, 'admin');
INSERT INTO `tb_sys_perm` VALUES (3060, 30, 'order', '订单管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (3070, 30, 'usermember', '会员管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (3080, 30, 'ranklist', '榜单管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (3090, 30, 'comment', '评论管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (201010, 2010, 'perm:add', '权限新增', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (201020, 2010, 'perm:update', '权限修改', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (201030, 2010, 'perm:delete', '权限删除', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (201040, 2010, 'perm:view', '权限列表', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (202010, 2020, 'user:add', '用户新增', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (202020, 2020, 'user:update', '用户修改', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (202030, 2020, 'user:delete', '用户删除', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (202040, 2020, 'user:view', '用户列表', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (203010, 2030, 'role:add', '角色新增', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (203020, 2030, 'role:update', '角色修改', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (203030, 2030, 'role:delete', '角色删除', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (203040, 2030, 'role:view', '角色列表', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (204010, 2040, 'task:add', '任务新增', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (204020, 2040, 'task:update', '任务修改', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (204030, 2040, 'task:delete', '任务删除', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (204040, 2040, 'task:view', '任务列表', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (301010, 3010, 'category:add', '分类新增', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (301020, 3010, 'category:update', '分类修改', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (301030, 3010, 'category:delete', '分类删除', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (301040, 3010, 'category:view', '分类列表', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (302010, 3020, 'group:add', '分组新增', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (302020, 3020, 'group:update', '分组修改', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (302030, 3020, 'group:delete', '分组删除', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (302040, 3020, 'group:view', '分组列表', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (303010, 3030, 'goods:add', '服务新增', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (303020, 3030, 'goods:update', '服务修改', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (303030, 3030, 'goods:delete', '服务删除', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (303040, 3030, 'goods:view', '服务列表', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (303050, 3030, 'goods:audit', '服务审核', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (303060, 3030, 'goods:changeStatus', '修改服务状态', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (304010, 3040, 'attributes:add', '规格属性新增', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (304020, 3040, 'attributes:update', '规格属性修改', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (304030, 3040, 'attributes:delete', '规格属性删除', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (304040, 3040, 'attributes:view', '规格属性列表', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (305010, 3050, 'shop:add', '商铺新增', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (305020, 3050, 'shop:update', '商铺修改', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (305030, 3050, 'shop:delete', '商铺删除', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (305040, 3050, 'shop:view', '商铺列表', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (305510, 3055, 'settled:view', '入驻列表', 1625097963947, 'admin', 1625097963947, 'admin');
INSERT INTO `tb_sys_perm` VALUES (305520, 3055, 'settled:update', '申请状态修改', 1625127657707, 'admin', 1625127657707, 'admin');
INSERT INTO `tb_sys_perm` VALUES (306010, 3060, 'order:add', '订单新增', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (306020, 3060, 'order:update', '订单修改', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (306030, 3060, 'order:delete', '订单删除', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (306040, 3060, 'order:view', '订单列表', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (306050, 3060, 'order:changeStatus', '订单管理', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (307020, 3070, 'usermember:update', '会员修改', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (307030, 3070, 'usermember:delete', '会员删除', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (307040, 3070, 'usermember:view', '会员列表', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (308010, 3080, 'ranklist:add', '榜单新增', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (308020, 3080, 'ranklist:update', '榜单修改', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (308030, 3080, 'ranklist:delete', '榜单删除', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (308040, 3080, 'ranklist:view', '榜单列表', 1618652880000, 'system', 1618652880000, 'system');
INSERT INTO `tb_sys_perm` VALUES (309010, 3090, 'comment:view', '评论列表', 1621845124458, 'system', 1621845124458, 'system');
INSERT INTO `tb_sys_perm` VALUES (309020, 3090, 'comment:update', '评论审核', 1621557876472, 'system', 1621769096240, 'system');
INSERT INTO `tb_sys_perm` VALUES (309030, 3090, 'comment:delete', '评论删除', 1621557900812, 'system', 1621769107001, 'system');
```

### 角色初始化

```sql
insert into tb_sys_role
values (100, 0, '超级管理员', 0, 1618652880000, 'system', 1618652880000, 'system');
insert into tb_sys_role_perm
values (1, 100, 10);
insert into tb_sys_role_perm
values (2, 100, 1010);
insert into tb_sys_role_perm
values (3, 100, 101010);
insert into tb_sys_role_perm
values (4, 100, 1020);
insert into tb_sys_role_perm
values (5, 100, 102010);
insert into tb_sys_role_perm
values (6, 100, 20);
insert into tb_sys_role_perm
values (7, 100, 2010);
insert into tb_sys_role_perm
values (8, 100, 2020);
insert into tb_sys_role_perm
values (9, 100, 2030);
insert into tb_sys_role_perm
values (10, 100, 2040);
insert into tb_sys_role_perm
values (11, 100, 203010);
insert into tb_sys_role_perm
values (12, 100, 203020);
insert into tb_sys_role_perm
values (13, 100, 203030);
insert into tb_sys_role_perm
values (14, 100, 203040);
insert into tb_sys_role_perm
values (15, 100, 204010);
insert into tb_sys_role_perm
values (16, 100, 204020);
insert into tb_sys_role_perm
values (17, 100, 204030);
insert into tb_sys_role_perm
values (18, 100, 204040);
```

### 用户初始化

```sql
insert into tb_sys_user
values (1, 0, 'admin', 15557182659, '$2a$10$ABk.ZnK/vImt/v9WlHLRxOuUcEoPDPtAA8r3SulY5e0khGr6MIxPK', 0, 1618652880000,
        'system', 1618652880000, 'system');
insert into tb_sys_user_role
values (1, 1, 100);
```

超级管理员初始化权限后，就可以通过给超级管理员分配权限来查看运营管理的页面了。分配权限后，需要刷新页面重新登录才会生效。
