package com.example.secretcommunity.util;

public final class HexUtils {

    private HexUtils() {
        // 유틸리티 클래스이므로 인스턴스 생성 방지
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X", b));
        }
        return hexString.toString();
    }
}
