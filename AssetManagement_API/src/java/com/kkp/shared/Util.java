package com.kkp.shared;

import com.kkp.security.AES256JNCryptor;
import com.kkp.security.JNCryptor;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.math.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;

public class Util {
	
    public static boolean isNullOrEmpty(String value)
    {
      return (value == null) || (value.trim().length() == 0);
    }

    private static final String CHAR_LIST = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int RANDOM_STRING_LENGTH = 8;
    
    public static String encrypt(String value)
    {
      String result = "";
      try
      {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] inputBytes = value.getBytes();
        md.update(inputBytes);

        byte[] digest = md.digest();
        StringBuffer md5Hash = new StringBuffer();
        String s = value;
        for (int i = 0; i < digest.length; i++)
        {
          s = Integer.toHexString(0xFF & digest[i]);
          if (s.length() < 2) {
            s = "0" + s;
          }
          md5Hash.append(s);
        }
        result = md5Hash.toString();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      return result;
    }

    /**
       * This method generates random string
       * @return
       */
    public String generateRandomString(){

        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<RANDOM_STRING_LENGTH; i++){
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

    /**
     * This method generates random numbers
     * @return int
     */
    private int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }
            
    public static String jreplace(String origin, String regex, String replace)
     {
       String result = origin;
       try
       {
         int index = -1;
         while ((index = origin.indexOf(regex)) > -1)
         {
           int len = regex.length();
           String begin = origin.substring(0, index);
           String end = origin.substring(index + len, origin.length());
           result = begin + replace + end;
           origin = result;
         }
       }
       catch (Exception e) {}
       return result;
     }

    public void checkACL(String action, String className, HttpServletRequest request, HttpServletResponse response) {
        try {
          if(action != null & action != className ){
               response.sendRedirect("./dashboard.html");
          }
        } catch (Exception e) {
        }
       }

    public static String reverse(String param){
        String result= "";
        try {
           if(param != null){
                 StringBuffer sb = new StringBuffer(param);
                 result = "0"+sb.reverse().toString();
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
         return result;
      }

    public String createComm(String key ,String key1){
      StringBuilder sb = new StringBuilder();
         try {
          Util util = new Util();
          if(!util.isNullOrEmpty(key) && !util.isNullOrEmpty(key1)){
              for(int i=0; i < key.length() -1; i += 2){
                  String output1 = key.substring(i, (i + 2));
                  sb.append(output1);
                  String output2 = key1.substring(i, (i + 4));
                  sb.append(output2);
              }
          }

         } catch (Exception e) {
             e.printStackTrace();
         }
      return  sb.toString();
     }         

      public String getPassword(String password, String key){
        String result = "";
       try {
           Util util = new Util();
           JNCryptor cryptor = new AES256JNCryptor();
           if(!util.isNullOrEmpty(password) && !util.isNullOrEmpty(key)){
               byte[] encryptedText = Base64.decodeBase64(password); //response_string.getBytes();
               byte[] plaintext = cryptor.decryptData(encryptedText, key.toCharArray());
               result = new String(plaintext);
           }
       } catch (Exception e) {
           result = "";
           e.printStackTrace();
       }
    return result;
   }

    public static String getProperty(ServletContext context, String key)
     {
       String value = null;
       Properties prop = null;
       try
       {
         File fprop = new File(context.getRealPath("/WEB-INF/classes/application.properties"));
         if ((fprop != null) && (fprop.exists()))
         {
           prop = new Properties();
           prop.load(new FileInputStream(fprop));
           value = prop.getProperty(key);
         }
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
       return value;
     }

    public  BigDecimal roundBigDecimal(final BigDecimal input){
       return input.round(
           new MathContext(
               input.toBigInteger().toString().length(),
               RoundingMode.HALF_EVEN
           )
       );
   }

     public String uuid(){
       return UUID.randomUUID().toString();
     }
  
    public static String reverseIt(String source) {
       int i, len = source.length();
       StringBuilder dest = new StringBuilder(len);
       for (i = (len - 1); i >= 0; i--){
           dest.append(source.charAt(i));
       }
       return dest.toString();
   } 
}
