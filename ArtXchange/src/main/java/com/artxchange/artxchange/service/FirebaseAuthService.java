// src/main/java/com/artxchange/service/FirebaseAuthService.java
package com.artxchange.artxchange.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public class FirebaseAuthService {
    
    public UserRecord createUser(String email, String password) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
            .setEmail(email)
            .setPassword(password)
            .setDisabled(false);
        
        return FirebaseAuth.getInstance().createUser(request);
    }
}