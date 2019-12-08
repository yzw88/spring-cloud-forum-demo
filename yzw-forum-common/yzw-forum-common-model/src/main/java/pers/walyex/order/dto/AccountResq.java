package pers.walyex.order.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountResq implements Serializable {
    private static final long serialVersionUID = 1625806458298370306L;

    private Integer accountId;

    private String username;

    private String email;
}
