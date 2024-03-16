package KGUcapstone.OutDecision.domain.user.repository;

import KGUcapstone.OutDecision.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
}
