package tomesic.zvonimir.credentialmanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static tomesic.zvonimir.credentialmanager.models.MasterPassword.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class MasterPassword {
    public static final String TABLE_NAME = "master_passwords";

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String password;
    private String salt;

    public MasterPassword(int id, String password, String salt) {
        this.id = id;
        this.password = password;
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}