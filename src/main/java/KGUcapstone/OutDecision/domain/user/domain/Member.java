package KGUcapstone.OutDecision.domain.user.domain;

import KGUcapstone.OutDecision.domain.user.domain.enums.SocialType;
import KGUcapstone.OutDecision.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //not null 50
    @Column(nullable = false, length = 50)
    private String email;

    //not null 16
    @Column(nullable = false, length = 16)
    private String password;

    //not null 20
    @Column(nullable = false, length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private SocialType socialType;

    //not null 20
    @Column(nullable = false, length = 20)
    private String nickname;

    //not null default 0
    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer point;

    //not null default 0
    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer bumps;

    //30
    @Column(length = 30)
    private String phone;

    //20
    @Column(length = 20)
    private String userTitle;

    //not null default 기본이미지
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT '기본 이미지 URL'")
    private String userImg;

    @Column(length = 30)
    private String userRole;

    public void updateMember(String name, String nickname, String phone) {
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}
