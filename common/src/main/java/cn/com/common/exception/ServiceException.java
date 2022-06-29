package cn.com.common.exception;


import java.io.Serializable;

/**
 * <p>ServiceException.java</p> 
 * <p>Service层异常类</p>
 * Service层代码统一抛出的异常类，Action层必须对此类异常进行捕获和处理。
 */
public class ServiceException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = -2440105546095427374L;

	public ServiceException()
	{
		super();
	}

	public ServiceException(String message)
	{
		super(message);
	}

	public ServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ServiceException(Throwable cause)
	{
		super(cause);
	}
}
