package com.netcracker.repository;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for Persons
 */
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query("select pe.groups from PersonEntity pe where pe.id=:id ")
    List<GroupEntity> getGroups (@Param("id") Long id);

    @Query("select pe from PersonEntity pe where " +
            "((:name is null )or(?1 IS NOT NULL AND pe.name like ?1)) and " +
            "((:raceID is null )or(?2 IS NOT NULL AND pe.race.id = ?2 )) and" +
            "((:ageFrom is null )or(?3 IS NOT NULL AND pe.age >= ?3)) and" +
            "((:ageTo is null )or(?4 IS NOT NULL AND pe.age <= ?4)) and" +
            "((:sex is null )or(?5 IS NOT NULL AND pe.sex like ?5))")
    List<PersonEntity> getBySearchParams(@Param("name") String name,
                                         @Param("raceID") Long raceID,
                                         @Param("ageFrom") Integer ageFrom,
                                         @Param("ageTo") Integer ageTo,
                                         @Param("sex") String sex);

    @Query("select pe from PersonEntity pe where pe.login=:login AND pe.pass=:password ")
    PersonEntity login (@Param("login")     String login,
                        @Param("password")  String password);
}
