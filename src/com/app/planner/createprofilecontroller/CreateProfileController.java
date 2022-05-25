package com.app.planner.createprofilecontroller;

import com.app.planner.BaseScreenController;
import com.app.planner.Profile;

public class CreateProfileController extends BaseScreenController {

    Profile profile;

    public void initialiseCredentials(String email, String password) {
        profile.setEmail(email);
        profile.setPassword(password);
    }
}
