package com.hjg;

import com.hjg.itext.util.MyResourceUtil;
import org.junit.Test;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/10/12
 */
public class UtilTest {

    @Test
    public void resourceTest() {
        String csv1 = "united_states.csv";
        System.out.println(MyResourceUtil.getFileName(csv1));

        String csv2 = "ufo.csv";
        System.out.println(MyResourceUtil.getFileName(csv2));
    }
}
