
-- ❗ 데이터 복사 성공 여부 꼭 확인해주세요 ❗


-- -----------------------------------------------------
-- Table `ssafytrip`에서 `aboha`로 `sidos` 데이터 복사
-- -----------------------------------------------------
insert into `aboha`.`sidos`
select `ssafytrip`.`sidos`.*
from `ssafytrip`.`sidos`;

-- -----------------------------------------------------
-- Table `ssafytrip`에서 `aboha`로 `guguns` 데이터 복사
-- -----------------------------------------------------
insert into `aboha`.`guguns`
select `ssafytrip`.`guguns`.*
from `ssafytrip`.`guguns`;

-- -----------------------------------------------------
-- Table `ssafytrip`에서 `aboha`로 `contenttypes` 데이터 복사
-- -----------------------------------------------------
insert into `aboha`.`contenttypes`
select `ssafytrip`.`contenttypes`.*
from `ssafytrip`.`contenttypes`;

-- -----------------------------------------------------
-- Table `ssafytrip`에서 `aboha`로 `attractions` 데이터 복사
-- -----------------------------------------------------
INSERT INTO `aboha`.`attractions` (
    `no`, `content_id`, `title`, `content_type_id`, `area_code`, `si_gun_gu_code`,
    `first_image1`, `first_image2`, `map_level`, `latitude`, `longitude`,
    `tel`, `addr1`, `addr2`, `homepage`, `overview`
)
SELECT
    `no`, `content_id`, `title`, `content_type_id`, `area_code`, `si_gun_gu_code`,
    `first_image1`, `first_image2`, `map_level`, `latitude`, `longitude`,
    `tel`, `addr1`, `addr2`, `homepage`, `overview`
FROM
    `ssafytrip`.`attractions`;


-- -----------------------------------------------------
-- Table `aboha`에서 `attractions` overview null인 데이터 제거
-- -----------------------------------------------------
SET SQL_SAFE_UPDATES = 0;
delete from `aboha`.`attractions` where overview is null;
SET SQL_SAFE_UPDATES = 1;



-- ✨ 제발 commit 해주세요! ✨
commit;