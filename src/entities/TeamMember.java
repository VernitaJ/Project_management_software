package entities;

import javax.management.relation.Role;

public class TeamMember extends User{
    public TeamMember(String userName, String password, String eMail, String occupation, String companyName) {
        super(userName, password, eMail, occupation, companyName);
    }

}
