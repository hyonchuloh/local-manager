/**
 * BOK-Wire "AH"(Additional Hangul) 텍스트 타입의 공통 허용 문자셋 검증 유틸리티 (JS 포트).
 *
 * BOK-Wire 스키마의 BOK_Max(nnn)TextAH 타입(BOK_Max16TextAH, BOK_Max35TextAH,
 * BOK_Max70TextAH, BOK_Max105TextAH, BOK_Max140TextAH, BOK_Max350TextAH)은 길이(16~350)만
 * 다를 뿐 허용 문자셋은 모두 동일하다. 이 모듈은 그 공통 문자셋(한글 사용 가능 범위)에
 * 문자열이 포함되는지만 검증한다. (길이 검증은 호출 측에서 별도로 수행)
 *
 * 허용 문자 = SWIFT-x 문자셋 + BOK 확장 특수문자 + 한글/CJK 등 유니코드 블록:
 *   - ASCII 영숫자: 0-9 A-Z a-z
 *   - SWIFT-x 특수문자 및 공백: / - ? : ( ) . , ' +  와 space
 *   - BOK 확장 특수문자: ! # $ % & * = ^ _ ` { | } ~ " ; < > @ [ \ ]
 *   - U+AC00‥U+D7A3 한글 음절(Hangul Syllables)
 *   - U+20A0‥U+20C0 통화 기호(Currency Symbols)
 *   - U+2460‥U+24FF 원 문자(Enclosed Alphanumerics)
 *   - U+3000‥U+303F CJK 기호 및 문장부호(CJK Symbols and Punctuation)
 *   - U+3200‥U+32FF CJK 괄호/원 문자(Enclosed CJK Letters and Months)
 *   - U+3300‥U+33FF CJK 호환 문자(CJK Compatibility)
 *   - U+3400‥U+4DBF CJK 통합 한자 확장 A(CJK Unified Ideographs Ext. A)
 *   - U+4E00‥U+9FFF CJK 통합 한자(CJK Unified Ideographs)
 *   - U+F900‥U+FA6D CJK 호환 한자(CJK Compatibility Ideographs)
 *   - U+FF01‥U+FFEE 전각/반각 문자(Halfwidth and Fullwidth Forms)
 *
 * 허용 문자는 모두 BMP(<= U+FFEE)에 속하므로 JS 문자열의 UTF-16 코드 단위(char) 검사로
 * 충분하다. 입력에 보충 문자(surrogate pair, 예: 이모지)가 있으면 surrogate 코드 단위가
 * 허용 범위 밖이므로 자연히 불허로 판정된다. (Java char 단위 검사와 동일한 의미)
 *
 * 원본: com.ohc.localmanager.util.BokAhTextValidator (Java)
 * @author 오현철
 * @date 2026.06.25
 */
