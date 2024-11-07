package com.example.managersystem.hanbler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = "body")
@Data
public class ReturnMessage<T> {

    @Getter
    @Setter
    private ReturnState state;

    @Getter
    @Setter
    private T body;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String errorCode;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String[] errorParams;

    /**
     * 页面国际化错误提示
     * 原message
     */
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String errorMessage;

    @Getter
    @Setter
    private String requestId;

    public ReturnMessage() {

    }

    public ReturnMessage(final ReturnState state) {

        this();
        this.state = state;
    }

    public ReturnMessage(final ReturnState state, final T body) {

        this();
        this.state = state;
        if (ReturnState.OK != state) {
            this.errorCode = String.valueOf(body);
        } else {
            this.body = body;
        }
    }

    public ReturnMessage(final ReturnState state, final String errorCode, final String... errorParams) {

        this();
        this.state = state;
        this.errorCode = errorCode;
        this.errorParams = errorParams;
    }
}
