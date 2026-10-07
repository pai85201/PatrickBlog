package com.paitrick.blog.common.response; public record ApiResponse<T>(int code,String message,T data){public static <T> ApiResponse<T> ok(T data){return new ApiResponse<>(0,"success",data);}}
