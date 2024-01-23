package org.commons.importing.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 *
 * @Description: 全局业务异常
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class CheckException extends RuntimeException {

	private String errMsg;

	public CheckException(String errMsg) {
		super(errMsg);
		this.errMsg = errMsg;
	}

}
