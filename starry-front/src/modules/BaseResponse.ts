/**
 * 请求响应
 * @author axr
 */

// types.ts

export interface BaseResponse<T> {
    code: number;
    data: T;
    message?: string;
    describe?: string;
}
