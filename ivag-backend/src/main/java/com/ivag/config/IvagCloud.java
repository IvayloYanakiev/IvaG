package com.ivag.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class IvagCloud {

    public Cloudinary ivagCloud() {

        HashMap<String,Object> cloudParams = new HashMap<>();
        cloudParams.put("cloud_name","dxnmejm7r");
        cloudParams.put("api_key","675682527246644");
        cloudParams.put("api_secret","zj63uN_FXJCIyByobSXIQYv2G1E");
        Cloudinary cloudinary = new Cloudinary(cloudParams);

        return cloudinary;
    }
}
