insert into SKILL values ('LUCKYMAN', '強運者', '運', 'VILLAGER', 13);
insert into SKILL values ('COMMANDER', '指揮官', '指', 'VILLAGER', 14);

update SKILL set disp_order = 13 where skill_code = 'LUCKYMAN';
update SKILL set disp_order = 14 where skill_code = 'COMMANDER';
update SKILL set disp_order = 15 where skill_code = 'WEREWOLF';
update SKILL set disp_order = 16 where skill_code = 'CURSEWOLF';
update SKILL set disp_order = 17 where skill_code = 'WISEWOLF';
update SKILL set disp_order = 18 where skill_code = 'MADMAN';
update SKILL set disp_order = 19 where skill_code = 'CMADMAN';
update SKILL set disp_order = 20 where skill_code = 'EVILMEDIUM';
update SKILL set disp_order = 21 where skill_code = 'FANATIC';
update SKILL set disp_order = 22 where skill_code = 'LOVER';
update SKILL set disp_order = 23 where skill_code = 'COHABITER';
update SKILL set disp_order = 24 where skill_code = 'FOX';
update SKILL set disp_order = 25 where skill_code = 'IMMORAL';
update SKILL set disp_order = 26 where skill_code = 'BOMBER';
update SKILL set disp_order = 27 where skill_code = 'LEFTOVER';
update SKILL set disp_order = 28 where skill_code = 'VILLAGERS';
update SKILL set disp_order = 29 where skill_code = 'WEREWOLFS';
update SKILL set disp_order = 30 where skill_code = 'LOVERS';
update SKILL set disp_order = 31 where skill_code = 'NOVILLAGERS';
update SKILL set disp_order = 32 where skill_code = 'FOOTSTEPS';
update SKILL set disp_order = 33 where skill_code = 'FRIENDS';

insert into ABILITY_TYPE values ('COMMAND', '指揮');