package org.application.dto;

import org.app.entity.Sport;

import java.util.List;

public class CenterQr {
    private List<Sport> sports;
    private String qrCodeBase64;

    public CenterQr(List<Sport> sports, String qrCodeBase64) {
        this.sports = sports;
        this.qrCodeBase64 = qrCodeBase64;
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }

    public String getQrCodeBase64() {
        return qrCodeBase64;
    }

    public void setQrCodeBase64(String qrCodeBase64) {
        this.qrCodeBase64 = qrCodeBase64;
    }
}

