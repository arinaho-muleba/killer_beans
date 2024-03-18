package com.killerbean.shell.enums;

public enum Urls {
    BASE_URL("http://killer-beans-env.eba-ccgh92bv.eu-west-1.elasticbeanstalk.com/api/v1");

    private final String url;

    Urls(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
