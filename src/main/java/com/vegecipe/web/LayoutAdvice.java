package com.vegecipe.web;

import com.vegecipe.config.auth.LoginUser;
import com.vegecipe.config.auth.dto.SessionUser;
import com.vegecipe.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Mustache.Compiler;
import com.samskivert.mustache.Template.Fragment;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.Writer;

@RestControllerAdvice
public class LayoutAdvice {
    private final Mustache.Compiler compiler;

    @Autowired
    public LayoutAdvice(Compiler compiler) {
        this.compiler = compiler;
    }

    @ModelAttribute("layout")
    public Mustache.Lambda layout(@LoginUser SessionUser user) {
        Layout layout = new Layout(compiler);
        if (user != null) {
            if (StringUtils.isEmpty(user.getName())) {
                layout.authenticatedUser = false;
            } else {
                layout.authenticatedUser = true;
                layout.loginUserEmail = user.getEmail();
                layout.loginUserName = user.getName();
                layout.loginUserPicture = user.getPicture();
                if(Role.STAFF.getKey().equals(user.getRoleKey()))
                    layout.isStaff = true;
            }
        }
        return layout;
    }
}

class Layout implements Mustache.Lambda {
    String Body = "";
    String loginUserName = "";
    String loginUserEmail = "";
    String loginUserPicture = "";
    Boolean authenticatedUser = false;
    Boolean isStaff = false;

    private Compiler compiler;

    public Layout(Compiler compiler){
        this.compiler = compiler;
    }

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
        Body = frag.execute();
        compiler.compile("{{>layout}}").execute(frag.context(), out);
    }
}