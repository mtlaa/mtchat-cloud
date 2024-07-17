package com.mtlaa.api.domain.websocket.vo;

import com.mtlaa.api.domain.websocket.enums.WebSocketResponseTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create 2023/11/30 15:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketResponse<T> {
    /**
     * @see WebSocketResponseTypeEnum
     */
    private Integer type;
    private T data;
}
