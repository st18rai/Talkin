package com.internship.droidz.talkin.data.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Novak Alexandr on 03.02.2017.
 */

public final class JsonAndXmlConverters {
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Json {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Xml {
    }}
