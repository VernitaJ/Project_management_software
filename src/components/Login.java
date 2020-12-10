package components;

import controller.SystemAdmin;
import tools.Input;

public class Login {
    private static final Login instance = null;
    private Input input = Input.getInstance();

    Login(){
    }

    public static Login getInstance() {
        if (instance == null) {
            return new Login();
        } else {
            return instance;
        }
    }
    private boolean checkAdminUsername()
    {
        if (SystemAdmin.getInstance().getUsername().equals(input.getStr("Username: ")))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private boolean checkAdminPassword()
    {
        if (SystemAdmin.getInstance().getPassword().equals(input.getStr("Password: ")))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public boolean authorizeAdmin()
    {
        if (checkAdminUsername())
        {
            if(checkAdminPassword())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

}