(function (root, factory) {
    if (typeof module === 'object' && module.exports) {
        module.exports = factory();           // CommonJS / Node
    } else {
        root.BokAhTextValidator = factory();  // 브라우저 전역
    }
}(typeof self !== 'undefined' ? self : this, function () {
    'use strict';

    /**
     * AH 타입 공통 허용 문자셋의 정규식 문자 클래스(양자 제외).
     * 재사용(직접 RegExp 조합)이 필요한 호출 측을 위해 공개한다.
     */
    var ALLOWED_CHAR_CLASS =
        "[0-9A-Za-z/\\-?:().,'+ !#$%&*=^_`{|}~\";<>@\\[\\\\\\]" +
        "\\uAC00-\\uD7A3" +   // Hangul Syllables
        "\\u20A0-\\u20C0" +   // Currency Symbols
        "\\u2460-\\u24FF" +   // Enclosed Alphanumerics
        "\\u3000-\\u303F" +   // CJK Symbols and Punctuation
        "\\u3200-\\u32FF" +   // Enclosed CJK Letters and Months
        "\\u3300-\\u33FF" +   // CJK Compatibility
        "\\u3400-\\u4DBF" +   // CJK Unified Ideographs Ext. A
        "\\u4E00-\\u9FFF" +   // CJK Unified Ideographs
        "\\uF900-\\uFA6D" +   // CJK Compatibility Ideographs
        "\\uFF01-\\uFFEE" +   // Halfwidth and Fullwidth Forms
        "]";

    /** 1글자 이상 전체가 허용 문자인지 검사하는 컴파일된 패턴(XSD pattern과 동등, 길이 무제한). */
    var ALLOWED_PATTERN = new RegExp("^(?:" + ALLOWED_CHAR_CLASS + ")+$");

    /**
     * 단일 문자가 AH 허용 문자셋에 포함되는지 검사한다.
     *
     * @param {string} ch 검사할 문자(1글자). 길이>1이면 첫 코드 단위만 본다.
     * @returns {boolean} 허용 문자면 true
     */
    function isAllowedChar(ch) {
        var c = ch.charCodeAt(0);
        if (c >= 0x30 && c <= 0x39) return true;  // 0-9
        if (c >= 0x41 && c <= 0x5A) return true;  // A-Z
        if (c >= 0x61 && c <= 0x7A) return true;  // a-z
        switch (c) {
            // SWIFT-x 특수문자 + 공백
            case 0x2F: // /
            case 0x2D: // -
            case 0x3F: // ?
            case 0x3A: // :
            case 0x28: // (
            case 0x29: // )
            case 0x2E: // .
            case 0x2C: // ,
            case 0x27: // '
            case 0x2B: // +
            case 0x20: // space
            // BOK 확장 특수문자
            case 0x21: // !
            case 0x23: // #
            case 0x24: // $
            case 0x25: // %
            case 0x26: // &
            case 0x2A: // *
            case 0x3D: // =
            case 0x5E: // ^
            case 0x5F: // _
            case 0x60: // `
            case 0x7B: // {
            case 0x7C: // |
            case 0x7D: // }
            case 0x7E: // ~
            case 0x22: // "
            case 0x3B: // ;
            case 0x3C: // <
            case 0x3E: // >
            case 0x40: // @
            case 0x5B: // [
            case 0x5C: // \
            case 0x5D: // ]
                return true;
            default:
                break;
        }
        // 유니코드 블록 경계는 코드포인트 범위로 비교한다.
        return (c >= 0xAC00 && c <= 0xD7A3)   // Hangul Syllables
            || (c >= 0x20A0 && c <= 0x20C0)   // Currency Symbols
            || (c >= 0x2460 && c <= 0x24FF)   // Enclosed Alphanumerics
            || (c >= 0x3000 && c <= 0x303F)   // CJK Symbols and Punctuation
            || (c >= 0x3200 && c <= 0x32FF)   // Enclosed CJK Letters and Months
            || (c >= 0x3300 && c <= 0x33FF)   // CJK Compatibility
            || (c >= 0x3400 && c <= 0x4DBF)   // CJK Unified Ideographs Ext. A
            || (c >= 0x4E00 && c <= 0x9FFF)   // CJK Unified Ideographs
            || (c >= 0xF900 && c <= 0xFA6D)   // CJK Compatibility Ideographs
            || (c >= 0xFF01 && c <= 0xFFEE);  // Halfwidth and Fullwidth Forms
    }

    /**
     * 문자열의 모든 문자가 AH 허용 문자셋에 포함되는지 검사한다.
     * (길이는 검사하지 않음 — 호출 측에서 타입별 최대 길이를 별도 적용)
     *
     * @param {string} text 검사할 문자열
     * @returns {boolean} null/undefined면 false, 빈 문자열이면 true(불허 문자 없음),
     *                    그 외엔 전부 허용 문자일 때 true
     */
    function containsOnlyAllowed(text) {
        if (text === null || text === undefined) {
            return false;
        }
        for (var i = 0; i < text.length; i++) {
            if (!isAllowedChar(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 허용되지 않는 첫 문자의 인덱스를 반환한다. (오류 메시지/로그용)
     *
     * @param {string} text 검사할 문자열
     * @returns {number} 불허 문자의 첫 인덱스, 전부 허용이면 -1 (null/undefined면 -1)
     */
    function indexOfIllegalChar(text) {
        if (text === null || text === undefined) {
            return -1;
        }
        for (var i = 0; i < text.length; i++) {
            if (!isAllowedChar(text.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 컴파일된 패턴 기반 검사(1글자 이상 전체가 허용 문자). XSD pattern 의미와 동일하며,
     * 빈 문자열은 false 가 된다. (문자 단위 검사인 containsOnlyAllowed와 빈 문자열 처리만 다름)
     *
     * @param {string} text 검사할 문자열
     * @returns {boolean} null/undefined/빈 문자열이면 false, 1글자 이상이고 전부 허용 문자면 true
     */
    function matchesPattern(text) {
        return (text !== null && text !== undefined) && ALLOWED_PATTERN.test(text);
    }

    return {
        ALLOWED_CHAR_CLASS: ALLOWED_CHAR_CLASS,
        isAllowedChar: isAllowedChar,
        containsOnlyAllowed: containsOnlyAllowed,
        indexOfIllegalChar: indexOfIllegalChar,
        matchesPattern: matchesPattern
    };
}));
