package org.wfq.wufangquan.controller.requestFormation;

public record userInfoModifyRequest(
    String account,
    String passwordHash,
    String newPasswordHash,
    uploadSubmitRequest icon
) {
}
