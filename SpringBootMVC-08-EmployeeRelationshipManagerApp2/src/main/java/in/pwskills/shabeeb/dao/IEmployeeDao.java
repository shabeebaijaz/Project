package in.pwskills.shabeeb.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import in.pwskills.shabeeb.model.Employee;

public interface IEmployeeDao extends JpaRepository<Employee, Integer> {

}
