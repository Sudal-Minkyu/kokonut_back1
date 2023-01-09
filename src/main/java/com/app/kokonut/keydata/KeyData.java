package com.app.kokonut.keydata;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@EqualsAndHashCode(of = "keyName")
@Data
@NoArgsConstructor
@Table(name="key_data")
public class KeyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_name")
    private String keyName;

    @Column(name = "key_value")
    private String keyValue;

    @Column(name = "key_group")
    private String keyGroup;

    @Column(name = "key_description")
    private String keyDescription;

}
