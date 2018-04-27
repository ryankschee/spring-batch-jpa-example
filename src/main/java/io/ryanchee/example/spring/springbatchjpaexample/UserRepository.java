package io.ryanchee.example.spring.springbatchjpaexample;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserBean, String> {

	@Query("select u from UserBean u where u.id = ?1")
	public UserBean findById(Long id);
	
	@Query("select u from UserBean u where UPPER(u.employeeName) = UPPER(?1)")
	public UserBean findByName(String employeeName);

	@Query("select u from UserBean u where UPPER(u.employeeId) = UPPER(?1)")
	public UserBean findByEmployeeId(String employeeId);
	
	@Query("select u from UserBean u where UPPER(u.department) = UPPER(?1)")
	public List<UserBean> findByDepartment(String department);
}
