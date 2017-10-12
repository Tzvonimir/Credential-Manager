package tomesic.zvonimir.credentialmanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static tomesic.zvonimir.credentialmanager.models.Category.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class Category {
    public static final String TABLE_NAME = "categories";

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
