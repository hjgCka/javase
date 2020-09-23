package com.hjg.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/9/23
 */
public class FtpUtil {

    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    /**
     * 连接FTP服务器
     * @param ftpAddress    FTP服务器IP地址
     * @param port          FTP服务器端口
     * @return
     */
    public static FTPClient connectFtpServer(String ftpAddress, int port){
        FTPClient ftpClient = new FTPClient();

        //每5分钟(300秒)发送一次NOOP指令，保持命令传输连接在上传或下载时处于连接状态
        ftpClient.setControlKeepAliveTimeout(300);

        //Set how long to wait for control keep-alive message replies
        //默认为1000毫秒，即1秒。这里的参数单位为毫秒
        ftpClient.setControlKeepAliveReplyTimeout(3000);

        //Saves the character encoding to be used by the FTP control connection
        ftpClient.setControlEncoding(StandardCharsets.UTF_8.toString());

        try {
            ftpClient.connect(ftpAddress, port);
            // After connection attempt, you should check the reply code to verify success.
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                logger.error("initFtpClient(),ftp服务器拒绝连接,,INPUT:ftpAddress={},port={}", ftpAddress, port);
                return null;
            }
        } catch (IOException e) {
            logger.error("initFtpClient(),连接ftp服务器发生异常,INPUT:ftpAddress={},port={}", ftpAddress, port, e);
            return null;
        }

        logger.info("initFtpClient(),成功连接ftp服务器,INPUT:ftpAddress={},port={}", ftpAddress, port);
        return ftpClient;
    }

    /**
     * 登陆FTP服务器
     * @param ftpClient      FtpClient
     * @param username       用户名
     * @param password       密码
     * @return
     */
    public static boolean loginFtpServer(FTPClient ftpClient, String username, String password){
        try {
            if (!ftpClient.login(username, password)) {
                ftpClient.logout();
                logger.warn("loginFtpServer(),登录失败,INPUT:username={}", username);
                return false;
            }
        } catch (IOException e) {
            logger.error("loginFtpServer(),登录发生异常,INPUT:username={}", username, e);
            return false;
        }

        // in theory this should not be necessary as servers should default to ASCII
        // but they don't all do so - see NET-500
        try {
            ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
        } catch (IOException e) {
            logger.error("loginFtpServer(),设置文件类型异常", e);
            return false;
        }
        // Use passive mode as default because most of us are
        // behind firewalls these days.
        ftpClient.enterLocalPassiveMode();

        logger.info("loginFtpServer(),成功登录ftp服务器,INPUT:username={}", username);
        return true;
    }

    /**
     * 列出远程目录的文件
     * @param ftpClient      FtpClient。
     * @param remote         远程目录。
     * @return
     */
    public static List<String> listFtpFiles(FTPClient ftpClient, String remote){
        List<String> files = null;
        /*try {
            files = new ArrayList<>();
            for (String s : ftpClient.listNames(remote)) {
                files.add(s);
            }
        } catch (IOException e) {
            logger.error("listFtpFiles(),列出FTP服务器文件异常,INPUT:remote={}", remote, e);
        }*/
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(remote, e -> {
                return e.isFile();
            });
            if(remote.endsWith("/")){
                files = Arrays.stream(ftpFiles).map(f -> {
                    String fileName = f.getName();
                    return remote + fileName;
                }).collect(Collectors.toList());
            } else {
                files = Arrays.stream(ftpFiles).map(f -> {
                    String fileName = f.getName();
                    return remote + "/" + fileName;
                }).collect(Collectors.toList());
            }
        } catch (IOException e) {
            logger.error("listFtpFiles(),列出FTP服务器文件异常,INPUT:remote={}", remote, e);
        }

        return files;
    }

    /**
     * 下载批量远程文件，且数目不能超过最后的参数。
     * @param ftpClient
     * @param remoteFiles
     * @param localDir
     * @param limit
     * @return
     */
    public static int downloadListFileWithLimit(FTPClient ftpClient, List<String> remoteFiles, String localDir, int limit){
        int sum = 0;
        for(String remoteFile : remoteFiles){
            String localFile = localDir + remoteFile;
            sum += downloadFtpFile(ftpClient, remoteFile, localFile);
            if(sum >= limit){
                break;
            }
        }
        return sum;
    }

    /**
     * 下载批量远程文件。
     * @param ftpClient
     * @param remoteFiles
     * @param localDir
     */
    public static void downloadListFile(FTPClient ftpClient, List<String> remoteFiles, String localDir){
        for(String remoteFile : remoteFiles){
            String localFile = localDir + remoteFile;
            downloadFtpFile(ftpClient, remoteFile, localFile);
        }
    }

    /**
     * 下载单个远程文件，成功返回1，失败返回0。
     * @param ftpClient
     * @param remote
     * @param local
     * @return
     */
    public static int downloadFtpFile(FTPClient ftpClient, String remote, String local){
        Path path = Paths.get(local);
        if(Files.exists(path)){
            logger.info("downloadFtpFile(),该远程文件在本地已存在,INPUT:remote={}, local={}", remote, local);
            return 0;
        }
        try {
            OutputStream output = new FileOutputStream(local);
            ftpClient.retrieveFile(remote, output);
            output.close();
            logger.info("downloadFtpFiles(),成功下载ftp服务器文件到本地,INPUT:remote={},local={}", remote, local);
            return 1;
        } catch (IOException e) {
            logger.error("downloadFtpFiles(),下载文件时异常,INPUT:remote={},local={}", remote, local, e);
            try {
                Files.delete(path);
            } catch (IOException ex) {
                logger.error("downloadFtpFiles(),删除文件时异常,INPUT:path={}", path, ex);
            }
            return 0;
        }
    }

    /**
     * 退出FTP服务器。
     * @param ftpClient
     */
    public static void logoutFtpServer(FTPClient ftpClient){
        try {
            // check that control connection is working OK
            ftpClient.noop();
            ftpClient.logout();
        } catch (IOException e) {
            logger.error("logoutFtpServer(),登出时发生异常", e);
        }
    }

}
