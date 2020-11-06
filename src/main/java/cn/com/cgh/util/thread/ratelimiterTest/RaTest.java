package cn.com.cgh.util.thread.ratelimiterTest;

import com.google.common.util.concurrent.RateLimiter;

import java.time.Duration;
import java.time.Instant;

public class RaTest {
    private final RateLimiter rateLimiter = RateLimiter.create(1.0);

    public static void main(String[] args) {
        RaTest raTest = new RaTest();
        String a = "ab";
        System.out.println(a.getBytes().length);
        raTest.aaa(a.getBytes());

    }

    public void aaa(byte[] a){
        Instant now = Instant.now();
        System.out.println(rateLimiter.acquire(a.length));
        rateLimiter.acquire(a.length);
        System.out.println(Duration.between(now,Instant.now()));
        System.out.println(rateLimiter.getRate());
    }
}
