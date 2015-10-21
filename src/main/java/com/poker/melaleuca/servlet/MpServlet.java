package com.poker.melaleuca.servlet;

import org.apache.commons.codec.digest.DigestUtils;
import spark.servlet.SparkApplication;

import static spark.Spark.get;

/**
 * Created by pokerwen on 2015/10/21.
 */
public class MpServlet implements SparkApplication {

    public void init() {
        get("/processMpMessage", ((request, response) -> {
            String signature = request.queryParams("signature");
            System.out.println("signature:" + signature);
            String timestamp = request.queryParams("timestamp");
            System.out.println("timestamp:" + timestamp);
            String nonce = request.queryParams("nonce");
            System.out.println("nonce:" + nonce);
            String echostr = request.queryParams("echostr");
            System.out.println("echostr:" + echostr);

            String sha1String = encodeWeChatString(timestamp, nonce);
//            String sha1String = "klsdjfasdfklj";
            System.out.println("sha1String:" + sha1String);

            if(signature.equals(sha1String)){
                return echostr;
            }else {
                return null;
            }
        }));
    }

    public static String TOKEN = "PokerWen";

    public String encodeWeChatString(String timestamp, String nonce){
        String sortedString = orderStringsInDictionary(TOKEN, timestamp, nonce);

        return DigestUtils.sha1Hex(sortedString);
    }

    public String orderStringsInDictionary(String str1, String str2, String str3){
        String[] inputAry = { str1, str2, str3};
        for (int i = 0; i < inputAry.length - 1; i++) {
            boolean change = false; // 用作冒泡排序的标记，如果一趟排序存在交换，则change设为true，说明还需要下一趟排序
            for (int j = 0; j < inputAry.length - i - 1; j++) {
                if (bigger(inputAry[j], inputAry[j + 1])) {
                    // swap(s[j], s[j + 1]);
                    String tmp = inputAry[j];
                    inputAry[j] = inputAry[j + 1];
                    inputAry[j + 1] = tmp;
                    change = true;
                }
            }
            if (!change) {
                break; // 当change为false的时候，说明不需要再冒泡了
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < inputAry.length; i++) {
            sb.append(inputAry[i]);
        }

        return sb.toString();
    }

    private boolean bigger(String s1, String s2) {
        int length1 = s1.length();
        int length2 = s2.length();
        int i = 0;
        while (i < length1 && i < length2) {
            if (s1.charAt(i) > s2.charAt(i)) {
                return true;
            } else if (s1.charAt(i) < s2.charAt(i)) {
                return false;
            } else {
                i++;
            }
        }
        if (i == length1) {
            return false;
        } else {
            return true;
        }
    }
}
