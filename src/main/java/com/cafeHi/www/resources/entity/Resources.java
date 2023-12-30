package com.cafeHi.www.resources.entity;

import com.cafeHi.www.member.entity.MemberAuth;
import com.cafeHi.www.member.entity.MemberAuthHierachy;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "RESOURCES")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resources implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private Long id;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "http_method")
    private String httpMethod;

    @Column(name = "order_num")
    private int orderNum;

    @Column(name = "resource_type")
    private String resourceType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "member_auth_resources", joinColumns = {
            @JoinColumn(name = "resource_id") }, inverseJoinColumns = { @JoinColumn(name = "member_auth_id") })
    private Set<MemberAuth> memberAuthSet = new HashSet<>();
}
