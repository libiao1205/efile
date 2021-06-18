package com.sundeinfo.sypglass.relay.domain;

import com.sundeinfo.core.domain.Entity;
import com.sundeinfo.sypglass.relay.define.RelayStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class RelayProcessInfo extends Entity implements Serializable {

    private byte relayStatus;

    private String relayFilePath;

    private String relayInfo;


    public static RelayProcessInfo initialize(long id, RelayStatus status,String relayFilePath,String relayInfo){
        RelayProcessInfo info = new RelayProcessInfo();
        info.setId(id);
        info.setRelayStatus((byte) status.code());
        info.setRelayFilePath(relayFilePath);
        info.setRelayInfo(relayInfo);
        return info;
    }
}