
/*打开事件*/
 SET GLOBAL event_scheduler = 'ON';
 
 /*创建更新数据的存储过程*/
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ON_TIME_CANCEL_ROOOM`()
BEGIN
update fg_reservation_room set status='ON_TIME_CANCEL' where date(DATE_ADD(consumeDate,interval 1 day))=date(now());
END$$
DELIMITER ;


/*创建定时器，从2016-01-08 12:00开始执行，每天执行一次*/
create event ON_TIME_CANCEL_ROOOM_EVENT
on schedule every 1 day STARTS '2016-01-08 12:00:00'
on completion preserve disable
do call ON_TIME_CANCEL_ROOOM();


/*打开定时器*/
alter event ON_TIME_CANCEL_ROOOM_EVENT on completion preserve enable;