package com.huawei.solarsafe.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.bean.GlobalConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES双向加解密算法工具类
 * Created by jwx531163 on 2017/12/20.
 */

public class AESUtil {


    private static final String TAG = "AESUtil";

    private static String path = MyApplication.getContext().getFilesDir().toString();
    /**
     * 根秘钥之硬编码部分
     */
    private static final String FINAL_SECURE_COMPONENT = "8E3471665848F464E3C4D1AA21E2F743";

    /**
     * 生成密文的长度
     */
    private static final int HASH_BIT_SIZE = 128 * 4;
    /**
     * 迭代次数
     */
    private static final int PBKDF2_ITERATIONS = 20000;

    /**
     * 4字节的算法id
     */
    private static byte[] id = new byte[]{1, 2, 3,4};

    private static final String fileName1[] = {"m","x.txt"};

    private static final String fileName2[] = {"n","y.txt"};

    /***
     * AES加密算法加密
     *
     * @param input 数据
     * @throws Exception
     */
    public static String encrypt(String input) {
        if (TextUtils.isEmpty(input)) return "";
        SecureRandom sr = new SecureRandom();
        byte[] randomByte = new byte[16];  //lv值
        sr.nextBytes(randomByte);
        byte[] randomKey = new byte[16];   //工作秘钥
        sr.nextBytes(randomKey);
        try {
            byte[] rootKey = getRootKey();
            if (TextUtils.isEmpty(GlobalConstants.rt_ky)){
                GlobalConstants.rt_ky  = toHex(rootKey);
            }

            //数据密文部分
            byte[] result = aes(input.getBytes("utf-8"),randomKey,randomByte,Cipher.ENCRYPT_MODE);
            //工作秘钥密文部分
            byte[] keyResult = aes(randomKey,rootKey ,randomByte, Cipher.ENCRYPT_MODE);
            //拼接得到最终的加密密文
            return parseBytes2HexStr(id, randomByte, result, keyResult);
        }catch (Exception e){
            Log.e(TAG, "encrypt: "+e.getMessage() );
            return "";
        }
    }


    /***
     * AES加密算法解密
     *
     * @param str 需解密数据（16进制字符串）
     * @throws Exception
     */
    public static String decrypt(String str)  {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        byte[] bytes = toByte(str);
        byte[] iv = getIv(bytes);
        byte[] result = getResult(bytes);
        byte[] key = getKey(bytes);
        try {
            //获取解密后的工作秘钥明文
            byte[] randomKey = aes(key, getRootKey(),iv, Cipher.DECRYPT_MODE);
            //获取解密后的数据明文
            byte[] data = aes(result, randomKey,iv,Cipher.DECRYPT_MODE);
            return new String(data);
        }catch (Exception e){
            Log.e(TAG, "decrypt: "+e.getMessage());
            return "";
        }
    }

