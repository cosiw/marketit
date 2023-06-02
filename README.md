# marketit

## 스웨거 주소
localhost:8082/swagger-ui/index.html#/


## 실행 방법

### Requirements
 - Java SE 11


### Installation & build

```
$ git clone https://github.com/cosiw/marketit.git
$ cd marketit
$ ./gradlew build
$ cd build/libs
$ java -jar order-0.0.1-SNAPSHOT.jar
```

## DB ERD
![image](https://github.com/cosiw/marketit/assets/91179733/c1dd0cb7-6316-426d-a2af-1d36edf393c5)

### 테이블 생성 쿼리
```
CREATE TABLE `torder` (
	`order_idx` INT(11) NOT NULL AUTO_INCREMENT,
	`item_name` VARCHAR(200) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	`user_idx` INT(11) NULL DEFAULT NULL,
	`address` VARCHAR(200) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	`status` CHAR(1) NULL DEFAULT NULL COMMENT '접수 : R, 완료 :C' COLLATE 'utf8mb4_general_ci',
	`complete_user` INT(11) NOT NULL DEFAULT '0',
	PRIMARY KEY (`order_idx`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1
;
```

## 설명
 - Spring boot와 JPA를 이용하여 개발하였습니다.
 - 주문 접수에서의 인증(유저)에 대한 내용은 requestDTO에 추가하여 임의로 추가할 수 있도록 구현하였습니다.
 - 주문 완료에서 해당 주문을 완료 처리한 유저에 대한 내용은 RequestParam을 통해 입력받을 수 있도록 구현하였습니다.

