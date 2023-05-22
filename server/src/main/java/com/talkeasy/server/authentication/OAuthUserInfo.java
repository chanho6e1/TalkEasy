package com.talkeasy.server.authentication;

import java.util.Map;

public interface OAuthUserInfo {


    String getName();

    Map<String, Object> getAttributes();
}