    /**
     * 获取拼接组装的根秘钥
     *
     * @throws Exception
     */
    public static byte[] getRootKey() throws Exception {
        if (!TextUtils.isEmpty(GlobalConstants.rt_ky)){
            return  toByte(GlobalConstants.rt_ky);
        }
        //根秘钥的生成则是用安全组件1（代码中写死的硬数据）+2个以文件存储形式的字符串安全组件采用算法得到
        byte[] bytes = toByte(FINAL_SECURE_COMPONENT);
        //从文件中读取安全组件1
        byte[] bytes1 = readComponent(fileName1);
        if (bytes1 == null) return null;
        //从文件中读取安全组件2
        byte[] bytes2 = readComponent(fileName2);
        if (bytes2 == null) return null;
        byte[] temp = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byte i1 = (byte) (bytes[i] ^ bytes1[i] ^ bytes2[i]);
            temp[i] = i1;
        }
        //按位异或之后得到16进制的字符串
        String hex = toHex(temp);
        KeySpec spec = new PBEKeySpec(hex.toCharArray(), toByte(FINAL_SECURE_COMPONENT), PBKDF2_ITERATIONS, HASH_BIT_SIZE);
        //SHA256算法
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return skf.generateSecret(spec).getEncoded();
    }


    /**
     * 创建或更新根秘钥
     */
    public static void updateRootKey(OnUpdateFileListener listener) {
        SecureRandom sr = new SecureRandom();
        byte[] newComponent1 = new byte[16];  //新的安全组件1
        sr.nextBytes(newComponent1);
        byte[] newComponent2 = new byte[16];   //新的安全组件2
        sr.nextBytes(newComponent2);
        updateFile(fileName1, newComponent1,fileName2, newComponent2,listener);
    }


    public interface OnUpdateFileListener{
        void onSuccess();
        void failure();
    }
    /**
     * 覆盖更新、创建写入 安全组件文件
     *
     * @param fileName1       安全组件文件名1
     * @param fileName2       安全组件文件名2
     * @param newComponent1  安全组件的新内容1
     * @param newComponent2  安全组件的新内容2
     */
    private static void updateFile(final String[] fileName1, final byte[] newComponent1,
                                   final String[] fileName2, final byte[] newComponent2,
                                   final OnUpdateFileListener L) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream out1 = null;
                FileOutputStream out2 = null;
                boolean isSuccess1 = false;
                String path1 = path+File.separator+fileName1[0];
                String path2 = path+File.separator+fileName2[0];
                try {
                    File dirFile1  = new File(path1);
                    if (!dirFile1.exists()) {
                        boolean mkdirs = dirFile1.mkdirs();
                    }
                    File file1 = new File(path1+File.separator+fileName1[1]);
                    out1 = new FileOutputStream(file1);
                    out1.write(toHex(newComponent1).getBytes());
                    out1.flush();
                    isSuccess1= true;
                    File dirFile2  = new File(path2);
                    if (!dirFile2.exists()) {
                        boolean mkdirs = dirFile2.mkdirs();
                    }
                    File file2 = new File(path2+File.separator+fileName2[1]);
                    out2 = new FileOutputStream(file2);
                    out2.write(toHex(newComponent2).getBytes());
                    out2.flush();
                    if (L!=null){
                        L.onSuccess();
                    }
                } catch (IOException e) {
                   if ( L!=null){
                       if (isSuccess1){
                           L.onSuccess();  //只要其中一個更新成功，根秘钥就会变化，即视为更新成功
                       }else {
                           L.failure();
                       }
                   }
                    Log.e(TAG, e.getMessage());
                } finally {
                    if (out1 != null) {
                        try {
                            out1.close();
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                    if (out2 != null) {
                        try {
                            out2.close();
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * 读取文件中的安全组件内容
     *
     * @param fileName 安全组件文件名
     * @return 安全组件内容二进制字节数组
     */
    private static byte[] readComponent(String[] fileName) {
        FileInputStream in =null;
        BufferedReader reader =null;
        try {
            File file = new File(path+File.separator+fileName[0]+File.separator+fileName[1]);
            if (!file.exists()) return null;
            in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer sb = new StringBuffer();
            int codeNum ;
            while ((codeNum = reader.read())!=-1) {
                  char c = (char) codeNum;
                if (c=='\n'){
                    break;
                }
                if (sb.length()>=200){
                    throw new IOException("input too long");
                }
                sb.append(c);
            }
            return toByte(sb.toString());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }finally {
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }


    /**
     * 获取解密后数据部分
     *
     * @param bytes 整个密文的二进制字节数组
     * @return 解密后数据字节数组
     */
    private static byte[] getResult(byte[] bytes) {
        byte resultBytes[] = new byte[bytes.length-52];
        for (int i = 20; i < bytes.length-32; i++) {
            resultBytes[i-20] = bytes[i];
        }
        return resultBytes;
    }

    /**
     * 获取解密后iv偏移量部分
     *
     * @param bytes 整个密文的二进制字节数组
     * @return 解密后iv偏移量字节数组
     */
    private static byte[] getIv(byte[] bytes) {
        byte resultBytes[] = new byte[16];
        for (int i = 4; i < 20; i++) {
            resultBytes[i-4] = bytes[i];
        }
        return resultBytes;
    }
    /**
     * 获取解密后工作秘钥部分
     *
     * @param bytes 整个密文的二进制字节数组
     * @return 解密后工作秘钥部分
     */
    private static byte[] getKey(byte[] bytes) {
        byte resultBytes[] = new byte[32];
        for (int i = bytes.length-32; i < bytes.length; i++) {
            resultBytes[i-bytes.length+32] = bytes[i];
        }
        return resultBytes;
    }

    /**
     * 得到最终的加密密文
     *
     * @param id   算法id
     * @param lv   lv向量
     * @param data 数据密文
     * @param key  秘钥密文
     * @return
     * @throws Exception
     */
    private static String parseBytes2HexStr(byte[] id, byte[] lv, byte[] data, byte[] key) throws Exception {
        return toHex(id) + toHex(lv) + toHex(data) + toHex(key);
    }

    /***
     * Aes算法
     *
     * @param byteData 需加解密的数据
     * @param byteKey  加解密秘钥
     * @param iv       iv偏移量
     * @param opmode   模式
     * @throws Exception
     */
    private static byte[] aes(byte[] byteData, byte[] byteKey,byte[] iv ,int opmode) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
       SecureRandom secureRandom ;
        if (android.os.Build.VERSION.SDK_INT > 23) {
            secureRandom =  SecureRandom.getInstance("SHA1PRNG",new CryptoProvider());
        }else{
            secureRandom =  SecureRandom.getInstance("SHA1PRNG","Crypto");
        }
        secureRandom.setSeed(byteKey);
        kgen.init(128, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        //初始化加密器
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
        cipher.init(opmode, keySpec, ivSpec);// 初始化
        return cipher.doFinal(byteData);
    }

    /**
     * 二进制转化为16进制
     *
     * @param buf 需转换的二进制数组
     * @return 转化后的16进制字符串
     */
    public static String toHex(byte[] buf) {
        final String HEX = "0123456789ABCDEF";
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            result.append(HEX.charAt((buf[i] >> 4) & 0x0f)).append(
                    HEX.charAt(buf[i] & 0x0f));
        }
        return result.toString();
    }
    /**
     * 16进制转为二进制
     *
     * @param hexString 需转换的16进制字符串
     * @return 转换后的2进制字节数组
     */
    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }
}
