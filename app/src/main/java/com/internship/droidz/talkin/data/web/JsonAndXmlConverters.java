package com.internship.droidz.talkin.data.web;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Novak Alexandr on 03.02.2017.
 */

public final class JsonAndXmlConverters {
    @Retention(RUNTIME)
    @interface Json {
    }

    @Retention(RUNTIME)
    @interface Xml {
    }}
