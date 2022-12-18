package avada.media.myhouse24_client.model.common;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public class MappedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

}
