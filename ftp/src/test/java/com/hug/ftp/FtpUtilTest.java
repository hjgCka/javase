package com.hug.ftp;

import com.hjg.ftp.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.util.List;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/9/24
 */
public class FtpUtilTest {

    private static final String address = "10.168.55.88";
    private static final int port = 21;

    private static final String username = "ftpuser";
    private static final String password = "acct4rcs";

    @Test
    public void listFileTest() {

        String remote = "/";

        FTPClient ftpClient = FtpUtil.connectFtpServer(address, port);
        boolean successed = FtpUtil.loginFtpServer(ftpClient, username, password);
        if(successed) {
            List<String> list = FtpUtil.listFtpFiles(ftpClient, remote);
            System.out.println("list = " + list);

            FtpUtil.logoutFtpServer(ftpClient);
        } else {
            System.out.println("登陆失败");
        }
    }

    @Test
    public void downloadFileTest() {

        String remote = "/data.xlsx", local = "e://data.xlsx";

        FTPClient ftpClient = FtpUtil.connectFtpServer(address, port);
        boolean successed = FtpUtil.loginFtpServer(ftpClient, username, password);
        if(successed) {

            int count = FtpUtil.downloadFtpFile(ftpClient, remote, local);
            System.out.println("下载次数 count = " + count);
        } else {
            System.out.println("登陆失败");
        }
    }
}
