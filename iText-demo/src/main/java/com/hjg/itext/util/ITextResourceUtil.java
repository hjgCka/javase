package com.hjg.itext.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/10/12
 */
public class ITextResourceUtil {

    private static ClassLoader cls = ITextResourceUtil.class.getClassLoader();

    public static String getAbsoluteFilePath(String data) {
        return cls.getResource(data).getFile();
    }

    public static final List<List<String>> convert(String src, String separator) throws IOException {
        List<List<String>> resultSet = new ArrayList<>();
        try(BufferedReader br =
                    new BufferedReader(new InputStreamReader(new FileInputStream(src), StandardCharsets.UTF_8))) {

            String line;
            List<String> record;
            while((line = br.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, separator);
                record = new ArrayList<>();
                while(tokenizer.hasMoreTokens()) {
                    record.add(tokenizer.nextToken());
                }

                resultSet.add(record);
            }

            return resultSet;
        } catch (IOException e) {
            throw e;
        }
    }
}
