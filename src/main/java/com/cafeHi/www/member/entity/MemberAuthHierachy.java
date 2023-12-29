package com.cafeHi.www.member.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberAuthHierachy implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "child_name")
    private String childName;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_name", referencedColumnName = "child_name")
    private MemberAuthHierachy parentName;

    @OneToMany(mappedBy = "parentName", cascade = {CascadeType.ALL})
    private Set<MemberAuthHierachy> memberAuthHierarchy = new HashSet<>();

    // parentName setting method
    public void setParentName(MemberAuthHierachy memberAuthHierachy) {
        this.parentName = memberAuthHierachy;
    }
}
