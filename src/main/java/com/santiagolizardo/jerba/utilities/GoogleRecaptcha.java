package com.santiagolizardo.jerba.utilities;

import com.google.appengine.api.urlfetch.URLFetchService;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GoogleRecaptcha {

    private static final Logger logger = Logger.getLogger(GoogleRecaptcha.class.getName());

    private static final String RECAPTCHA_SERVICE_URL = "https://www.google.com/recaptcha/api/siteverify";

    private String publicKey;
    private String privateKey;

    public GoogleRecaptcha(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * checks if a user is valid
     * @param clientRecaptchaResponse
     * @return true if human, false if bot
     * @throws IOException
     * @throws java.text.ParseException
     */
    public boolean isValid(String clientRecaptchaResponse, String remoteIp) {
        if (clientRecaptchaResponse == null || "".equals(clientRecaptchaResponse)) {
            return false;
        }

        try {
            URL obj = new URL(RECAPTCHA_SERVICE_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            //add client result as post parameter
            String postParams =
                    "secret=" + privateKey +
                            "&response=" + clientRecaptchaResponse + "&remoteip=" + remoteIp;

            // send post request to google recaptcha server
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            if(responseCode != 200) {
                logger.warning("Response code != 200: " + responseCode);
                return false;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //Parse JSON-response
            JSONObject json = new JSONObject(response.toString());

            Boolean success = (Boolean) json.get("success");
            Double score = (Double) json.get("score");

            logger.info("success : " + success);
            logger.info("score : " + score);

            return (success && score >= 0.5);
        } catch(Exception ioe) {
            logger.log(Level.SEVERE, ioe.getMessage(), ioe);

            // On error the fallback is to an invalid token
            return false;
        }
    }
}