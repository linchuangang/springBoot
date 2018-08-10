package com.example.Redis;

/**
 * Created by Administrator on 2018/8/9.
 */
public class RedisCacheException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RedisCacheException() {
        super();
    }

    public RedisCacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisCacheException(String message) {
        super(message);
    }

    public RedisCacheException(Throwable cause) {
        super(cause);
    }

}
