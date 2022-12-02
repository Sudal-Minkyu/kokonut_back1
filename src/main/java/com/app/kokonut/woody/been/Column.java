package com.app.kokonut.woody.been;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Woody
 * Date : 2022-11-03
 * Time :
 * Remark : 테이블 컬럼별 데이터 가져오는 클래스
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Column {
	private String field;
	private String type;
	private String collation;
	private String nullable;
	private String key;
	private String defaultValue;
	private String extra;
	private String privileges;
	private String comment;
}
