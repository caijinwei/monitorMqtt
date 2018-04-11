package com.wecon.box.util;

import com.wecon.box.config.BoxWebConfig;

/**
 * Created by zengzhipeng on 2017/8/4.
 */
public class BoxWebConfigContext {
    public static BoxWebConfig boxWebConfig;

    public void setBoxWebConfig(BoxWebConfig boxWebConfig) {
        BoxWebConfigContext.boxWebConfig = boxWebConfig;
    }
}
