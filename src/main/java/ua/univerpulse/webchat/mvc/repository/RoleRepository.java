package ua.univerpulse.webchat.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.univerpulse.webchat.mvc.domain.Role;
import ua.univerpulse.webchat.mvc.domain.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.role = :roleEnum")
    Role findRoleByRoleName(@Param("roleEnum") RoleEnum roleEnum);

}
