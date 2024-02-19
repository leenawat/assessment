package com.kbtg.bootcamp.posttest.lottery.exception;

public class LotteryDuplicateException extends RuntimeException {
    public LotteryDuplicateException(String errorMessage) {
        super(errorMessage);
    }
}
