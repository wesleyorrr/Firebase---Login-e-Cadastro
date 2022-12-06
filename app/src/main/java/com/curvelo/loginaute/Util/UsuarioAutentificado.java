package com.curvelo.loginaute.Util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsuarioAutentificado {
    public static FirebaseUser usuarioLogado(){
        FirebaseAuth usuario = ConfiguracaoBd.firebaseAutentificacao();
        return usuario.getCurrentUser();
    }
}
