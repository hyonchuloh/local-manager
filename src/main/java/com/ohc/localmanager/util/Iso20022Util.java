package com.ohc.localmanager.util;

/**
 * BOK-Wire "AH"(Additional Hangul) 텍스트 타입의 공통 허용 문자셋 검증 유틸리티.
 *
 * <p>BOK-Wire 스키마의 {@code BOK_Max(nnn)TextAH} 타입(BOK_Max16TextAH, BOK_Max35TextAH,
 * BOK_Max70TextAH, BOK_Max105TextAH, BOK_Max140TextAH, BOK_Max350TextAH)은 길이(16~350)만
 * 다를 뿐 <b>허용 문자셋은 모두 동일</b>하다. 이 클래스는 그 공통 문자셋(한글 사용 가능 범위)에
 * 문자열이 포함되는지만 검증한다. (길이 검증은 호출 측에서 별도로 수행)
 *
 * <p>허용 문자 = SWIFT-x 문자셋 + BOK 확장 특수문자 + 한글/CJK 등 유니코드 블록:
 * <ul>
 *   <li>ASCII 영숫자: {@code 0-9 A-Z a-z}</li>
 *   <li>SWIFT-x 특수문자 및 공백: {@code / - ? : ( ) . , ' +} 와 space</li>
 *   <li>BOK 확장 특수문자: {@code ! # $ % & * = ^ _ ` { | } ~ " ; < > @ [ \ ]}</li>
 *   <li>U+AC00‥U+D7A3 한글 음절(Hangul Syllables)</li>
 *   <li>U+20A0‥U+20C0 통화 기호(Currency Symbols)</li>
 *   <li>U+2460‥U+24FF 원 문자(Enclosed Alphanumerics)</li>
 *   <li>U+3000‥U+303F CJK 기호 및 문장부호(CJK Symbols and Punctuation)</li>
 *   <li>U+3200‥U+32FF CJK 괄호/원 문자(Enclosed CJK Letters and Months)</li>
 *   <li>U+3300‥U+33FF CJK 호환 문자(CJK Compatibility)</li>
 *   <li>U+3400‥U+4DBF CJK 통합 한자 확장 A(CJK Unified Ideographs Ext. A)</li>
 *   <li>U+4E00‥U+9FFF CJK 통합 한자(CJK Unified Ideographs)</li>
 *   <li>U+F900‥U+FA6D CJK 호환 한자(CJK Compatibility Ideographs)</li>
 *   <li>U+FF01‥U+FFEE 전각/반각 문자(Halfwidth and Fullwidth Forms)</li>
 * </ul>
 *
 * <p>원본 XSD pattern (length 양자({@code {1,nnn}}) 제외):
 * <pre>[0-9a-zA-Z/\-\?:\(\)\.,'\+ !#$%&amp;\*=^_`\{\|\}~";&lt;&gt;@\[\\\] 그리고 위 유니코드 블록들]</pre>
 *
 * <p>허용 문자는 모두 BMP(≤ U+FFEE)에 속하므로 {@code char} 단위 검사로 충분하다. 입력에
 * 보충 문자(surrogate pair, 예: 이모지)가 있으면 surrogate 코드 단위가 허용 범위 밖이므로
 * 자연히 불허로 판정된다.
 *
 */
public final class Iso20022Util {

    /**
     * 단일 문자가 AH 허용 문자셋에 포함되는지 검사한다.
     * 2026.6.25 신규 추가: BOK 확장 특수문자 허용 범위 추가
     *
     * @param c 검사할 문자
     * @return 허용 문자면 true
     */
    public static boolean isAllowedAhChar(char c) {
        if (c >= '0' && c <= '9') return true;
        if (c >= 'A' && c <= 'Z') return true;
        if (c >= 'a' && c <= 'z') return true;
        switch (c) {
            // SWIFT-x 특수문자 + 공백
            case '/': case '-': case '?': case ':': case '(': case ')':
            case '.': case ',': case '\'': case '+': case ' ':
            // BOK 확장 특수문자
            case '!': case '#': case '$': case '%': case '&': case '*':
            case '=': case '^': case '_': case '`': case '{': case '|':
            case '}': case '~': case '"': case ';': case '<': case '>':
            case '@': case '[': case '\\': case ']':
                return true;
            default:
                break;
        }
        // 유니코드 블록 경계는 코드포인트 범위로 비교한다.
        return (c >= '가' && c <= '힣')   // Hangul Syllables  U+AC00..U+D7A3
            || (c >= '₠' && c <= '⃀')   // Currency Symbols
            || (c >= '①' && c <= '⓿')   // Enclosed Alphanumerics
            || (c >= '　' && c <= '〿')   // CJK Symbols and Punctuation
            || (c >= '㈀' && c <= '㋿')   // Enclosed CJK Letters and Months
            || (c >= '㌀' && c <= '㏿')   // CJK Compatibility
            || (c >= '㐀' && c <= '䶿')   // CJK Unified Ideographs Ext. A
            || (c >= '一' && c <= '鿿')   // CJK Unified Ideographs
            || (c >= '豈' && c <= '舘')   // CJK Compatibility Ideographs
            || (c >= '！' && c <= '￮');  // Halfwidth and Fullwidth Forms
    }

    /**
     * 문자열의 모든 문자가 AH 허용 문자셋에 포함되는지 검사한다.
     * (길이는 검사하지 않음 — 호출 측에서 타입별 최대 길이를 별도 적용)
     * 2026.6.25 신규 추가: BOK 확장 특수문자 허용 범위 추가
     *
     * @param text 검사할 문자열
     * @return null이면 false, 빈 문자열이면 true(불허 문자 없음), 그 외엔 전부 허용 문자일 때 true
     */
    public static boolean containsOnlyAllowedAhChars(String text) {
        if (text == null) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            if (!isAllowedAhChar(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /** 
     * 테스트용 main 메서드.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        String test = "ＴＥＳＴ－１００００ ＫＢ증권, ＮＨ투자증권, ＳＫ증권, ㈜한국시료, " +
                      "＜홍＞길동, (전각공백)09810118-85-8, 06979938104€, 83722847-47-19㏇, " +
                      "이율보증）軀광奔ⓢ";
          
        if ( !Iso20022Util.containsOnlyAllowedAhChars(test) ) {
            throw new IllegalArgumentException("허용 문자셋에 포함되지 않는 문자가 있습니다: " + test);
        }
        System.out.println("정상: " + test);
    }
}
