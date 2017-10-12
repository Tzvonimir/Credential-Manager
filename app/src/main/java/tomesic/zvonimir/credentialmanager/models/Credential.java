package tomesic.zvonimir.credentialmanager.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static tomesic.zvonimir.credentialmanager.models.Credential.TABLE_NAME;

@Entity(tableName = TABLE_NAME, foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "id",
        childColumns = "category_id"))
public class Credential {
    public static final String TABLE_NAME = "credentials";

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String username;
    private String email;
    private String password;
    private String comment;
    private String URL;

    @ColumnInfo(name = "category_id")
    public int categoryId;

    public Credential(int id, String name, String username, String email, String password, String comment, String URL, int categoryId) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.comment = comment;
        this.URL = URL;
        this.categoryId = categoryId;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", comment='" + comment + '\'' +
                ", URL='" + URL + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}