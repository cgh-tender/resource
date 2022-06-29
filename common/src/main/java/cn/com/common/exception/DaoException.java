/**
 * FileName: DaoException.java
 */
package cn.com.common.exception;

/**
 * <p>
 * DaoException.java
 * </p>
 * <p>
 * Dao层异常类
 * </p>
 * Dao层代码统一抛出的异常类，Service层必须对此类异常进行捕获和处理。
 */
public class DaoException extends ServiceException {
	public DaoException() {
		super();
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}
}
