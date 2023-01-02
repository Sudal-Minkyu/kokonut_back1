package com.app.kokonutuser;

import java.util.List;
import java.util.Map;

public interface DynamicUserRepositoryCustom {

    void userCommonTable(String commonQuery); // 생성, 삭제, 업데이트 실행 공용

    int selectExistTable(String businessNumber); // tableName = businessNumber

    List<Map<String, Object>> selectUserList(String searchQuery);

    int selectUserListCount(String businessNumber);
}
