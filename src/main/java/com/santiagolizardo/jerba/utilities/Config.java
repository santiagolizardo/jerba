package com.santiagolizardo.jerba.utilities;

import java.util.LinkedHashMap;
import java.util.Map;

public class Config {

    private boolean appspotDomainAllowed;
    private boolean insecureHttpAllowed;

    private Map<String, String> redirections;

    public Config() {
        appspotDomainAllowed = true;
        insecureHttpAllowed = false;

        redirections = new LinkedHashMap<>();
    }

    public void setAppspotDomainAllowed(boolean appspotDomainAllowed) {
        this.appspotDomainAllowed = appspotDomainAllowed;
    }

    public boolean isAppspotDomainAllowed() {
        return appspotDomainAllowed;
    }

    public void setInsecureHttpAllowed(boolean insecureHttpAllowed) {
        this.insecureHttpAllowed = insecureHttpAllowed;
    }

    public boolean isInsecureHttpAllowed() {
        return insecureHttpAllowed;
    }

    public Map<String, String> getRedirections() {
        return redirections;
    }
}
