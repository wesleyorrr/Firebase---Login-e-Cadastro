package com.curvelo.loginaute.Util;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoBd {
    private static FirebaseAuth auth;
        public static FirebaseAuth firebaseAutentificacao(){
            if (auth == null){
                auth = FirebaseAuth.getInstance();
            }
            return auth;
        }
}
