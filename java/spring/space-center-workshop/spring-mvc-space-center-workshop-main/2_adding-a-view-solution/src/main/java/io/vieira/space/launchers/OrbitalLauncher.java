package io.vieira.space.launchers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("ORBITAL_LAUNCHERS")
public class OrbitalLauncher {

    private final String name;

    private final String codeName;

    private final int maxLeo;

    @Id
    private int id;

    @JsonCreator
    public OrbitalLauncher(@JsonProperty("name") String name, @JsonProperty("codeName") String codeName, @JsonProperty("maxLeo") int maxLeo) {
        this.name = name;
        this.codeName = codeName;
        this.maxLeo = maxLeo;
    }

    @PersistenceCreator
    public OrbitalLauncher(int id, String name, String codeName, int maxLeo) {
        this(name, codeName, maxLeo);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCodeName() {
        return codeName;
    }

    public int getMaxLeo() {
        return maxLeo;
    }
}
