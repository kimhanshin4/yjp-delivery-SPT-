package com.yjp.delivery.common.meta;

public enum ResultCode {
    SUCCESS(0, "정상 처리 되었습니다"),
    SYSTEM_ERROR(1000, "알 수 없는 애러가 발생했습니다."),
    NOT_FOUND_SAMPLE(1001, "샘플 데이터가 없습니다."),
    NOT_FOUND_USER(2000, "존재하지 않는 유저입니다."),
    NOT_FOUND_SHOP(4001, "가게 데이터가 없습니다."),
    NOT_FOUND_REVIEW(4002, "리뷰 데이터가 없습니다"),
    NOT_FOUND_MENU(5001, "메뉴 데이터가 없습니다."),
    ALREADY_LIKED_SHOP(6000, "이미 좋아요가 눌린 가게입니다."),
    NOT_YET_LIKED_SHOP(6001, "아직 좋아요를 누르지 않은 가게입니다.");

    private Integer code;
    private String message;

    ResultCode(Integer code, String errorMessage) {
        this.code = code;
        this.message = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
