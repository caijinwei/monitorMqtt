package com.wecon.common.test;

import com.wecon.common.enums.FwVersionOption;
import com.wecon.common.enums.ProjectOption;
import com.wecon.common.enums.ResourceTypeOption;
import com.wecon.common.util.EnumUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by fengbing on 2016/1/8.
 */
public class EnumUtilTest
{
    @Test
    public void toSingleEnum()
    {
        ProjectOption projectOption = EnumUtil.toEnum(ProjectOption.class, 1);
        Assert.assertEquals(ProjectOption.MOVETOANDROID.getValue(), projectOption.getValue());

        projectOption = EnumUtil.toEnum(ProjectOption.class, 2);
        Assert.assertEquals(ProjectOption.ANDROIDGENIUS.getValue(), projectOption.getValue());
    }

    @Test
    public void toMultEnum()
    {
        List<ProjectOption> list = EnumUtil.toEnum(ProjectOption.class, new int[]{1, 2});

        Assert.assertEquals(2, list.size());

        Assert.assertEquals(true, list.contains(ProjectOption.MOVETOANDROID));
        Assert.assertEquals(true, list.contains(ProjectOption.ANDROIDGENIUS));


        list = EnumUtil.toEnum(ProjectOption.class, null);

        Assert.assertEquals(0, list.size());

    }

    @Test
    public void enumKeyTest()
    {
        FwVersionOption[] fw = FwVersionOption.class.getEnumConstants();

        System.out.println(fw[0].name());
        System.out.println(FwVersionOption.ANDROID.name());

        //printAll(FwVersionOption.class);
        printAll(ResourceTypeOption.class);
    }

    private <T extends Enum> T printAll(Class<T> cls)
    {
        T[] arr = cls.getEnumConstants();
        for (T t : arr)
        {
            System.out.println(t.name());
        }
        return arr[0];
    }


}
