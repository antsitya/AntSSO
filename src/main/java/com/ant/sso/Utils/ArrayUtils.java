package com.ant.sso.Utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {
    public static List buildList(Object... objects) {
        List params = new ArrayList();
        for (Object obj : objects) {
            params.add(obj);
        }
        return params;
    }
}
